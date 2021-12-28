package org.lemon.repository.database.interfaces;

public interface SaveConcurrencyRepository {
    void execute(Long userId);
}
