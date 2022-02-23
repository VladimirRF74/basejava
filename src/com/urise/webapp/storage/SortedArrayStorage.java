package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    public SortedArrayStorage() {
        super();
    }

    @Override
    protected void saveItem(Resume resume) {
        int insertionIndex = Math.abs(findResume(resume.getUuid()));
        System.arraycopy(storage, insertionIndex - 1, storage, insertionIndex, size - (insertionIndex - 1));
        storage[insertionIndex - 1] = resume;
        size++;
    }

    @Override
    protected void deleteItem(int index) {
        System.arraycopy(storage, index + 1, storage, index, size - index - 1);
        size--;
    }

    @Override
    protected int findResume(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}