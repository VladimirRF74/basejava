package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private final List<Resume> resumeList = new ArrayList<>();

    @Override
    public void clear() {
        resumeList.clear();
    }

    @Override
    public int getSize() {
        return resumeList.size();
    }

    @Override
    protected List<Resume> getList() {
        return resumeList;
    }

    @Override
    protected Resume getResume(Object index) {
        return resumeList.get((int) index);
    }

    @Override
    protected boolean checkSearchKey(Object searchKey) {
        return (int) searchKey >= 0;
    }

    @Override
    protected void saveResume(Resume resume) {
        resumeList.add(resume);
    }

    @Override
    protected void deleteResume(Object index) {
        resumeList.remove((int) index);
    }

    @Override
    protected Object findSearchKey(Object uuid) {
        return resumeList.indexOf(new Resume(String.valueOf(uuid)));
    }

    @Override
    protected void updateResume(Object index, Resume resume) {
        resumeList.set((int) index, resume);
    }
}