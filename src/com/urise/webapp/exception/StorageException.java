package com.urise.webapp.exception;

public class StorageException extends RuntimeException {
    private final String uuid;

    public StorageException(String message) {
        super(message);
        this.uuid = "";
    }

    public StorageException(String message, String uuid) {
        super(String.format(message, uuid));
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }
}