package org.lemon.configuration.util;

import org.lemon.exception.custom.LockException;

import java.util.HashSet;
import java.util.function.Supplier;

public class LockManager {

    private static final HashSet<String> LOCKED_KEYS = new HashSet<>();

    public <T> T lockAndExecute(String lockKey, Supplier<T> supplier) {
        checkIfKeyAlreadyLocked(lockKey);

        try {
            return supplier.get();
        } finally {
            unlockKey(lockKey);
        }
    }

    private void checkIfKeyAlreadyLocked(String lockKey) {
        if(LOCKED_KEYS.contains(lockKey)) {
            throw new LockException("Key " + lockKey + " is already locked.");
        }
    }

    private void unlockKey(String lockKey) {
        LOCKED_KEYS.remove(lockKey);
    }

    //Testing purposes only
    public void addLockKey(String lockKey) {
        LOCKED_KEYS.add(lockKey);
    }

    //Testing purposes only
    public void clearLock() {
        LOCKED_KEYS.clear();
    }
}
