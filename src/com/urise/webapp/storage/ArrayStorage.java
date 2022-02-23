package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public class ArrayStorage extends AbstractArrayStorage {

    public ArrayStorage() {
        super();
    }

    @Override
    protected void saveItem(Resume resume) {
        storage[size] = resume;
        size++;
    }

    @Override
    protected void deleteItem(int index) {
        storage[index] = storage[size - 1];
        size--;
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