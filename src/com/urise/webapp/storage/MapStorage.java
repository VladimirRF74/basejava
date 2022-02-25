package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    Map<String, Resume> storageMap = new HashMap<>();

    @Override
    protected void saveResume(Resume resume) {
        String key = resume.getUuid();
        storageMap.put(key, resume);
    }

    @Override
    protected Resume getResume(int index, String key) {
        return storageMap.get(key);
    }

    @Override
    protected void updateResume(int index, Resume resume) {
        String key = resume.getUuid();
        storageMap.replace(key, resume);
    }

    @Override
    protected void deleteResume(int index, String key) {
        storageMap.remove(key);
    }

    @Override
    public int findResume(String uuid) {
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
