package com.urise.webapp.sql;

import com.urise.webapp.exception.ExistStorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public <T> T executeRequest(String sql, BlockCode<T> blockCode) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            T value = blockCode.doing(ps);
            ps.execute();
            return value;
        } catch (SQLException e) {
            throw new ExistStorageException(e.getMessage());
        }
    }

    public interface BlockCode<T> {
        T doing(PreparedStatement ps) throws SQLException;
    }
}