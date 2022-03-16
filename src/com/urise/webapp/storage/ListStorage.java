package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {
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
    protected Resume getResume(Integer index) {
        return resumeList.get(index);
    }

    @Override
    protected boolean isExist(Integer index) {
        return index >= 0;
    }

    @Override
    protected void saveResume(Resume resume) {
        resumeList.add(resume);
    }

    @Override
    protected void deleteResume(Integer index) {
        resumeList.remove((int) index);
    }

    @Override
    protected Integer findSearchKey(String uuid) {
        for (int i = 0; i < resumeList.size(); i++) {
            if (resumeList.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void updateResume(Integer index, Resume resume) {
        resumeList.set(index, resume);
    }
}