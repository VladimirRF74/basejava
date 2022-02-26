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
    protected Resume getResume(String key) {
        return storageMap.get(key);
    }

    @Override
    protected void updateResume(Resume resume) {
        String key = resume.getUuid();
        storageMap.replace(key, resume);
    }

    @Override
    protected void deleteResume(String key) {
        storageMap.remove(key);
    }

    @Override
    protected int findResume(String uuid) {
        if (storageMap.containsKey(uuid)) {
            return 1;
        }
        return -1;
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
