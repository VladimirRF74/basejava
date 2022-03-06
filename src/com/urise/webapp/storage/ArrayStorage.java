package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected void saveToArray(Resume resume) {
        storage[size] = resume;
    }

    @Override
    protected void deleteFromArray(int index) {
        storage[index] = storage[size - 1];
    }

    @Override
    protected Object findSearchKey(Resume resume) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(resume.getUuid())) {
                return i;
            }
        }
        return -1;
    }
}