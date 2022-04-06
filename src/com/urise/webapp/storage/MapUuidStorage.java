package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapUuidStorage extends AbstractStorage<String> {
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
    protected void saveResume(String uuid, Resume resume) {
        storageMap.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume getResume(String uuid) {
        return storageMap.get(uuid);
    }

    @Override
    protected void updateResume(String uuid, Resume resume) {
        storageMap.replace(uuid, resume);
    }

    @Override
    protected void deleteResume(String uuid) {
        storageMap.remove(uuid);
    }

    @Override
    protected String findSearchKey(String uuid) {
        return (storageMap.containsKey(uuid)) ? uuid : null;
    }

    @Override
    protected boolean isExist(String searchKey) {
        return searchKey != null;
    }

    @Override
    protected List<Resume> getList() {
        return new ArrayList<>(storageMap.values());
    }
}