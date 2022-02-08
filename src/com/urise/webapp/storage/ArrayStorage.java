package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class ArrayStorage {
    private final Resume[] storage = new Resume[10000];
    private int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume resume) {
        if (size >= storage.length) {
            System.out.println("Sorry, array is full");
            return;
        }
        if (findResume(resume.getUuid()) < size) {
            System.out.printf("Sorry, %s already available\n", resume);
        } else {
            storage[size] = resume;
            size++;
        }
    }

    public void update(Resume resume) {
        String resumeUuid = resume.getUuid();
        int index = findResume(resumeUuid);
        if (size == 0) {
            System.out.println("Sorry, it's empty here!");
            return;
        }
        if (index < size) {
            storage[index] = resume;
        } else System.out.printf("Sorry, there is no  %s here!\n", resumeUuid);
    }

    public Resume get(String uuid) {
        int index = findResume(uuid);
        if (index < size) {
            return storage[index];
        }
        System.out.printf("Sorry, he's not %s!\n", uuid);
        return null;

    }

    public void delete(String uuid) {
        int index = findResume(uuid);
        if (index >= 0 & size != 0) {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        } else System.out.printf("Sorry, %s failed to delete\n", uuid);
    }

    public Resume[] getAll() {
        Resume[] allResume = new Resume[size];
        if (size > 0) System.arraycopy(storage, 0, allResume, 0, size);
        return allResume;
    }

    public int getSize() {
        return size;
    }

    private int findResume(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return size;
    }
}
