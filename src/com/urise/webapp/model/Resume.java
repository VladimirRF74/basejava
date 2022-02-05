package com.urise.webapp.model;

/**
 * Initial resume class
 */
public class Resume {
    private String uuid; // Unique identifier

    public Resume(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    @Override
    public String toString() {
        return uuid;
    }
}
