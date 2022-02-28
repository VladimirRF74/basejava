package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    protected List<Resume> resumeList = new ArrayList<>();

    @Override
    public void clear() {
        resumeList.clear();
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
    protected Resume getResume(Object searchKey) {
        int index = (int) searchKey;
        return resumeList.get(index);
    }

    @Override
    protected void saveResume(Resume resume) {
        resumeList.add(resume);
    }

    @Override
    protected void deleteResume(Object searchKey) {
        resumeList.remove((int) searchKey);
    }

    @Override
    protected Object findSearchKey(String uuid) {
        Object searchKey = resumeList.indexOf(new Resume(uuid));
        return searchKey;
    }

    @Override
    protected void updateResume(Object searchKey, Resume resume) {
        resumeList.set((int) searchKey, resume);
    }
}