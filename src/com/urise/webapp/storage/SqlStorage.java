package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.SqlHelper;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        String sql = "DELETE FROM resume";
        sqlHelper.executeRequest(sql, preparedStatement -> null);
    }

    @Override
    public void save(Resume resume) {
        String sql = "INSERT INTO resume (uuid, full_name) VALUES (?,?)";
        sqlHelper.executeRequest(sql, ps -> {
            ps.setString(1, resume.getUuid());
            ps.setString(2, resume.getFullName());
            return null;
        });
    }

    @Override
    public void update(Resume resume) {
        String sql = "UPDATE resume SET full_name = ? WHERE uuid = ?";
        sqlHelper.executeRequest(sql, ps -> {
            ps.setString(2, resume.getUuid());
            ps.setString(1, resume.getFullName());
            ps.executeUpdate();
            return null;
        });
    }

    @Override
    public Resume get(String uuid) throws SQLException {
        String sql = "SELECT * FROM resume WHERE uuid = ?";
        return sqlHelper.executeRequest(sql, ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("full_name"));
        });
    }

    @Override
    public void delete(String uuid) {
        String sql = "DELETE FROM resume WHERE uuid = ?";
        sqlHelper.executeRequest(sql, ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumeList = new ArrayList<>();
        String sql = "SELECT * FROM resume r ORDER BY full_name,uuid";
        return sqlHelper.executeRequest(sql, ps -> {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                resumeList.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
            }
            return resumeList;
        });
    }

    @Override
    public int getSize() {
        String sql = "SELECT count(*) FROM resume";
        return sqlHelper.executeRequest(sql, ps -> {
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }
}