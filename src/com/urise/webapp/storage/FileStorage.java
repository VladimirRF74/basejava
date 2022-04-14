package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.serializer.StreamSerializer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {
    private final File directory;
    private final StreamSerializer objectIOStreamStorage;

    protected FileStorage(File directory, StreamSerializer objectIOStreamStorage) {
        Objects.requireNonNull(directory, "directory must not be null");
        this.objectIOStreamStorage = objectIOStreamStorage;
        directory.mkdir();
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    protected List<Resume> getList() {
        List<Resume> list = new ArrayList<>(getSize());
        for (File file : getArrayOfFiles()) {
            list.add(getResume(file));
        }
        return list;
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected void saveResume(File file, Resume resume) {
        updateResume(file, resume);
    }

    @Override
    protected Resume getResume(File file) {
        try {
            return objectIOStreamStorage.doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File read error", file.getName(), e);
        }
    }

    @Override
    protected void updateResume(File file, Resume resume) {
        try {
            objectIOStreamStorage.doWrite(resume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File write error", resume.getUuid(), e);
        }
    }

    @Override
    protected void deleteResume(File file) {
        if (!file.delete()) {
            throw new StorageException("File delete error", file.getName());
        }
    }

    @Override
    protected File findSearchKey(String searchKey) {
        return new File(directory, searchKey);
    }

    @Override
    public void clear() {
        for (File file : getArrayOfFiles()) {
            deleteResume(file);
        }
    }

    @Override
    public int getSize() {
        return getArrayOfFiles().length;
    }

    private File[] getArrayOfFiles() {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException("Directory read error");
        }
        return files;
    }
}
