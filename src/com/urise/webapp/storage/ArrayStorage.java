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
        if (size == 0 || storage.length > size) {
            storage[size] = resume;
            size++;
        } else System.out.println("Sorry, no place!");
    }

    public void update(Resume resume) {
        int counter = 0;
        if (size == 0) {
            System.out.println("Sorry, it's empty here!");
        } else for (int i = 0; i < size; i++) {
            if (resume.getUuid().equals(storage[i].toString())) {
                storage[i] = resume;
                break;
            } else if (size > i) {
                counter++;
                if (size == counter) {
                    System.out.println("Sorry, what we are looking for is not here!");
                }
            }
        }
    }

    public Resume get(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].toString().equals(uuid)) {
                return storage[i];
            }
        }
        return new Resume("Sorry, he's not here!");
    }

    public void delete(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].toString())) {
                storage[i] = storage[size - 1];
                storage[size - 1] = null;
                size--;
                break;
            }
        }
    }

    public Resume[] getAll() {
        Resume[] allResume = new Resume[size()];
        if (size > 0) System.arraycopy(storage, 0, allResume, 0, size);
        return allResume;
    }

    public int size() {
        size = 0;
        for (Resume resume : storage) {
            if (resume != null) {
                size++;
            } else break;
        }
        return size;
    }
}
