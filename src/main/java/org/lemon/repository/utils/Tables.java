package org.lemon.repository.utils;

public enum Tables {
    CONCURRENCY("CREATE TABLE IF NOT EXISTS CONCURRENCY(" +
            "CONCURRENCY_ID BIGINT(20) NOT NULL AUTO_INCREMENT," +
            "USER_ID BIGINT(20) NOT NULL," +
            "TIMESTAMP DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP," +
            "PRIMARY KEY (CONCURRENCY_ID));" +
            "CREATE INDEX user_id_idx ON CONCURRENCY(USER_ID)");

    private String creationScript;

    Tables(String creationScript) {
        this.creationScript = creationScript;
    }

    public String getCreationScript() {
        return creationScript;
    }
}
