package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public abstract class AbstractArrayStorageTest {
    private static final String UUID_1 = "uuid1";
    private static final Resume RESUME_1 = new Resume(UUID_1);
    private static final String UUID_2 = "uuid2";
    private static final Resume RESUME_2 = new Resume(UUID_2);
    private static final String UUID_3 = "uuid3";
    private static final Resume RESUME_3 = new Resume(UUID_3);
    private final Storage storage;

    protected AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void clear() {
        storage.clear();
        assertEquals(0, storage.getSize());
    }

    @Test
    public void save() {
        Resume resume = new Resume();
        storage.save(resume);
        assertEquals(4, storage.getSize());
        assertSame(resume, storage.get(resume.getUuid()));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(new Resume(UUID_1));
    }

    @Test
    public void update() {
        Resume resume = new Resume(UUID_2);
        storage.update(resume);
        assertSame(resume, storage.get(UUID_2));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        Resume resume = new Resume("dummy");
        storage.update(resume);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_1);
        assertEquals(2, storage.getSize());
        storage.get(UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete("dummy");
    }

    @Test
    public void get() {
        assertEquals(RESUME_1, storage.get(UUID_1));
        assertEquals(RESUME_2, storage.get(UUID_2));
        assertEquals(RESUME_3, storage.get(UUID_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }

    @Test
    public void getSize() {
        assertEquals(3, storage.getSize());
    }

    @Test
    public void getAll() {
        Resume[] expected = new Resume[]{RESUME_1, RESUME_2, RESUME_3};
        assertEquals(3, expected.length);
        assertArrayEquals(expected, storage.getAll());
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
            fail("Exception was thrown earlier than expected");
        }
        storage.save(new Resume());
    }
}