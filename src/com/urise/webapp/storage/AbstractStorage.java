package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public final void update(Resume resume) {
        String resumeUuid = resume.getUuid();
        int index = findResume(resumeUuid);
        if (index < 0) {
            throw new NotExistStorageException(resumeUuid);
        }
        saveNewItem(index, resume);
    }

    @Override
    public final void delete(String uuid) {
        int index = findResume(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        deleteItem(index);
    }

    @Override
    public final Resume get(String uuid) {
        int index = findResume(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return getOf(index);
    }

    protected abstract Resume getOf(int index);

    protected abstract void saveNewItem(int index, Resume resume);

    protected abstract void deleteItem(int index);

    protected abstract int findResume(String uuid);
}