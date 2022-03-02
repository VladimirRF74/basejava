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
    protected final void deleteResume(Object uuid) {
        deleteFromArray((int) uuid);
        size--;
    }

    protected boolean checkSearchKey(Object searchKey) {
        return (int) searchKey >= 0;
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
    protected Resume getResume(Object uuid) {
        return storage[(int) uuid];
    }

    @Override
    protected void updateResume(Object uuid, Resume resume) {
        storage[(int) uuid] = resume;
    }

    protected abstract void deleteFromArray(int index);

    protected abstract void saveToArray(Resume resume);
}