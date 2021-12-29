package org.lemon.unit.repository.database;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lemon.configuration.util.DatabaseUtils;
import org.lemon.exception.custom.RepositoryException;
import org.lemon.repository.database.impl.GetConcurrencyByUserIdDBRepository;
import org.lemon.repository.database.interfaces.GetConcurrencyByUserIdRepository;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class GetConcurrencyByUserIdRepositoryTest extends TestDatabaseHelper {

    private GetConcurrencyByUserIdRepository instance;

    @BeforeEach
    public void setUp() {
        super.setUp();
        this.instance = new GetConcurrencyByUserIdDBRepository(super.dataSource);
    }

    @AfterEach
    public void cleanUp() {
        super.cleanUp();
    }

    @Test
    public void whenInsertingConcurrencyWithin10Seconds_mustOutputSameAmount() throws SQLException {
        Long userId = 1234L;
        int concurrencyQty = 3;

        super.insertMultipleConcurrency(userId, LocalDateTime.now(), concurrencyQty);

        Long actualValue = this.instance.execute(userId);

        Assertions.assertEquals(concurrencyQty, actualValue);
    }

    @Test
    public void whenInsertingConcurrencyAbove10Seconds_mustOutputLessAmount() throws SQLException {
        Long userId = 1234L;
        int concurrencyQty = 2;

        super.insertConcurrency(userId, LocalDateTime.now().minusSeconds(11));
        super.insertMultipleConcurrency(userId, LocalDateTime.now(), concurrencyQty);

        Long actualValue = this.instance.execute(userId);

        Assertions.assertEquals(concurrencyQty, actualValue);
    }

    @Test
    public void whenTableDoesNotExist_mustThrowException() {
        DatabaseUtils.dropAllTables();

        Assertions.assertThrows(RepositoryException.class, () -> this.instance.execute(1234L));
    }
}
