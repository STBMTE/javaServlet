package org.example.service;

import java.io.File;

public class FileService {
    public FileService(File file, long length){
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