package org.lemon.unit.repository.database;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lemon.configuration.util.DatabaseUtils;
import org.lemon.exception.custom.RepositoryException;
import org.lemon.repository.database.impl.SaveConcurrencyDBRepository;

import java.sql.SQLException;

public class SaveConcurrencyRepositoryTest extends TestDatabaseHelper {

    private SaveConcurrencyDBRepository instance;

    @BeforeEach
    public void setUp() {
        super.setUp();
        this.instance = new SaveConcurrencyDBRepository(super.dataSource);
    }

    @AfterEach
    public void cleanUp() {
        super.cleanUp();
    }

    @Test
    public void whenSaveConcurrency_mustNotThrowException() throws SQLException {
        Long userId = 1234L;

        Assertions.assertDoesNotThrow(() -> this.instance.execute(userId));
        Assertions.assertEquals(1, super.selectCountConcurrencyByUserId(userId));
    }

    @Test
    public void whenTableDoesNotExist_mustThrowException() {
        DatabaseUtils.dropAllTables();

        Assertions.assertThrows(RepositoryException.class, () -> this.instance.execute(1234L));
    }
}
