package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {
    protected final Resume[] storage;
    protected int size = 0;

    protected AbstractStorage(Resume[] storage) {
        this.storage = storage;
    }

    public AbstractStorage() {
        storage = new Resume[0];
    }

    @Override
    public final void save(Resume resume) {
        if (isOverflow(resume)) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
        if (isExist(resume)) {
            throw new ExistStorageException(resume.getUuid());
        }
        saveToArray(resume);
        size++;
    }

    @Override
    public void update(Resume resume) {
        String resumeUuid = resume.getUuid();
        int index = findResume(resumeUuid);
        if (index < 0) {
            throw new NotExistStorageException(resumeUuid);
        }
        saveUpdate(index, resume);
    }

    @Override
    public final void delete(String uuid) {
        int index = findResume(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        deleteFromArray(index);
        size--;
    }

    @Override
    public Resume get(String uuid) {
        int index = findResume(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return storage[index];
    }

    protected abstract void saveUpdate(int index, Resume resume);

    protected abstract void deleteFromArray(int index);

    protected abstract boolean isExist(Resume resume);

    protected abstract boolean isOverflow(Resume resume);

    protected abstract void saveToArray(Resume resume);

    protected abstract int findResume(String uuid);
}