package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PathStorage extends AbstractStorage<Path> {
    private final Path directory;
    private ObjectIOStreamStorage objectIOStreamStorage;

    public PathStorage(String dir, ObjectIOStreamStorage objectIOStreamStorage) {
        Objects.requireNonNull(dir, "directory must not be null");
        directory = Paths.get(dir);
        this.objectIOStreamStorage = objectIOStreamStorage;
        try {
            Files.createDirectory(directory);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
    }

    @Override
    protected List<Resume> getList() {
        try {
            return Files.list(directory).map(this::getResume).collect(Collectors.toList());
        } catch (IOException e) {
            throw new StorageException("Directory read error", directory.toAbsolutePath().toString(), e);
        }
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.isRegularFile(path);
    }

    @Override
    protected void saveResume(Path path, Resume resume) {
        updateResume(path, resume);
    }

    @Override
    protected Resume getResume(Path path) {
        try {
            return objectIOStreamStorage.doRead(new BufferedInputStream(new FileInputStream(String.valueOf(path))));
        } catch (IOException e) {
            throw new StorageException("Path read error", directory.getFileName().toString(), e);
        }
    }

    @Override
    protected void updateResume(Path path, Resume resume) {
        try {
            objectIOStreamStorage.doWrite(resume, new BufferedOutputStream(new FileOutputStream(String.valueOf(path))));
        } catch (IOException e) {
            throw new StorageException("Path write error", resume.getUuid(), e);
        }
    }

    @Override
    protected void deleteResume(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("Path delete error", directory.getFileName().toString(), e);
        }
    }

    @Override
    protected Path findSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::deleteResume);
        } catch (IOException e) {
            throw new StorageException("Path delete error");
        }
    }

    @Override
    public int getSize() {
        try {
            return (int) Files.list(directory).count();
        } catch (IOException e) {
            throw new StorageException("Directory read error", directory.toAbsolutePath().toString(), e);
        }
    }
}
