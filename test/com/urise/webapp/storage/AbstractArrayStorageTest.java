package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Test;

import static org.junit.Assert.fail;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest{
    private final Storage storage;

    protected AbstractArrayStorageTest(Storage storage) {
        super(storage);
        this.storage = storage;
    }

    @Test(expected = StorageException.class)
    public void checkOverflow() {
        int count = 0;
        storage.clear();
        try {
            for (int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                Resume resume = new Resume("uuid" + count);
                storage.save(resume);
                count++;
            }
        } catch (StorageException e) {
            fail("Exception was thrown earlier than expected");
        }
        storage.save(new Resume("uuid" + count));
    }
}
