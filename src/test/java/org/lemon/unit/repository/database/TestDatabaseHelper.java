package org.lemon.unit.repository.database;

import com.google.inject.Guice;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.lemon.configuration.module.Application;
import org.lemon.configuration.module.SQLModule;
import org.lemon.configuration.util.DatabaseUtils;
import org.lemon.repository.utils.Tables;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;

public abstract class TestDatabaseHelper {

    protected DataSource dataSource;

    protected void setUp() {
        Application.addInjector(Application.APP, Guice.createInjector(new SQLModule()));

        this.dataSource = DatabaseUtils.getH2DataSource();

        DatabaseUtils.createAllTables();
    }

    protected void cleanUp() {
        DatabaseUtils.dropAllTables();
        Application.clearInjectors();
    }

    protected void insertConcurrency(Long userId, LocalDateTime date) throws SQLException {
        String query = "INSERT INTO " + Tables.CONCURRENCY + " (USER_ID, TIMESTAMP)" +
                "VALUES (?, ?)";

        QueryRunner qr = new QueryRunner(dataSource);
        Connection conn = dataSource.getConnection();

        qr.insert(query, new ScalarHandler<>(), userId, date);

        DbUtils.commitAndClose(conn);
    }

    protected void insertMultipleConcurrency(Long userId, LocalDateTime date, int times) throws SQLException {
        for (int i = 0; i < times; i++) {
            insertConcurrency(userId, date);
        }
    }

    protected Long selectCountConcurrencyByUserId(Long userId) throws SQLException {
        String query = "SELECT COUNT(1) FROM " + Tables.CONCURRENCY + " " +
                "WHERE USER_ID = ?";

        QueryRunner qr = new QueryRunner(dataSource);
        Connection conn = dataSource.getConnection();

        Long result = qr.query(query, new ScalarHandler<>(), userId);

        DbUtils.commitAndClose(conn);

        return result;
    }
}
