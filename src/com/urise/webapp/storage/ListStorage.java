package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    protected List<Resume> resumeList = new ArrayList<>();

    public ListStorage() {
        super();
    }

    @Override
    public void clear() {
        resumeList.clear();
    }

    @Override
    public void save(Resume resume) {
        if (resumeList.contains(resume)) {
            throw new ExistStorageException(resume.getUuid());
        }
        saveItem(resume);
    }

    @Override
    public Resume[] getAll() {
        return resumeList.toArray(Resume[]::new);
    }

    @Override
    public int getSize() {
        return resumeList.size();
    }

    @Override
    protected Resume getOf(int index) {
        return resumeList.get(index);
    }

    protected void saveItem(Resume resume) {
        resumeList.add(resume);
    }

    @Override
    protected void deleteItem(int index) {
        resumeList.remove(index);
    }

    @Override
    protected int findResume(String uuid) {
        return resumeList.indexOf(new Resume(uuid));
    }

    @Override
    protected void saveNewItem(int index, Resume resume) {
        resumeList.add(index, resume);
    }
}