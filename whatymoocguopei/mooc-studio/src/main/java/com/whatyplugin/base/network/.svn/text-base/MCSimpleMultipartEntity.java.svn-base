package com.whatyplugin.base.network;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.message.BasicHeader;

import android.text.TextUtils;
import android.util.Log;

class MCSimpleMultipartEntity implements HttpEntity {
    class FilePart {
        public File file;
        public byte[] header;

        public FilePart(MCSimpleMultipartEntity arg2, String key, File file, String type) {
            super();
            this.header = this.createHeader(key, file.getName(), type);
            this.file = file;
        }

        public FilePart(MCSimpleMultipartEntity arg2, String key, File file, String type, String customFileName) {
            super();
            if(TextUtils.isEmpty(((CharSequence)customFileName))) {
                customFileName = file.getName();
            }

            this.header = this.createHeader(key, customFileName, type);
            this.file = file;
        }

        private byte[] createHeader(String key, String filename, String type) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                byteArrayOutputStream.write(MCSimpleMultipartEntity.this.boundaryLine);
                byteArrayOutputStream.write(MCSimpleMultipartEntity.this.createContentDisposition(key, filename));
                byteArrayOutputStream.write(MCSimpleMultipartEntity.this.createContentType(type));
                byteArrayOutputStream.write(MCSimpleMultipartEntity.TRANSFER_ENCODING_BINARY);
                byteArrayOutputStream.write(MCSimpleMultipartEntity.CR_LF);
            }
            catch(IOException e) {
                Log.e("SimpleMultipartEntity", "createHeader ByteArrayOutputStream exception", ((Throwable)e));
            }

