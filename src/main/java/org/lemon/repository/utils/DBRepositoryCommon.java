package org.lemon.repository.utils;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public abstract class DBRepositoryCommon {

    private QueryRunner queryRunner;

    protected DBRepositoryCommon(DataSource dataSource) {
        this.queryRunner = new QueryRunner(dataSource);
    }

    private Connection getNewConnection() throws SQLException {
        DataSource ds = this.queryRunner.getDataSource();
        return ds.getConnection();
    }

    protected <T> T runQuery(String query, ResultSetHandler<T> handler, Object... args) throws SQLException {

        try (Connection connection = this.getNewConnection()) {
            return this.queryRunner.query(connection, query, handler, args);
        }
    }

    protected Long runInsert(String query, Object... args) throws SQLException {

        try (Connection connection = this.getNewConnection()) {
            return this.queryRunner.insert(connection, query, new ScalarHandler<>(), args);
        }
    }
}
