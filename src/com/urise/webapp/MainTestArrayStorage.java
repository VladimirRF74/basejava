package com.urise.webapp;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.ArrayStorage;
import com.urise.webapp.storage.SortedArrayStorage;
import com.urise.webapp.storage.Storage;

/**
 * Test for your com.urise.webapp.storage.ArrayStorage implementation
 */
public class MainTestArrayStorage {
    private static final Storage ARRAY_STORAGE = new ArrayStorage();
    private static final Storage SORTED_ARRAY_STORAGE = new SortedArrayStorage();

    public static void main(String[] args) {
        Resume r1 = new Resume("uuid1");
        Resume r2 = new Resume("uuid2");
        Resume r3 = new Resume("uuid3");

        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r3);
        SORTED_ARRAY_STORAGE.save(r3);
        SORTED_ARRAY_STORAGE.save(r2);
        SORTED_ARRAY_STORAGE.save(r1);

        printAllSorted();
        System.out.println("Size: " + SORTED_ARRAY_STORAGE.getSize());
        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()));
        System.out.println("Size: " + ARRAY_STORAGE.getSize());
        System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));
        System.out.println("Get sorted r1: " + SORTED_ARRAY_STORAGE.get(r1.getUuid()));
        SORTED_ARRAY_STORAGE.delete(r1.getUuid());
        printAllSorted();
        System.out.println("Size: " + SORTED_ARRAY_STORAGE.getSize());
        SORTED_ARRAY_STORAGE.update(r2);
        SORTED_ARRAY_STORAGE.update(r1);
        printAllSorted();

        printAll();
        ARRAY_STORAGE.delete(r1.getUuid());
        printAll();
        ARRAY_STORAGE.save(r2);
        printAll();
        ARRAY_STORAGE.update(r3);
        printAll();
        ARRAY_STORAGE.update(r1);
        printAll();
        ARRAY_STORAGE.clear();
        printAll();

        System.out.println("Size: " + ARRAY_STORAGE.getSize());
        ARRAY_STORAGE.delete(r1.getUuid());
        ARRAY_STORAGE.update(r1);
    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAll()) {
            System.out.println(r);
        }
    }

    static void printAllSorted() {
        System.out.println("\nGet All Sorted");
        for (Resume r : SORTED_ARRAY_STORAGE.getAll()) {
            System.out.println(r);
        }
    }
}