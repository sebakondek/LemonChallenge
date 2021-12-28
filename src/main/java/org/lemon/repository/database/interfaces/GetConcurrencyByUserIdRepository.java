package org.lemon.repository.database.interfaces;

public interface GetConcurrencyByUserIdRepository {
    Long execute(Long userId);
}
