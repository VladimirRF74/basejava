package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.*;
import com.urise.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.executeRequest("DELETE FROM resume", preparedStatement -> null);
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.transactionExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, resume.getFullName());
                ps.execute();
            }
            insertContact(resume, conn);
            insertSection(resume, conn);
            return null;
        });
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.transactionExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                ps.setString(1, resume.getFullName());
                ps.setString(2, resume.getUuid());
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(resume.getUuid());
                }
            }
            deleted("DELETE FROM contact WHERE resume_uuid = ?", resume);
            deleted("DELETE FROM section WHERE resume_uuid = ?", resume);
            insertContact(resume, conn);
            insertSection(resume, conn);
            return null;
        });
    }

    private void deleted(String sql, Resume resume) {
        sqlHelper.executeRequest(sql, ps -> {
            ps.setString(1, resume.getUuid());
            return null;
        });
    }

    @Override
    public Resume get(String uuid) throws SQLException {
        return sqlHelper.transactionExecute(conn -> {
            Resume resume;
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume r WHERE r.uuid = ?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
                resume = new Resume(uuid, rs.getString("full_name"));
            }
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM contact WHERE resume_uuid =?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String value = rs.getString("value");
                    ContactType type = ContactType.valueOf(rs.getString("type"));
                    if (value != null) {
                        resume.addContact(type, value);
                    }
                }
            }
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM section WHERE resume_uuid =?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    selectSection(resume, rs);
                }
            }
            return resume;
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
        return sqlHelper.transactionExecute(conn -> {
            Map<String, Resume> map = new LinkedHashMap<>();
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume ORDER BY full_name,uuid")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("uuid");
                    if (map.get(uuid) == null) {
                        map.put(uuid, new Resume(uuid, rs.getString("full_name")));
                    }
                }
            }
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM contact")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Resume r = map.get(rs.getString("resume_uuid"));
                    String value = rs.getString("value");
                    if (value != null) {
                        r.addContact(ContactType.valueOf(rs.getString("type")), value);
                    }
                }
            }
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM section")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Resume r = map.get(rs.getString("resume_uuid"));
                    selectSection(r, rs);
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

    private void insertContact(Resume resume, Connection conn) throws SQLException {
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

    private void insertSection(Resume resume, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO section (resume_uuid, type, content) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, AbstractSection> entry : resume.getSections().entrySet()) {
                SectionType type = entry.getKey();
                ps.setString(1, resume.getUuid());
                ps.setString(2, type.name());
                switch (type) {
                    case PERSONAL, OBJECTIVE -> ps.setString(3, String.valueOf(entry.getValue()));
                    case ACHIEVEMENT, QUALIFICATIONS -> ps.setString(3, addSection(entry.getValue()));
                }
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private ListSection getSectionValue(String value) {
        String[] row = value.split("\n");
        List<String> list = new ArrayList<>();
        for (String s : row) {
            list.add(s);
        }
        return new ListSection(list);
    }

    private String addSection(AbstractSection value) {
        ListSection ls = (ListSection) value;
        List<String> list = ls.getItems();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            builder.append(i < list.size() - 1 ? list.get(i).concat("\n") : list.get(i));
        }
        return builder.toString();
    }

    private void selectSection(Resume resume, ResultSet rs) throws SQLException {
        String value = rs.getString("content");
        SectionType type = SectionType.valueOf(rs.getString("type"));
        if (value != null) {
            switch (type) {
                case PERSONAL, OBJECTIVE -> resume.addSection(type, new TextSection(value));
                case ACHIEVEMENT, QUALIFICATIONS -> resume.addSection(type, getSectionValue(value));
                default -> throw new IllegalStateException("Unexpected value: " + type);
            }
        }
    }

}