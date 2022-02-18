package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public abstract class AbstractArrayStorageTest {
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private final Storage storage;

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertNull("Should be null", null);
    }

    @Test
    public void save() {
        storage.clear();
        storage.save(new Resume(UUID_1));
        Assert.assertEquals(1, storage.getSize());
    }

    @Test
    public void update() {
        storage.update(new Resume(UUID_2));
        Assert.assertEquals(new Resume(UUID_2), storage.get(UUID_2));
    }

    @Test
    public void get() {
        Assert.assertEquals(new Resume(UUID_1), storage.get(UUID_1));
    }

    @Test
    public void delete() {
        storage.delete(UUID_1);
        Assert.assertEquals(2, storage.getSize());
    }

    @Test
    public void getAll() {
        Assert.assertArrayEquals(new Resume[]{new Resume(UUID_1), new Resume(UUID_2), new Resume(UUID_3)}, storage.getAll());
    }

    @Test
    public void getSize() {
        Assert.assertEquals(3, storage.getSize());
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }

    @Test(expected = NotExistStorageException.class)
    public void failedToDelete() {
        storage.delete("dummy");
    }

    @Test(expected = ExistStorageException.class)
    public void findDuplicate() {
        storage.save(new Resume(UUID_2));
    }

    @Test(expected = StorageException.class)
    public void checkOverflow() {
        storage.clear();
        try {
            for (int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                Resume resume = new Resume();
                storage.save(resume);
            }
        } catch (StorageException e) {
            Assert.fail("Exception was thrown earlier than expected");
        }
        storage.save(new Resume());
    }
}