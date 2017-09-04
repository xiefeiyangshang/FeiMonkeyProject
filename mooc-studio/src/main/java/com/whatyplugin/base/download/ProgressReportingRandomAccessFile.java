package com.whatyplugin.base.download;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class ProgressReportingRandomAccessFile extends RandomAccessFile {
    public interface RandomAccessFileWriteListener {
        void fileWriteListener(int arg1);
    }

    private int progress;

    public ProgressReportingRandomAccessFile(File file, String mode) throws FileNotFoundException {
        super(file, mode);
        this.progress = 0;
    }

    public void write(byte[] buffer, int offset, int count) throws IOException {
        super.write(buffer, offset, count);
        this.progress += count;
    }
}

