package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public final void save(Resume resume) {
        Object searchKey = findSearchKey(resume.getUuid());
        if (searchKey instanceof String || (int) searchKey >= 0) {
            throw new ExistStorageException(resume.getUuid());

        }
        saveResume(resume);
    }

    @Override
    public final void update(Resume resume) {
        String resumeUuid = resume.getUuid();
        updateResume(notExistStorage(resumeUuid), resume);
    }

    @Override
    public final void delete(String uuid) {
        deleteResume(notExistStorage(uuid));
    }

    @Override
    public final Resume get(String uuid) {
        return getResume(notExistStorage(uuid));
    }

    private Object notExistStorage(String uuid) {
        Object searchKey = findSearchKey(uuid);
        if (searchKey instanceof Number) {
            if ((int) searchKey < 0) {
                throw new NotExistStorageException(uuid);
            }
        }
        return searchKey;
    }

    protected abstract void saveResume(Resume resume);

    protected abstract Resume getResume(Object searchKey);

    protected abstract void updateResume(Object searchKey, Resume resume);

    protected abstract void deleteResume(Object searchKey);

    protected abstract Object findSearchKey(String uuid);
}