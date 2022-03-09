package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage;
    protected int size = 0;

    public AbstractArrayStorage() {
        storage = new Resume[STORAGE_LIMIT];
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected final void saveResume(Resume resume) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow");
        }
        saveToArray(resume);
        size++;
    }

    @Override
    protected final void deleteResume(Object index) {
        deleteFromArray((int) index);
        size--;
    }

    protected boolean checkObtainedKey(Object index) {
        return (int) index >= 0;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    protected Resume getResume(Object index) {
        return storage[(int) index];
    }

    @Override
    protected void updateResume(Object index, Resume resume) {
        storage[(int) index] = resume;
    }

    protected List<Resume> getList() {
        Resume[] resumes = Arrays.copyOf(storage, size);
        return Arrays.asList(resumes);
    }

    protected abstract void deleteFromArray(int index);

    protected abstract void saveToArray(Resume resume);
}