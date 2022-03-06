package com.urise.webapp.storage;

import org.junit.Test;
import org.junit.experimental.categories.Category;

public class MapResumeStorageTest extends AbstractStorageTest {
    public MapResumeStorageTest() {
        super(new MapResumeStorage());
    }

    @Override
    @Category(OnlyArrays.class)
    @Test
    public void checkOverflow() {
    }
}
