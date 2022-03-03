package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Map;
import java.util.TreeMap;

public class MapStorage extends AbstractStorage {
    private final Map<String, Resume> storageMap = new TreeMap<>();

    @Override
    protected void saveResume(Resume resume) {
        String key = resume.getUuid();
        storageMap.put(key, resume);
    }

    @Override
    protected Resume getResume(Object uuid) {
        String key = (String) uuid;
        return storageMap.get(key);
    }

    @Override
    protected void updateResume(Object uuid, Resume resume) {
        String key = (String) uuid;
        storageMap.replace(key, resume);
    }

    @Override
    protected void deleteResume(Object uuid) {
        String key = (String) uuid;
        storageMap.remove(key);
    }

    @Override
    protected Object findSearchKey(String uuid) {
        if (storageMap.containsKey(uuid)) {
            return uuid;
        }
        return null;
    }

    public boolean checkSearchKey(Object searchKey) {
        return searchKey != null;
    }

    @Override
    public void clear() {
        storageMap.clear();
    }

    @Override
    public Resume[] getAll() {
        return storageMap.values().toArray(Resume[]::new);
    }

    @Override
    public int getSize() {
        return storageMap.size();
    }
}