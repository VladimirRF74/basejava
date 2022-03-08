package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public abstract class AbstractStorageTest {
    private static final String UUID_1 = "uuid1";
    private static final Resume RESUME_1 = new Resume(UUID_1, "Donald Duck");
    private static final String UUID_2 = "uuid2";
    private static final Resume RESUME_2 = new Resume(UUID_2, "Peter Pen");
    private static final String UUID_3 = "uuid3";
    private static final Resume RESUME_3 = new Resume(UUID_3, "Donald Duck");
    private static final String UUID_4 = "uuid4";
    private static final Resume RESUME_4 = new Resume(UUID_4, "Micky Mouse");
    private final Storage storage;

    protected AbstractStorageTest(Storage storage) {
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
        Resume resume = RESUME_4;
        storage.save(resume);
        assertEquals(4, storage.getSize());
        assertSame(resume, storage.get(resume.getUuid()));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(RESUME_1);
    }

    @Test
    public void update() {
        storage.update(RESUME_2);
        assertEquals(3, storage.getSize());
        assertSame(RESUME_2, storage.get(UUID_2));
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
    public void getAllSorted() {
        List<Resume> expected = new ArrayList<>();
        expected.add(RESUME_1);
        expected.add(RESUME_3);
        expected.add(RESUME_2);
        List<Resume> actual = storage.getAllSorted();
        assertEquals(expected, actual);
    }
}