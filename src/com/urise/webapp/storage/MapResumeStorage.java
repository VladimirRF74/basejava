package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage {
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
    protected boolean isExist(Object searchKey) {
        return searchKey != null;
    }

    @Override
    protected void saveResume(Resume resume) {
        String key = resume.getUuid();
        storageResumeMap.put(key, resume);
    }

    @Override
    protected Resume getResume(Object searchKey) {
        return (Resume) searchKey;
    }

    protected void updateResume(Object searchKey, Resume resume) {
        String key = String.valueOf(searchKey);
        storageResumeMap.replace(key, resume);
    }

    @Override
    protected void deleteResume(Object searchKey) {
        String key = String.valueOf(searchKey);
        storageResumeMap.remove(key);
    }

    @Override
    protected Object findSearchKey(String searchKey) {
        String key = String.valueOf(searchKey);
        return storageResumeMap.get(key);
    }

    @Override
    protected List<Resume> getList() {
        return new ArrayList<>(storageResumeMap.values());
    }
}