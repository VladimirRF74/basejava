package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 100000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public abstract void save(Resume resume);

    public void update(Resume resume) {
        String resumeUuid = resume.getUuid();
        int index = findResume(resumeUuid);
        if (size == 0) {
            System.out.println("Sorry, it's empty here!");
            return;
        }
        if (index < 0) {
            System.out.printf("Sorry, there is no  %s here!\n", resumeUuid);
        } else storage[index] = resume;
    }

    public Resume get(String uuid) {
        int index = findResume(uuid);
        if (index < 0) {
            System.out.printf("Sorry, he's not %s!\n", uuid);
            return null;
        }
        return storage[index];
    }

    public abstract void delete(String uuid);

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    public int getSize() {
        return size;
    }

    protected abstract int findResume(String uuid);

    protected boolean isOverflow() {
        if (size >= STORAGE_LIMIT) {
            System.out.println("Sorry, array is full");
            return true;
        } else return false;
    }
}