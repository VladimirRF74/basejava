package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

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
    protected final void deleteResume(Object searchKey) {
        deleteFromArray((int) (searchKey));
        size--;
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
    protected Resume getResume(Object searchKey) {
        int index = (int) searchKey;
        return storage[index];
    }

    @Override
    protected void updateResume(Object searchKey, Resume resume) {
        storage[(int) searchKey] = resume;
    }

    protected abstract void deleteFromArray(int index);

    protected abstract void saveToArray(Resume resume);
}