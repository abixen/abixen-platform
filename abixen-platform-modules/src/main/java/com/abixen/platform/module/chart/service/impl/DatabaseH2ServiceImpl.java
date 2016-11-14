package com.abixen.platform.module.chart.service.impl;

import com.abixen.platform.module.chart.exception.DatabaseConnectionException;
import com.abixen.platform.module.chart.form.DatabaseConnectionForm;
import com.abixen.platform.module.chart.model.impl.DatabaseConnection;
import com.abixen.platform.module.chart.service.DatabaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by Konrad on 2016-11-14.
 */
@Service("H2")
public class DatabaseH2ServiceImpl implements DatabaseService {
    private final Logger log = LoggerFactory.getLogger(DatabasePostgresServiceImpl.class);


    @Override
    public Connection getConnection(DatabaseConnection databaseConnection) {
        return getConnection(new DatabaseConnectionForm(databaseConnection));
    }

    @Override
    public Connection getConnection(DatabaseConnectionForm databaseConnectionForm) {
        try {

            Class.forName("org.h2.Driver");

        } catch (ClassNotFoundException exception) {

            log.error("Where is your H2 JDBC Driver? "
                    + "Include in your library path!");
            throw new DatabaseConnectionException(exception.getMessage());

        }

        log.info("H2 JDBC Driver Registered!");

        Connection connection;

        try {

            connection = DriverManager.getConnection(
                    "jdbc:h2:mem:" +
                            databaseConnectionForm.getDatabaseName(), databaseConnectionForm.getUsername(),
                    databaseConnectionForm.getPassword());

        } catch (SQLException exception) {
            log.error("Connection Failed! Check output console");
            throw new DatabaseConnectionException(exception.getMessage());
        }

        if (connection != null) {
            log.info("You made it, take control your database now!");
        } else {
            log.error("Failed to make connection!");
        }

        return connection;
    }

    @Override
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

    @Override
    public List<String> getTables(Connection connection) {
        final int objectTypeIndex = 4;
        final int objectNameIndex = 3;

        List<String> tables = new ArrayList<>();

        try {
            DatabaseMetaData md = connection.getMetaData();
            ResultSet rs = md.getTables(null, null, "%", null);

            while (rs.next()) {
                if ("TABLE".equals(rs.getString(objectTypeIndex)) || "VIEW".equals(rs.getString(objectTypeIndex))) {
                    tables.add(rs.getString(objectNameIndex));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tables;
    }
}
