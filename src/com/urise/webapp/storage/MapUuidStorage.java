package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapUuidStorage extends AbstractStorage {
    private final Map<String, Resume> storageMap = new HashMap<>();

    @Override
    public void clear() {
        storageMap.clear();
    }

    @Override
    public int getSize() {
        return storageMap.size();
    }

    @Override
    protected void saveResume(Resume resume) {
        String key = resume.getUuid();
        storageMap.put(key, resume);
    }

    @Override
    protected Resume getResume(Object uuid) {
        String key = String.valueOf(uuid);
        return storageMap.get(key);
    }

    @Override
    protected void updateResume(Object uuid, Resume resume) {
        String key = String.valueOf(uuid);
        storageMap.replace(key, resume);
    }

    @Override
    protected void deleteResume(Object uuid) {
        String key = String.valueOf(uuid);
        storageMap.remove(key);
    }

    @Override
    protected Object findSearchKey(String uuid) {
        String key = String.valueOf(uuid);
        if (storageMap.containsKey(key)) {
            return uuid;
        }
        return null;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return searchKey != null;
    }

    @Override
    protected List<Resume> getList() {
        return new ArrayList<>(storageMap.values());
    }
}