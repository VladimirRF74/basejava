package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
        sqlHelper.transactionExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, resume.getFullName());
                ps.execute();
            }
            addContact(resume, conn);
            return null;
        });
    }

    @Override
    public void update(Resume resume) {
        String sql = "UPDATE resume SET full_name = ? WHERE uuid = ?";
        sqlHelper.transactionExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, resume.getFullName());
                ps.setString(2, resume.getUuid());
                ps.executeUpdate();
            }
            sqlHelper.executeRequest("DELETE FROM contact WHERE resume_uuid = ?", ps -> {
                ps.setString(1, resume.getUuid());
                return null;
            });
            addContact(resume, conn);
            return null;
        });
    }

    private void addContact(Resume resume, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, entry.getKey().name());
                ps.setString(3, entry.getValue());
                ps.addBatch();
            }
            ps.executeBatch();

        }

    }

    @Override
    public Resume get(String uuid) throws SQLException {
        String sql = "SELECT * FROM resume r LEFT JOIN contact c on r.uuid = c.resume_uuid WHERE r.uuid = ?";
        return sqlHelper.executeRequest(sql, ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            Resume r = new Resume(uuid, rs.getString("full_name"));
            do {
                String value = rs.getString("value");
                ContactType type = ContactType.valueOf(rs.getString("type"));
                r.addContact(type, value);
            } while (rs.next());
            return r;
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
        String sql = "SELECT * FROM resume r LEFT JOIN contact c on r.uuid = c.resume_uuid ORDER BY full_name,uuid";
        return sqlHelper.executeRequest(sql, ps -> {
            ResultSet rs = ps.executeQuery();
            Map<String, Resume> map = new LinkedHashMap<>();
            while (rs.next()) {
                String uuid = rs.getString("uuid");
                Resume resume = map.get(uuid);
                if (resume == null) {
                    resume = new Resume(uuid, rs.getString("full_name"));
                    map.put(uuid, resume);
                }
                String value = rs.getString("value");
                if (value != null) {
                    resume.addContact(ContactType.valueOf(rs.getString("type")), value);
                }

            }
            return new ArrayList<>(map.values());
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