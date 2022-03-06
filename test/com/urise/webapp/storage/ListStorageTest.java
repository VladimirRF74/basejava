package com.urise.webapp.storage;

import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ListStorageTest extends AbstractStorageTest {

    public ListStorageTest() {
        super(new ListStorage());
    }

    @Override
    @Category(OnlyArrays.class)
    @Test
    public void checkOverflow() {
    }
}