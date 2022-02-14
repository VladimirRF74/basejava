package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void saveMethod(Resume resume, int indexAbs) {
        System.arraycopy(storage, indexAbs - 1, storage, indexAbs, size);
        storage[indexAbs - 1] = resume;
    }

    @Override
    protected void deleteItem(int index) {
        System.arraycopy(storage, index + 1, storage, index, size - 1);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected int findResume(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}