package com.urise.webapp.storage;

public class ObjectStreamPathStorageTest extends AbstractStorageTest {
    public ObjectStreamPathStorageTest() {
        super(new PathStorage(String.valueOf(STORAGE_DIR), new ObjectStream()));
    }
}