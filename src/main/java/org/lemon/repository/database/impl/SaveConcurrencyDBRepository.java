package org.lemon.repository.database.impl;

import org.lemon.exception.custom.RepositoryException;
import org.lemon.repository.database.interfaces.SaveConcurrencyRepository;
import org.lemon.repository.utils.DBRepositoryCommon;
import org.lemon.repository.utils.Tables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.sql.DataSource;
import java.time.LocalDateTime;

@Singleton
public class SaveConcurrencyDBRepository extends DBRepositoryCommon implements SaveConcurrencyRepository {

    private static final Logger log = LoggerFactory.getLogger(SaveConcurrencyDBRepository.class);
    private static final String QUERY = "INSERT INTO " + Tables.CONCURRENCY + " (USER_ID, TIMESTAMP) " +
            "VALUES (?, ?)";

    @Inject
    public SaveConcurrencyDBRepository(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void execute(Long userId) {
        try {
            super.runInsert(QUERY, userId, LocalDateTime.now());
        } catch (Exception e) {
            log.error("Error trying to insert concurrency for user " + userId);
            throw new RepositoryException("Error trying to insert concurrency for user " + userId, e);
        }
    }
}
