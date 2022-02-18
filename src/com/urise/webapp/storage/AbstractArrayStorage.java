package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public final void save(Resume resume) {
        int index = findResume(resume.getUuid());
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
        if (index >= 0) {
            throw new ExistStorageException(resume.getUuid());
        }
        if (size == 0) {
            storage[size] = resume;
        } else {
            saveToArray(resume);
        }
        size++;
    }

    protected abstract void saveToArray(Resume resume);

    @Override
    public void update(Resume resume) {
        String resumeUuid = resume.getUuid();
        int index = findResume(resumeUuid);
        if (size == 0 || index < 0) {
            throw new NotExistStorageException(resumeUuid);
        } else storage[index] = resume;
    }

    @Override
    public Resume get(String uuid) {
        int index = findResume(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return storage[index];
    }

    @Override
    public final void delete(String uuid) {
        int index = findResume(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        deleteItem(index);
        size--;
    }

    protected abstract void deleteItem(int index);

    protected abstract int findResume(String uuid);

    @Override
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    @Override
    public int getSize() {
        return size;
    }
}