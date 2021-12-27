package org.lemon.configuration.module;

import com.google.inject.AbstractModule;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;

public class SQLModule extends AbstractModule {

    private static final Logger log = LoggerFactory.getLogger(SQLModule.class);

    private static final String HOST = "jdbc:h2:mem:lemon;MODE=mysql";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "";

    @Override
    protected void configure() {
        log.info("Initializing SQLModule");

        bind(DataSource.class).toInstance(this.getDataSource());

        log.info("Successfully initialized SQLModule");
    }

    private DataSource getDataSource() {

        int maxPoolSize = 10;
        int idlePoolSize = 3;
        int connectionTimeout = 1000;
        int idleTimeout = 5000;

        HikariConfig config = new HikariConfig();

        config.setJdbcUrl(HOST);
        config.setUsername(USERNAME);
        config.setPassword(PASSWORD);
        config.setMinimumIdle(idlePoolSize);
        config.setMaximumPoolSize(maxPoolSize);
        config.setConnectionTimeout(connectionTimeout);
        config.setIdleTimeout(idleTimeout);
        config.setAutoCommit(true);

        config.addDataSourceProperty("serverTimezone", "GMT-3");
        config.addDataSourceProperty("cachePrepStmts", Boolean.FALSE);
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.addDataSourceProperty("useServerPrepStmts", Boolean.FALSE);
        config.addDataSourceProperty("useLocalSessionState", Boolean.TRUE);
        config.addDataSourceProperty("useLocalTransactionState", Boolean.TRUE);
        config.addDataSourceProperty("rewriteBatchedStatements", Boolean.TRUE);
        config.addDataSourceProperty("cacheResultSetMetadata", Boolean.TRUE);
        config.addDataSourceProperty("cacheServerConfiguration", Boolean.TRUE);
        config.addDataSourceProperty("elideSetAutoCommits", Boolean.TRUE);
        config.addDataSourceProperty("maintainTimeStats", Boolean.FALSE);

       return new HikariDataSource(config);
    }
}
