package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10000;

    public AbstractArrayStorage() {
        super(new Resume[STORAGE_LIMIT]);
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    protected Resume getOf(int index) {
        return storage[index];
    }

    protected void saveUpdate(int index, Resume resume) {
        storage[index] = resume;
    }

    protected boolean isExist(Resume resume) {
        return findResume(resume.getUuid()) >= 0;
    }

    @Override
    protected boolean isOverflow(Resume resume) {
        return size >= STORAGE_LIMIT;
    }
}