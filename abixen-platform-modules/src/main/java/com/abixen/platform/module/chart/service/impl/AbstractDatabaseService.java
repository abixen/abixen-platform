/**
 * Copyright (c) 2010-present Abixen Systems. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.abixen.platform.module.chart.service.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;


public class AbstractDatabaseService {

    public List<String> getColumns(Connection connection, String tableName) {

        List<String> columns = new ArrayList<>();

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName);
            ResultSetMetaData rsmd = rs.getMetaData();

            int columnCount = rsmd.getColumnCount();

            IntStream.range(1, columnCount + 1).forEach(i -> {
                try {
                    columns.add(rsmd.getColumnName(i));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return columns;
    }

    public List<String> getTables(Connection connection) {

        final int objectTypeIndex = 4;
        final int objectNameIndex = 3;

        List<String> tables = new ArrayList<>();

        try {
            ResultSet rs = getTablesAsResultSet(connection);

            while (rs.next()) {
                if ("TABLE".equals(rs.getString(objectTypeIndex)) || "VIEW".equals(rs.getString(objectTypeIndex))) {
                    if (isAllowedTable(rs.getString(objectNameIndex))) {
                        tables.add(rs.getString(objectNameIndex));
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tables;
    }

    protected ResultSet getTablesAsResultSet(Connection connection) throws SQLException {
        DatabaseMetaData md = connection.getMetaData();
        return md.getTables(null, null, "%", null);
    }

    protected boolean isAllowedTable(String tableName) {
        return true;
    }
}