package com.whatyplugin.imooc.logic.proxy;

/**
 * 参数规则：tagScope<xorLength<tagWhere
 * 文件大小要大于xorLength * 5
 * <p/>
 * 文件前面指定字符数量同后面某区域指定数量异或，结果放在文件末尾，并在文件末尾添加一定长度的混淆，解密的时候再异或回来。
 */

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;

public class MCResourceDecoderLocal {
    /**
    private RandomAccessFile raf = null;
    public static boolean IS_DEBUG = false;
    private String fileName = null;
    int tagScope = 800;//用来标记是否加密过的范围
    int xorLength = 4567;//加密区域大小
    int tagWhere = 5656;//用来标记是否加密过的文件位置
    int tagValue = 0x83128def;//用来标记是否加密的标志
    int minEndTag = 8;//结尾用于混淆的的最小区域

    final public boolean init(String path) throws Exception {
        fileName = path;
        raf = new RandomAccessFile(path, "rw");
        return true;
    }

    public void release() {
        try {
            if (raf != null) {
                raf.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isEncode() throws IOException {
        if (!checkLength()) {
            return false;
        }
        int position = getPosition();
        this.raf.seek(position);
        if (tagValue == this.raf.readInt()) {
            if (IS_DEBUG) {
                System.out.println("已经加密过了 " + this.fileName);
            }
            return true;
        } else {
            if (IS_DEBUG) {
                System.out.println("还没有加密 " + this.fileName);
            }
            return false;
        }
    }

    public void decode() throws IOException {
        if (!checkLength()) {
            return;
        }
        if (!isEncode()) {
            return;
        }

        int position = getPosition();
        ByteBuffer encodeBuffer = ByteBuffer.allocate(xorLength);
        // 读取文件末尾指定长度的内容
        this.raf.seek(this.raf.length() - xorLength - position - minEndTag);
        this.raf.read(encodeBuffer.array());

        ByteBuffer encodeBuffer2 = ByteBuffer.allocate(xorLength);
        // 读取接下来的指定数量byte
        this.raf.seek(getXorStart(position));
        this.raf.read(encodeBuffer2.array());

        // 内容异或
        xorScope(encodeBuffer, encodeBuffer2);

        // 将异或结果写入的文件开始位置。
        this.raf.seek(0);
        this.raf.write(encodeBuffer.array());

        deleteEncodeTag(position);
    }

    public void encode() throws IOException {
        if (!checkLength()) {
            return;
        }
        if (isEncode()) {
            return;
        }
        int position = getPosition();
        ByteBuffer encodeBuffer = ByteBuffer.allocate(xorLength);
        // 获取文件最前面指定数量byte
        this.raf.seek(0);
        this.raf.read(encodeBuffer.array());

        this.raf.seek(getXorStart(position));
        ByteBuffer encodeBuffer2 = ByteBuffer.allocate(xorLength);
        // 读取接下来的指定数量byte
        this.raf.read(encodeBuffer2.array());

        // 文件异或
        xorScope(encodeBuffer, encodeBuffer2);

        writeOtherInfoToFirst();
        createEncodeTag(position);

        if (checkFileLast(encodeBuffer, position + minEndTag)) {
            if (IS_DEBUG) {
                System.out.println("不是第一次加密了呢");
            }
            return;
        } else {
            if (IS_DEBUG) {
                System.out.println("第一次加密");
            }
        }

        // 内容保存到文件末尾
        this.raf.seek(this.raf.length());
        this.raf.write(encodeBuffer.array());
        createConfusionEnd(position + minEndTag);
    }

    private boolean checkFileLast(ByteBuffer encodeBuffer, int position) throws IOException {
        ByteBuffer checkBuf = ByteBuffer.allocate(xorLength);
        this.raf.seek(this.raf.length() - xorLength - position);
        this.raf.read(checkBuf.array());
        for (int i = 0; i < xorLength; i++) {
            if (encodeBuffer.array()[i] != checkBuf.array()[i]) {
                return false;
            }
        }
        return true;
    }

    private void writeOtherInfoToFirst() throws IOException {
        ByteBuffer encodeBuffer = ByteBuffer.allocate(xorLength);
        // 获取中间某部分内容对文件头进行填充
        this.raf.seek((this.raf.length() - 1 - xorLength) / 5);
        this.raf.read(encodeBuffer.array());

        // 将填充内容写入到文件头
        this.raf.seek(0);
        this.raf.write(encodeBuffer.array());
    }

    private void deleteEncodeTag(int position) throws IOException {
        this.raf.seek(this.raf.length() - xorLength - position);

    }

    private int getXorStart(int position) {
        //至少要有一个身位的偏移
        return xorLength * ((position % 3) + 1);
    }

    private void createConfusionEnd(int position) throws IOException {
        ByteBuffer encodeBuffer = ByteBuffer.allocate(position);
        this.raf.seek(this.raf.length() / 3);
        this.raf.read(encodeBuffer.array());
        // 内容保存到文件末尾
        this.raf.seek(this.raf.length());
        this.raf.write(encodeBuffer.array());
    }

    private void xorScope(ByteBuffer one, ByteBuffer two) {
        for (int i = 0; i < xorLength; i++) {
            one.array()[i] = (byte) (one.array()[i] ^ two.array()[i]);
        }
    }

    private void createEncodeTag(int position) throws IOException {
        this.raf.seek(position);
        this.raf.writeInt(tagValue);
    }

    private int getPosition() throws IOException {
        this.raf.seek(tagWhere);
        int position = this.raf.readInt();
        return position % tagScope;
    }

    private boolean checkLength() throws IOException {
        return this.raf.length() > xorLength * 5;
    }

    */
    public boolean init(String path) {
        return true;
    }

    public void decode() {
    }

    public void release() {
    }

    public void encode() {
    }
}
