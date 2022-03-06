package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage {
    private final Map<String, Resume> storageNewMap = new HashMap<>();

    @Override
    protected boolean checkSearchKey(Object searchKey) {
        return searchKey != null;
    }

    @Override
    protected void saveResume(Resume resume) {
        String key = resume.getUuid();
        storageNewMap.put(key, resume);
    }

    @Override
    protected Resume getResume(Object searchKey) {
        String key = String.valueOf(searchKey);
        return storageNewMap.get(key);
    }

    protected void updateResume(Object uuid, Resume resume) {
        String key = String.valueOf(uuid);
        storageNewMap.replace(key, resume);
    }

    @Override
    protected void deleteResume(Object searchKey) {
        String key = String.valueOf(searchKey);
        storageNewMap.remove(key);
    }

    protected Object findSearchKey(String searchKey) {
        for (String key : storageNewMap.keySet()) {
            if (storageNewMap.get(key).toString().equals(searchKey)) {
                return storageNewMap.get(key);
            }
        }
        return null;
    }

    @Override
    public void clear() {
        storageNewMap.clear();
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> list = new ArrayList<>(storageNewMap.values());
        list.sort(AbstractStorage.RESUME_FULLNAME_UUID_COMPARATOR);
        return list;
    }

    @Override
    public int getSize() {
        return storageNewMap.size();
    }
}