package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<SK> implements Storage {
    private static final Comparator<Resume> RESUME_FULLNAME_UUID_COMPARATOR = Comparator
            .comparing(Resume::getFullName)
            .thenComparing(Resume::getUuid);
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    @Override
    public final void save(Resume resume) {
        LOG.info("Save " + resume);
        SK searchKey = findSearchKey(resume.getUuid());
        if (isExist(searchKey)) {
            LOG.warning("Resume " + resume + " exist");
            throw new ExistStorageException(resume.getUuid());
        }
        saveResume(resume);
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted");
        List<Resume> list = getList();
        list.sort(AbstractStorage.RESUME_FULLNAME_UUID_COMPARATOR);
        return list;
    }

    @Override
    public final void update(Resume resume) {
        LOG.info("Update " + resume);
        updateResume(getSearchKeyIfExist(resume.getUuid()), resume);
    }

    @Override
    public final void delete(String uuid) {
        LOG.info("Delete " + uuid);
        deleteResume(getSearchKeyIfExist(uuid));
    }

    @Override
    public final Resume get(String uuid) {
        LOG.info("Get " + uuid);
        return getResume(getSearchKeyIfExist(uuid));
    }

    private SK getSearchKeyIfExist(String uuid) {
        SK searchKey = findSearchKey(uuid);
        if (!isExist(searchKey)) {
            LOG.warning("Resume " + uuid + " not exist");
            throw new NotExistStorageException(uuid);

        }
        return searchKey;
    }

    protected abstract List<Resume> getList();

    protected abstract boolean isExist(SK searchKey);

    protected abstract void saveResume(Resume resume);

    protected abstract Resume getResume(SK searchKey);

    protected abstract void updateResume(SK searchKey, Resume resume);

    protected abstract void deleteResume(SK searchKey);

    protected abstract SK findSearchKey(String searchKey);
}