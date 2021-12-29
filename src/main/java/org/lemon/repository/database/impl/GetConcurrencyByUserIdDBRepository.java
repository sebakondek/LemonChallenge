package org.lemon.repository.database.impl;

import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.lemon.exception.custom.RepositoryException;
import org.lemon.repository.database.interfaces.GetConcurrencyByUserIdRepository;
import org.lemon.repository.utils.DBRepositoryCommon;
import org.lemon.repository.utils.Tables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.sql.DataSource;
import java.time.LocalDateTime;

@Singleton
public class GetConcurrencyByUserIdDBRepository extends DBRepositoryCommon implements GetConcurrencyByUserIdRepository {

    private static final Logger log = LoggerFactory.getLogger(GetConcurrencyByUserIdDBRepository.class);
    private static final String QUERY = "SELECT COUNT(1) FROM " + Tables.CONCURRENCY +
            " WHERE USER_ID = ? AND TIMESTAMP BETWEEN ? AND ?";

    @Inject
    public GetConcurrencyByUserIdDBRepository(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public Long execute(Long userId, int timeWindow) {
        LocalDateTime now = LocalDateTime.now();

        try {
            return super.runQuery(QUERY, new ScalarHandler<>(), userId, now.minusSeconds(timeWindow), now);
        } catch (Exception e) {
            log.error("Error getting count from concurrency for user " + userId);
            throw new RepositoryException("Error getting count from concurrency for user " + userId, e);
        }
    }
}
