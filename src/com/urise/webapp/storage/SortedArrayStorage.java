package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void save(Resume resume) {
        int index = findResume(resume.getUuid());
        int indexFind = Math.abs(index);
        if (index >= 0) {
            System.out.printf("Sorry, %s already available\n", resume);
            return;
        } else if (size == 0) {
            storage[size] = resume;
        } else {
            System.arraycopy(storage, indexFind - 1, storage, indexFind, size);
            storage[indexFind - 1] = resume;
        }
        if (isOverflow()) return;
        size++;
    }

    @Override
    public void delete(String uuid) {
        int index = findResume(uuid);
        if (index < 0) {
            System.out.printf("Sorry, %s failed to delete\n", uuid);
        } else {
            System.arraycopy(storage, index + 1, storage, index, size - 1);
            storage[size - 1] = null;
            size--;
        }
    }

    @Override
    protected int findResume(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}