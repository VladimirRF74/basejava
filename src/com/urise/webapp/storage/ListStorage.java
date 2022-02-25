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
    protected Resume getResume(int index, String key) {
        return resumeList.get(index);
    }

    @Override
    protected void saveResume(Resume resume) {
        resumeList.add(resume);
    }

    @Override
    protected void deleteResume(int index, String key) {
        resumeList.remove(index);
    }

    @Override
    protected int findResume(String uuid) {
        return resumeList.indexOf(new Resume(uuid));
    }

    @Override
    protected void updateResume(int index, Resume resume) {
        resumeList.set(index, resume);
    }
}