            return byteArrayOutputStream.toByteArray();
        }

        public long getTotalLength() {
            return (((long)this.header.length)) + (this.file.length() + (((long)MCSimpleMultipartEntity.CR_LF.length)));
        }

        public void writeTo(OutputStream out) throws IOException {
            out.write(this.header);
            MCSimpleMultipartEntity.this.updateProgress(this.header.length);
            FileInputStream fileInputStream = new FileInputStream(this.file);
            byte[] bytes = new byte[4096];
            while(true) {
                int inLength = fileInputStream.read(bytes);
                if(inLength == -1) {
                    break;
                }

                out.write(bytes, 0, inLength);
                MCSimpleMultipartEntity.this.updateProgress(inLength);
            }

            out.write(MCSimpleMultipartEntity.CR_LF);
            MCSimpleMultipartEntity.this.updateProgress(MCSimpleMultipartEntity.CR_LF.length);
            out.flush();
            MCAsyncHttpClient.silentCloseInputStream(((InputStream)fileInputStream));
        }
    }

    private static byte[] CR_LF = null;
    private static final String LOG_TAG = "SimpleMultipartEntity";
    private static char[] MULTIPART_CHARS = null;
    private static final String STR_CR_LF = "\r\n";
    private static byte[] TRANSFER_ENCODING_BINARY;
    private final String boundary;
    private final byte[] boundaryEnd;
    private final byte[] boundaryLine;
    private int bytesWritten;
    private final List fileParts;
    private boolean isRepeatable;
    private final ByteArrayOutputStream out;
    private final MCResponseHandlerInterface progressHandler;
    private int totalSize;

    static {
        MCSimpleMultipartEntity.CR_LF = "\r\n".getBytes();
        MCSimpleMultipartEntity.TRANSFER_ENCODING_BINARY = "Content-Transfer-Encoding: binary\r\n".getBytes();
        MCSimpleMultipartEntity.MULTIPART_CHARS = "-_1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    }

    public MCSimpleMultipartEntity(MCResponseHandlerInterface progressHandler) {
        super();
        this.fileParts = new ArrayList();
        this.out = new ByteArrayOutputStream();
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        int v1;
        for(v1 = 0; v1 < 30; ++v1) {
            builder.append(MCSimpleMultipartEntity.MULTIPART_CHARS[random.nextInt(MCSimpleMultipartEntity.MULTIPART_CHARS.length)]);
        }

        this.boundary = builder.toString();
        this.boundaryLine = ("--" + this.boundary + "\r\n".getBytes()).getBytes();
        this.boundaryEnd = ("--" + this.boundary + "--" + "\r\n".getBytes()).getBytes();
        this.progressHandler = progressHandler;
    }

    static byte[] access$0(MCSimpleMultipartEntity arg1) {
        return arg1.boundaryLine;
    }

    static byte[] access$1(MCSimpleMultipartEntity arg1, String arg2, String arg3) {
        return arg1.createContentDisposition(arg2, arg3);
    }

    static byte[] access$2(MCSimpleMultipartEntity arg1, String arg2) {
        return arg1.createContentType(arg2);
    }

    static byte[] access$3() {
        return MCSimpleMultipartEntity.TRANSFER_ENCODING_BINARY;
    }

    static byte[] access$4() {
        return MCSimpleMultipartEntity.CR_LF;
    }

    static void access$5(MCSimpleMultipartEntity arg0, int arg1) {
        arg0.updateProgress(arg1);
    }

    public void addPart(String key, String streamName, InputStream inputStream, String type) throws IOException {
        this.out.write(this.boundaryLine);
        this.out.write(this.createContentDisposition(key, streamName));
        this.out.write(this.createContentType(type));
        this.out.write(MCSimpleMultipartEntity.TRANSFER_ENCODING_BINARY);
        this.out.write(MCSimpleMultipartEntity.CR_LF);
        byte[] v1 = new byte[4096];
        while(true) {
            int temp = inputStream.read(v1);
            if(temp == -1) {
                break;
            }

            this.out.write(v1, 0, temp);
        }

        this.out.write(MCSimpleMultipartEntity.CR_LF);
        this.out.flush();
        MCAsyncHttpClient.silentCloseOutputStream(this.out);
    }

    public void addPart(String key, File file, String type, String customFileName) {
        this.fileParts.add(new FilePart(this, key, file, this.normalizeContentType(type), customFileName));
    }

    public void addPart(String key, File file) {
        this.addPart(key, file, null);
    }

    public void addPart(String key, File file, String type) {
        this.fileParts.add(new FilePart(this, key, file, this.normalizeContentType(type)));
    }

    public void addPart(String key, String value) {
        this.addPartWithCharset(key, value, null);
    }

    public void addPart(String key, String value, String contentType) {
        try {
            this.out.write(this.boundaryLine);
            this.out.write(this.createContentDisposition(key));
            this.out.write(this.createContentType(contentType));
            this.out.write(MCSimpleMultipartEntity.CR_LF);
            this.out.write(value.getBytes());
            this.out.write(MCSimpleMultipartEntity.CR_LF);
        }
        catch(IOException e) {
            Log.e("SimpleMultipartEntity", "addPart ByteArrayOutputStream exception", ((Throwable)e));
        }
    }

    public void addPartWithCharset(String key, String value, String charset) {
        if(charset == null) {
            charset = "UTF-8";
        }

        this.addPart(key, value, "text/plain; charset=" + charset);
    }

    public void consumeContent() throws IOException, UnsupportedOperationException {
        if(this.isStreaming()) {
            throw new UnsupportedOperationException("Streaming entity does not implement #consumeContent()");
        }
    }

    private byte[] createContentDisposition(String key, String fileName) {
        return ("Content-Disposition: form-data; name=\"" + key + "\"" + "; filename=\"" + fileName + "\"" + "\r\n").getBytes();
    }

    private byte[] createContentDisposition(String key) {
        return ("Content-Disposition: form-data; name=\"" + key + "\"" + "\r\n").getBytes();
    }

    private byte[] createContentType(String type) {
        return ("Content-Type: " + this.normalizeContentType(type) + "\r\n").getBytes();
    }

    public InputStream getContent() throws IOException, UnsupportedOperationException {
        throw new UnsupportedOperationException("getContent() is not supported. Use writeTo() instead.");
    }

    public Header getContentEncoding() {
        return null;
    }

    public long getContentLength() {
        long contentLength;
        long size = ((long)this.out.size());
        Iterator iterator = this.fileParts.iterator();
        while(true) {
            if(iterator.hasNext()) {
                long totalLength = ((FilePart) iterator.next()).getTotalLength();
                if(totalLength < 0) {
                    contentLength = -1;
                }
                else {
                    size += totalLength;
                    continue;
                }
            }
            else {
                break;
            }

            return contentLength;
        }

        return size + (((long)this.boundaryEnd.length));
    }

    public Header getContentType() {
        return new BasicHeader("Content-Type", "multipart/form-data; boundary=" + this.boundary);
    }

    public boolean isChunked() {
        return false;
    }

    public boolean isRepeatable() {
        return this.isRepeatable;
    }

    public boolean isStreaming() {
        return false;
    }

    private String normalizeContentType(String type) {
        if(type == null) {
            type = "application/octet-stream";
        }

        return type;
    }

    public void setIsRepeatable(boolean isRepeatable) {
        this.isRepeatable = isRepeatable;
    }

    private void updateProgress(int count) {
        this.bytesWritten += count;
        this.progressHandler.sendProgressMessage(this.bytesWritten, this.totalSize);
    }

    public void writeTo(OutputStream outstream) throws IOException {
        this.bytesWritten = 0;
        this.totalSize = ((int)this.getContentLength());
        this.out.writeTo(outstream);
        this.updateProgress(this.out.size());
        Iterator v1 = this.fileParts.iterator();
        while(v1.hasNext()) {
            ((ByteArrayOutputStream) v1.next()).writeTo(outstream);
        }

        outstream.write(this.boundaryEnd);
        this.updateProgress(this.boundaryEnd.length);
    }
}

