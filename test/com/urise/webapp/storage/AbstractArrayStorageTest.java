package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Test;

import static org.junit.Assert.fail;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {

    protected AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void checkOverflow() {
        int i = 0;
        storage.clear();
        try {
            for (; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                Resume resume = new Resume("uuid" + i, "fullname" + i);
                storage.save(resume);
            }
        } catch (StorageException e) {
            fail("Exception was thrown earlier than expected");
        }
        storage.save(new Resume("uuid", "fullname"));
    }
}
