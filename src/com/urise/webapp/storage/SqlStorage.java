package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage{
    public final ConnectionFactory connectionFactory;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        String sql = "DELETE FROM resume";
        doDb(preparedStatement -> null, sql);
        /*try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.execute();
        } catch (SQLException e) {
            throw new StorageException(e.getMessage());
        }*/
    }

    @Override
    public void save(Resume resume) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO resume(uuid, full_name) VALUES (?, ?)")) {
            ps.setString(1, resume.getUuid());
            ps.setString(2, resume.getFullName());
            ps.execute();
        } catch (SQLException e) {
            throw new ExistStorageException(e.getMessage());
        }
    }

    @Override
    public void update(Resume resume) {
        String sql = "UPDATE resume SET full_name = ? WHERE uuid = ?";
        doDb(preparedStatement -> {
            preparedStatement.setString(2, resume.getUuid());
            preparedStatement.setString(1, resume.getFullName());
            return null;
        }, sql);
        /*try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
            ps.setString(2, resume.getUuid());
            ps.setString(1, resume.getFullName());
            ps.execute();
        } catch (SQLException e) {
            throw new StorageException(e.getMessage());
        }*/
    }

    @Override
    public Resume get(String uuid) throws SQLException {
        String sql = "SELECT * FROM resume WHERE uuid = ?";
        return doDb(ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("full_name"));
        }, sql);

        /*try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume WHERE uuid = ?")) {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("full_name"));
        } catch (SQLException e) {
            throw new StorageException(e.getMessage());
        }*/
    }

    @Override
    public void delete(String uuid) {
        String sql = "DELETE FROM resume WHERE uuid = ?";
        doDb(ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        }, sql);
        /*try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM resume WHERE uuid = ?")) {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
        } catch (SQLException e) {
            throw new StorageException(uuid, e.getMessage());
        }*/
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumeList = new ArrayList<>();
        String sql = "SELECT * FROM resume";
        return doDb(ps -> {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String uuid = rs.getString("uuid");
                String fullName = rs.getString("full_name");
                Resume resume = new Resume(uuid, fullName);
                resumeList.add(resume);
            }
            resumeList.sort(AbstractStorage.RESUME_FULLNAME_UUID_COMPARATOR);
            return resumeList;
        }, sql);
        /*try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String uuid = rs.getString("uuid");
                String fullName = rs.getString("full_name");
                Resume resume = new Resume(uuid, fullName);
                resumeList.add(resume);
            }
            resumeList.sort(AbstractStorage.RESUME_FULLNAME_UUID_COMPARATOR);
            return resumeList;
        } catch (SQLException e) {
            throw new StorageException(e.getMessage());
        }*/
    }

    @Override
    public int getSize() {
        String sql = "SELECT * FROM resume";
        final int[] count = {0};
        return doDb(ps -> {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count[0]++;
            }
            return count[0];
        }, sql);
        /*try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count++;
            }
            return count;
        } catch (SQLException e) {
            throw new StorageException(e.getMessage());
        }*/
    }

    interface BlockCode<T> {
        T doing(PreparedStatement ps) throws SQLException;
    }

    private <T> T doDb(BlockCode<T> blockCode, String sql) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            T value = blockCode.doing(ps);
            ps.execute();
            return value;
        } catch (SQLException e) {
            throw new StorageException(e.getMessage());
        }
    }
}
