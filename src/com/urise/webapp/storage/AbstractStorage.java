package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public final void save(Resume resume) {
        int index = findResume(resume.getUuid());
        if (index >= 0) {
            throw new ExistStorageException(resume.getUuid());
        }
        saveResume(resume);
    }

    @Override
    public final void update(Resume resume) {
        String resumeUuid = resume.getUuid();
        int index = findResume(resumeUuid);
        if (index < 0) {
            throw new NotExistStorageException(resumeUuid);
        }
        updateResume(index, resume);
    }

    @Override
    public final void delete(String uuid) {
        int index = findResume(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        deleteResume(index, uuid);
    }

    @Override
    public final Resume get(String uuid) {
        int index = findResume(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return getResume(index, uuid);
    }

    protected abstract void saveResume(Resume resume);

    protected abstract Resume getResume(int index, String key);

    protected abstract void updateResume(int index, Resume resume);

    protected abstract void deleteResume(int index, String key);

    protected abstract int findResume(String uuid);
}