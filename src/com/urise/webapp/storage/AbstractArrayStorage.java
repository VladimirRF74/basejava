package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 100000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public final void save(Resume resume) {
        int index = findResume(resume.getUuid());
        if (isOverflow()) return;
        if (index >= 0) {
            System.out.printf("Sorry, %s already available\n", resume);
            return;
        }
        if (size == 0) {
            storage[size] = resume;
        } else {
            saveToArray(resume, index);
        }
        size++;
    }

    protected abstract void saveToArray(Resume resume, int index);

    private boolean isOverflow() {
        if (size >= STORAGE_LIMIT) {
            System.out.println("Sorry, array is full");
            return true;
        }
        return false;
    }

    @Override
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

    @Override
    public Resume get(String uuid) {
        int index = findResume(uuid);
        if (index < 0) {
            System.out.printf("Sorry, he's not %s!\n", uuid);
            return null;
        }
        return storage[index];
    }

    @Override
    public final void delete(String uuid) {
        int index = findResume(uuid);
        if (index < 0) {
            System.out.printf("Sorry, %s failed to delete\n", uuid);
            return;
        }
        deleteItem(index);
        storage[size - 1] = null;
        size--;
    }

    protected abstract void deleteItem(int index);

    protected abstract int findResume(String uuid);

    @Override
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    @Override
    public int getSize() {
        return size;
    }
}