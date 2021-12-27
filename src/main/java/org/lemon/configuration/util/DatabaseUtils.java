package org.lemon.configuration.util;

import org.lemon.configuration.module.Application;
import org.lemon.repository.utils.Tables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class DatabaseUtils {

    private static final Logger log = LoggerFactory.getLogger(DatabaseUtils.class);

    private static DataSource dataSource;

    public static DataSource getH2DataSource() {
        if (dataSource == null) {
            dataSource = Application.getInstance(DataSource.class);
        }

        return dataSource;
    }

    public static void createAllTables() {
        Connection conn;

        try {
            conn = getH2DataSource().getConnection();
            Tables[] tables = Tables.values();

            for (Tables table : tables) {
                Statement statement = conn.createStatement();
                statement.execute(table.getCreationScript());
            }

            conn.close();
        } catch (SQLException e) {
            log.error("Failed creating all tables.");
            throw new RuntimeException(e);
        }
    }

    public static void executeDrop(Tables tableName) {
        Connection conn;

        try {
            conn = getH2DataSource().getConnection();

            Statement statement = conn.createStatement();
            statement.execute("DROP TABLE IF EXISTS " + tableName);

            conn.close();
        } catch (SQLException e) {
            log.error("Failed dropping table");
            throw new RuntimeException(e);
        }
    }

    public static void dropAllTables() {
        Tables[] tables = Tables.values();

        for (Tables table : tables) {
            executeDrop(table);
        }
    }

    public static void createTable(Tables table) {
        Connection conn;

        try {
            conn = getH2DataSource().getConnection();
            Statement statement = conn.createStatement();

            statement.execute(table.getCreationScript());

            conn.close();
        } catch (SQLException e) {
            log.error("Failed creating table " + table);
            throw new RuntimeException(e);
        }
    }


    public static void cleanTable(Tables table) {
        try {
            Connection conn = getH2DataSource().getConnection();
            Statement statement = conn.createStatement();

            statement.execute("DELETE FROM " + table);

            conn.close();
        } catch (SQLException e) {
            log.error("Failed cleaning table " + table);
            throw new RuntimeException(e);
        }
    }
}
