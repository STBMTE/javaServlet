package org.example.model;

import java.io.File;

public class FileModel {
    public FileModel(File file, long length) {
        this.file = file;
        this.length = length;
    }

    private final File file;
    private final long length;

    public File getFile() {
        return file;
    }

    public long getLength() {
        return length;
    }
}