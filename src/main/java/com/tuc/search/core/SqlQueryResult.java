package com.tuc.search.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class SqlQueryResult {
    private final String sql = "";
    private final List<Object> parameters = List.of();  // For PreparedStatement
    
    public PreparedStatement toPreparedStatement(Connection conn) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(sql);
        for (int i = 0; i < parameters.size(); i++) {
            stmt.setObject(i + 1, parameters.get(i));
        }
        return stmt;
    }
}
