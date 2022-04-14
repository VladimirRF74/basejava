package com.urise.webapp.storage;

import com.urise.webapp.storage.serializer.ObjectStreamSerializer;

public class ObjectStreamPathStorageTest extends AbstractStorageTest {
    public ObjectStreamPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getName(), new ObjectStreamSerializer()));
    }
}