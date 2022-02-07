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
        if (size == 0) {
            writeToCell(resume);
            return;
        }
        if (size >= storage.length) {
            System.out.println("Sorry, array is full");
            return;
        }
        if (findResume(resume)) {
            System.out.printf("Sorry, %s already available \n", resume);
        } else {
            writeToCell(resume);
        }
    }

    public void update(Resume resume) {
        int counter = 0;
        if (size == 0) {
            System.out.println("Sorry, it's empty here!");
        } else for (int i = 0; i < size; i++) {
            if (resume.getUuid().equals(storage[i].toString())) {
                storage[i] = resume;
                return;
            } else if (size > i) {
                counter++;
                if (size == counter) {
                    System.out.printf("Sorry, what we are looking for is not %s! \n", resume.getUuid());
                }
            }
        }
    }

    public Resume get(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].toString())) {
                return storage[i];
            }
        }
        System.out.printf("Sorry, he's not %s! \n", uuid);
        return null;
    }

    public void delete(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].toString())) {
                storage[i] = storage[size - 1];
                storage[size - 1] = null;
                size--;
                return;
            }
        }
        System.out.printf("Sorry, %s failed to delete \n", uuid);
    }

    public Resume[] getAll() {
        Resume[] allResume = new Resume[size];
        if (size > 0) System.arraycopy(storage, 0, allResume, 0, size);
        return allResume;
    }

    public int getSize() {
        return size;
    }

    private boolean findResume(Resume resume) {
        boolean rb = false;
        for (int i = 0; i < size; i++) {
            rb = storage[i].getUuid().equals(resume.getUuid());
        }
        return rb;
    }

    private void writeToCell(Resume resume) {
        storage[size] = resume;
        size++;
    }
}
