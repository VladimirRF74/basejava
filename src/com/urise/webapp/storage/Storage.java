package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.sql.SQLException;
import java.util.List;

public interface Storage {

    void clear();

    void save(Resume resume);

    void update(Resume resume);

    Resume get(String uuid) throws SQLException;

    void delete(String uuid);

    List<Resume> getAllSorted();

    int getSize();
}