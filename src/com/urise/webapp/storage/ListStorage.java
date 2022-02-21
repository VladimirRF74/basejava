package com.urise.webapp.storage;

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
    protected void deleteFromArray(int index) {
        resumeList.remove(index);
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

    @Override
    protected boolean isExist(Resume resume) {
        return resumeList.contains(resume);
    }

    @Override
    protected boolean isOverflow(Resume resume) {
        return resumeList.size() >= Integer.MAX_VALUE;
    }

    @Override
    protected void saveToArray(Resume resume) {
        resumeList.add(resume);
    }

    @Override
    protected int findResume(String uuid) {
        return resumeList.indexOf(new Resume(uuid));
    }

    @Override
    protected void saveUpdate(int index, Resume resume) {
        resumeList.add(index, resume);
    }
}