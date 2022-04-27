package com.urise.webapp.storage;

import com.urise.webapp.Config;
import com.urise.webapp.ResumeTestData;
import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public abstract class AbstractStorageTest {
    protected static final File STORAGE_DIR = Config.get().getStorageDir();
    private static final String UUID_1 = "uuid1";
    private static final Resume RESUME_1 = ResumeTestData.create(UUID_1, "Donald Duck");
    private static final String UUID_2 = "uuid2";
    private static final Resume RESUME_2 = ResumeTestData.create(UUID_2, "Peter Pen");
    private static final String UUID_3 = "uuid3";
    private static final Resume RESUME_3 = ResumeTestData.create(UUID_3, "Donald Duck");
    private static final String UUID_4 = "uuid4";
    private static final Resume RESUME_4 = ResumeTestData.create(UUID_4, "Micky Mouse");
    protected final Storage storage;

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
    public void save() throws SQLException {
        Resume resume = RESUME_4;
        storage.save(resume);
        assertEquals(4, storage.getSize());
        assertEquals(resume, storage.get(resume.getUuid()));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(RESUME_1);
    }

    @Test
    public void update() throws SQLException {
        storage.update(RESUME_2);
        assertEquals(3, storage.getSize());
        assertEquals(RESUME_2, storage.get(UUID_2));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws SQLException {
        storage.get("dummy");
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws SQLException {
        storage.delete(UUID_1);
        assertEquals(2, storage.getSize());
        storage.get(UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete("dummy");
    }

    @Test
    public void get() throws SQLException {
        assertEquals(RESUME_1, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws SQLException {
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