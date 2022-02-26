package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public final void save(Resume resume) throws ExistStorageException {
        int index = findResume(resume.getUuid());
        if (index >= 0) {
            throw new ExistStorageException(resume.getUuid());
        }
        saveResume(resume);
    }

    @Override
    public final void update(Resume resume) throws NotExistStorageException {
        String resumeUuid = resume.getUuid();
        int index = findResume(resumeUuid);
        notExistStorage(index, resumeUuid);
        updateResume(resume);
    }

    @Override
    public final void delete(String uuid) throws NotExistStorageException {
        notExistStorage(findResume(uuid), uuid);
        deleteResume(uuid);
    }

    @Override
    public final Resume get(String uuid) throws NotExistStorageException {
        notExistStorage(findResume(uuid), uuid);
        return getResume(uuid);
    }

    private void notExistStorage(int index, String uuid) throws NotExistStorageException {
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
    }

    protected abstract void saveResume(Resume resume);

    protected abstract Resume getResume(String uuid);

    protected abstract void updateResume(Resume resume);

    protected abstract void deleteResume(String key);

    protected abstract int findResume(String uuid);
}