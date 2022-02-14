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

    public final void save(Resume resume) {
        int index = findResume(resume.getUuid());
        int indexAbs = Math.abs(index);
        if (isOverflow()) return;
        if (index >= 0) {
            System.out.printf("Sorry, %s already available\n", resume);
            return;
        } else if (size == 0) {
            storage[size] = resume;
        } else {
            saveMethod(resume, indexAbs);
        }
        size++;
    }

    protected abstract void saveMethod(Resume resume, int indexAbs);

    protected boolean isOverflow() {
        if (size >= STORAGE_LIMIT) {
            System.out.println("Sorry, array is full");
            return true;
        } else return false;
    }

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

    public final void delete(String uuid) {
        if (error(uuid) < 0) {
            return;
        }
        deleteItem(error(uuid));
    }

    protected int error(String uuid) {
        int index = findResume(uuid);
        if (index < 0) {
            System.out.printf("Sorry, %s failed to delete\n", uuid);
        }
        return index;
    }

    protected abstract int findResume(String uuid);

    protected abstract void deleteItem(int index);

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    public int getSize() {
        return size;
    }
}