package com.urise.webapp.storage;

import com.urise.webapp.Config;
import com.urise.webapp.ResumeTestData;
import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.ContactType;
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
    private static final Resume RESUME_1 = ResumeTestData.create("Donald Duck");
    private static final Resume RESUME_2 = ResumeTestData.create("Peter Pen");
    private static final Resume RESUME_3 = ResumeTestData.create("Donald Duck");
    private static final Resume RESUME_4 = ResumeTestData.create("Micky Mouse");
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
        Resume newResume = new Resume(RESUME_2.getUuid(),"New Name");
        newResume.addContact(ContactType.EMAIL, "mail1@google.com");
        newResume.addContact(ContactType.SKYPE, "NewSkype");
        newResume.addContact(ContactType.PHONE, "+7 921 222-22-22");
        storage.update(newResume);
        assertEquals(3, storage.getSize());
        assertEquals(newResume, storage.get(RESUME_2.getUuid()));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws SQLException {
        storage.get("dummy");
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws SQLException {
        storage.delete(RESUME_1.getUuid());
        assertEquals(2, storage.getSize());
        storage.get(RESUME_1.getUuid());
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete("dummy");
    }

    @Test
    public void get() throws SQLException {
        assertEquals(RESUME_1.getSections(), storage.get(RESUME_1.getUuid()).getSections());
        assertEquals(RESUME_1, storage.get(RESUME_1.getUuid()));
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
        expected.add(RESUME_2);
        expected.add(RESUME_3);
        expected.sort(AbstractStorage.RESUME_FULLNAME_UUID_COMPARATOR);
        List<Resume> actual = storage.getAllSorted();
        assertEquals(expected, actual);
    }
}