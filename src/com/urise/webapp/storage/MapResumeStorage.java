package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage<Resume> {
    private final Map<String, Resume> storageResumeMap = new HashMap<>();

    @Override
    public int getSize() {
        return storageResumeMap.size();
    }

    @Override
    public void clear() {
        storageResumeMap.clear();
    }

    @Override
    protected boolean isExist(Resume searchKey) {
        return searchKey != null;
    }

    @Override
    protected void saveResume(Resume r, Resume resume) {
        storageResumeMap.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume getResume(Resume searchKey) {
        return searchKey;
    }

    protected void updateResume(Resume searchKey, Resume resume) {
        storageResumeMap.replace(searchKey.getUuid(), resume);
    }

    @Override
    protected void deleteResume(Resume searchKey) {
        storageResumeMap.remove(searchKey.getUuid());
    }

    @Override
    protected Resume findSearchKey(String searchKey) {
        String key = String.valueOf(searchKey);
        return storageResumeMap.get(key);
    }

    @Override
    protected List<Resume> getList() {
        return new ArrayList<>(storageResumeMap.values());
    }
}