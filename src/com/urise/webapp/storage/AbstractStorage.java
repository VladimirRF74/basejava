package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage implements Storage {
    private static final Comparator<Resume> RESUME_FULLNAME_UUID_COMPARATOR = Comparator
            .comparing(Resume::getFullName)
            .thenComparing(Resume::getUuid);

    @Override
    public final void save(Resume resume) {
        Object searchKey = findSearchKey(resume);
        if (checkSearchKey(searchKey)) {
            throw new ExistStorageException(resume.getUuid());
        }
        saveResume(resume);
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> list = getList();
        list.sort(AbstractStorage.RESUME_FULLNAME_UUID_COMPARATOR);
        return list;
    }

    @Override
    public final void update(Resume resume) {
        updateResume(notExistStorage(resume.getUuid()), resume);
    }

    @Override
    public final void delete(String uuid) {
        deleteResume(notExistStorage(uuid));
    }

    @Override
    public final Resume get(String uuid) {
        return getResume(notExistStorage(uuid));
    }

    private Object notExistStorage(Object uuid) {
        Object searchKey = findSearchKey(uuid);
        if (!checkSearchKey(searchKey)) {
            throw new NotExistStorageException((String) uuid);

        }
        return searchKey;
    }

    protected abstract List<Resume> getList();

    protected abstract boolean checkSearchKey(Object searchKey);

    protected abstract void saveResume(Resume resume);

    protected abstract Resume getResume(Object searchKey);

    protected abstract void updateResume(Object searchKey, Resume resume);

    protected abstract void deleteResume(Object searchKey);

    protected abstract Object findSearchKey(Object searchKey);
}