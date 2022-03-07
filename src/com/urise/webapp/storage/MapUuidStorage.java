package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapUuidStorage extends AbstractStorage {
    private final Map<String, Resume> storageMap = new HashMap<>();

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
        String key = (String) uuid;
        storageMap.replace(key, resume);
    }

    @Override
    protected void deleteResume(Object uuid) {
        String key = String.valueOf(uuid);
        storageMap.remove(key);
    }

    @Override
    protected Object findSearchKey(Resume resume) {
        String key = resume.getUuid();
        if (storageMap.containsKey(key)) {
            return resume;
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
    public List<Resume> getAllSorted() {
        List<Resume> list = new ArrayList<>(storageMap.values());
        list.sort(AbstractStorage.RESUME_FULLNAME_UUID_COMPARATOR);
        return list;
    }

    @Override
    public int getSize() {
        return storageMap.size();
    }
}