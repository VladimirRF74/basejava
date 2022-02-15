package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected void saveToArray(Resume resume) {
        storage[size] = resume;
    }

    @Override
    protected void deleteItem(int index) {
        storage[index] = storage[size - 1];
    }

    @Override
    protected int findResume(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}