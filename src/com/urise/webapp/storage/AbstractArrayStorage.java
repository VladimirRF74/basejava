package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage;
    protected int size = 0;

    public AbstractArrayStorage() {
        this.storage = new Resume[STORAGE_LIMIT];
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public final void save(Resume resume) {
        int index = findResume(resume.getUuid());
        if (isOverflow(resume)) {
            throw new StorageException("Storage overflow");
        }
        if (index >= 0) {
            throw new ExistStorageException(resume.getUuid());
        }
        saveItem(resume);
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

    protected void saveNewItem(int index, Resume resume) {
        storage[index] = resume;
    }

    protected boolean isOverflow(Resume resume) {
        return size >= STORAGE_LIMIT;
    }

    protected abstract void saveItem(Resume resume);
}