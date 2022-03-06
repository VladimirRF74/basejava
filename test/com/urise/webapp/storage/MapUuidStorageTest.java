package com.urise.webapp.storage;

import org.junit.Test;
import org.junit.experimental.categories.Category;

public class MapUuidStorageTest extends AbstractStorageTest {

    public MapUuidStorageTest() {
        super(new MapUuidStorage());
    }

    @Override
    @Category(OnlyArrays.class)
    @Test
    public void checkOverflow() {
    }
}
