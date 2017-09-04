package com.whatyplugin.imooc.logic.utils;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.StatFs;

import com.whatyplugin.base.log.MCLog;
import com.whatyplugin.imooc.logic.contants.Contants;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
public class FileUtils {
    public FileUtils() {
        super();
    }

    public static Bitmap getBitmap(String filePath) throws IOException {
        Bitmap bitmap3 = PictureUtils.compressImage(filePath, 400);
        int orientation = PictureUtils.getImageOrientation(filePath);
        Bitmap bitmap = PictureUtils.rotateBitmap(bitmap3, orientation);
        return bitmap;
    }

    public static String FormetFileSize(long fileS) {
        String v1;
        String v2;
        long v6 = 1073741824;
        long v4 = 1;
        DecimalFormat format = new DecimalFormat("#.0");
        if (fileS == 0) {
            v2 = "0B";
        }else {
            if (fileS > 0 && fileS <1024){
                v1 = fileS +"B";
            }else if(fileS >= 1024 && fileS < 1024 *1024){
                format = new DecimalFormat("0");
                v1 = String.valueOf(format.format((int)(((double) fileS)) / 1024)) + "KB";

            }else if (fileS < v6) {
                if (fileS / 1048576 < v4) {
                    format = new DecimalFormat("0.0");
                }

                v1 = String.valueOf(format.format((((double) fileS)) / 1048576)) + "MB";
            } else {
                if (fileS / v6 < v4) {
                    format = new DecimalFormat("0.0");
                }

                v1 = String.valueOf(format.format((((double) fileS)) / 1073741824)) + "GB";
            }

            v2 = v1;
        }

        return v2;
    }


    public static boolean checkSDCardMount(String path, Context context) {
        Method method = null;
        boolean result = false;
        if (path != null) {
            Object object = context.getSystemService("storage");
            try {
                method = object.getClass().getMethod("getVolumeState", String.class);
                Object obj = method.invoke(object, path);
                result = "mounted".equals(obj.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static boolean delAllFile(String path) {
        boolean result = false;
        File newFile = new File(path);
        if (!newFile.exists()) {
            result = false;
        } else if (!newFile.isDirectory()) {
            result = false;
        } else {
            String[] arr = newFile.list();
            for (int i = 0; i < arr.length; ++i) {
                File file = path.endsWith(File.separator) ? new File(String.valueOf(path) + arr[i]) : new File(String.valueOf(path) + File.separator + arr[i]);
                if (file.isFile()) {
                    file.delete();
                }
                if (file.isDirectory()) {
                    FileUtils.delAllFile(String.valueOf(path) + "/" + arr[i]);
                    FileUtils.delFolder(String.valueOf(path) + "/" + arr[i]);
                    result = true;
                }
            }
        }
        return result;
    }

    public static void delFolder(String folderPath) {
        try {
            FileUtils.delAllFile(folderPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
    }

    public static long getAllSDSize(String path) {
        long result;
        try {
            StatFs statFs = new StatFs(path);
            long size = ((long) statFs.getBlockSize());
            long count = ((long) statFs.getBlockCount());
            NumberFormat.getNumberInstance().setMaximumFractionDigits(2);
            result = count * size;
        } catch (Exception v6) {
            result = 0;
        }
        return result;
    }

    public static long getAvailaleSDSize(String path) {
        long result;
        try {
            StatFs statFs = new StatFs(path);
            long size = ((long) statFs.getBlockSize());
            long blocks = ((long) statFs.getAvailableBlocks());
            NumberFormat.getNumberInstance().setMaximumFractionDigits(2);
            result = blocks * size;
        } catch (Exception v6) {
            result = 0;
        }
        return result;
    }

    public static long getFileSize(String filepath) throws Exception {
        long result = 0;
        File[] array = new File(filepath).listFiles();
        if (array != null) {
            for (int i = 0; i < array.length; ++i) {
                result += array[i].isDirectory() ? FileUtils.getFileSize(array[i].getPath()) : array[i].length();
            }
        }
        return result;
    }

    public long getFileSizes(File f) throws Exception {
        long result = 0;
        if (f.exists()) {
            result = ((long) new FileInputStream(f).available());
        } else {
            f.createNewFile();
        }
        return result;
    }

    public long getlist(File f) {
        File[] array = f.listFiles();
        long length = ((long) array.length);
        for (int i = 0; i < array.length; ++i) {
            if (array[i].isDirectory()) {
                length = length + this.getlist(array[i]) - 1;
            }
        }
        return length;
    }

    public static void saveStringToFile(String fILE_PATH, String cOURSE_TYPE_FILE_NAME, String json) {
    }

    public static boolean isTwoSdcard(Context context) {
        boolean result = true;
        String[] storagelist = FileUtils.storageList(context);
        if (storagelist == null) {
            result = false;
        } else if (storagelist.length < 2) {
            result = false;
        } else if (!FileUtils.checkSDCardMount(storagelist[1], context)) {
            result = false;
        }
        return result;
    }

    public static String[] storageList(Context context) {
        Object object = null;
        Method method = null;
        Object storage = context.getSystemService("storage");
        try {
            method = storage.getClass().getMethod("getVolumePaths");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        if (method != null) {
            try {
                object = method.invoke(storage);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        return ((String[]) object);
    }

    /**
     * 是否存在该文件？
     *
     * @param filename
     * @param context
     * @return
     */
    public static boolean isFileExistsInPhoneOrSdcard(String filename, Context context) {
        boolean result = true;
        if (filename == null || (filename.isEmpty())) {
            result = false;
        } else if (!new File(Contants.VIDEO_PATH, filename).exists()) {
            result = false;
        }
        return result;
    }

    /**
     * 下载的文件路径？
     *
     * @param filename
     * @param context
     * @return
     */
    public static String getFilePathByName(String filename, Context context) {
        if (filename == null || (filename.isEmpty())) {
            return "";
        }
        return getVideoFullPath(filename);
    }

    public static long getAvailaleSDSize(char[] cs) {
        return 0;
    }

    public static String getStringFromFile(String fILE_PATH, String cOURSE_TYPE_FILE_NAME) {
        return null;
    }

    public static void copyFile(File sourceFile, File targetFile) throws IOException {
        int length = 5120;
        BufferedOutputStream outputStream = null;
        BufferedInputStream inputStream = null;
        try {
            inputStream = new BufferedInputStream(new FileInputStream(sourceFile));
            outputStream = new BufferedOutputStream(new FileOutputStream(targetFile));
        } catch (Exception e) {
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
            return;
        }
        try {
            byte[] array = new byte[length];
            while (true) {
                int len = inputStream.read(array);
                if (len == -1) {
                    break;
                }
                outputStream.write(array, 0, len);
            }
            outputStream.flush();
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        } catch (Exception v6) {
            v6.printStackTrace();
        }
    }

    public static String FormetFileSizeSmall(long fileS) {
        String result;
        long gb = 1073741824;
        long mb = 1024 * 1024;
        DecimalFormat format = new DecimalFormat("#.0");
        if (fileS < 1024) {
            result = fileS + "B";
        } else if (fileS < mb) {
            result = fileS / 1024 + "KB";
        } else if (fileS < gb) {
            format = new DecimalFormat("0.0");
            result = String.valueOf(format.format((((double) fileS)) / mb)) + "MB";
        } else {
            result = "文件太大了吧";
        }
        return result;
    }

    public static String getTakePhotoPath() {
        long timeMillis = System.currentTimeMillis();
        return getCacheFile().getPath() + File.separator + timeMillis + ".jpg";
    }

    /**
     * 得到缓存文件夹
     */
    public static File getCacheFile() {
        File f = new File(Environment.getExternalStorageDirectory().getPath() + "/whaty/");
        f.mkdirs();
        StringBuffer sb = new StringBuffer();
        sb.append(Environment.getExternalStorageDirectory().getPath());
        sb.append(File.separator);
        sb.append("whaty");
        sb.append(File.separator);
        sb.append("takePic");
        File file = new File(sb.toString());
        return file;
    }

    public static String saveImage(final Bitmap bitmap) throws IOException, FileNotFoundException {
        String filePath = "";
        // SD卡可用
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            // 创建文件目录
            File f = new File(getRootFilePath());
            f.mkdirs();
            // 获取成功
            if (bitmap != null) {
                // 获取当前系统时间
                Calendar cl = Calendar.getInstance();
                cl.setTime(Calendar.getInstance().getTime());
                long milliseconds = cl.getTimeInMillis();
                // 当前系统时间作为文件名
                String fileName = String.valueOf(milliseconds) + ".jpg";
                // 保存图片
                // 创建文件
                File file = new File(getRootFilePath(), fileName);
                file.createNewFile();
                if (!file.isFile()) {
                    throw new FileNotFoundException();
                }
                filePath = file.getAbsolutePath();
                FileOutputStream fOut = null;
                fOut = new FileOutputStream(file);
                // 将图片转为输出流
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                // 关闭输出流
                fOut.flush();
                fOut.close();
            }
        }
        return filePath;
    }

    private static String getRootFilePath() {
        File f = new File(Environment.getExternalStorageDirectory().getPath() + "/whaty/");
        f.mkdirs();
        StringBuffer stbPath = new StringBuffer();
        stbPath.append(Environment.getExternalStorageDirectory().getPath());
        stbPath.append(File.separator);
        stbPath.append("whaty");
        stbPath.append(File.separator);
        stbPath.append("smallPic");
        stbPath.append(File.separator);
        return stbPath.toString();
    }

    /**
     * 获取视频全路径
     *
     * @param fileName
     * @return
     */
    public static String getVideoFullPath(String fileName) {
        String url = null;
        String path = Contants.VIDEO_PATH;
        File file = new File(path, fileName);
        if (file.exists()) {
            url = path + "/" + fileName;
        } else {
            url = "";
        }
        return url;
    }

    public static List<String> filelist = new ArrayList<String>();

    /**
     * 通过递归得到某一路径下所有的目录及其文件
     *
     * @param filePath
     * @param typeName
     */
    public static void getFiles(String filePath, String typeName) {
        File root = new File(filePath);
        File[] files = root.listFiles();
        String path = "";
        if (files == null) {
            return;
        }
        for (File file : files) {
            path = file.getAbsolutePath();
            if (file.isDirectory()) {
                getFiles(path, typeName);
            } else {
                if (path.endsWith(typeName)) {
                    filelist.add(path);
                } else {
                    System.out.println("不在处理范围内的文件：" + path);
                }
            }
        }
    }

    /**
     * 按照路径复制文件
     *
     * @param org
     * @param target
     */
    public static void fileChannelCopyByPath(String org, String target) {
        File orgFile = new File(org);
        if (org.equals(target)) {
            return;
        }
        if (orgFile.exists()) {
            File targetFile = new File(target);
            targetFile.getParentFile().mkdirs();
            fileChannelCopy(orgFile, targetFile);
        }
    }

    /**
     * 使用文件通道的方式复制文件
     *
     * @param s 源文件
     * @param t 复制到的新文件
     */
    public static void fileChannelCopy(File s, File t) {
        if (s == null || t == null || s.length() == t.length()) {
            return;
        }
        FileInputStream fi = null;
        FileOutputStream fo = null;
        FileChannel in = null;
        FileChannel out = null;
        try {
            fi = new FileInputStream(s);
            fo = new FileOutputStream(t);
            in = fi.getChannel();// 得到对应的文件通道
            out = fo.getChannel();// 得到对应的文件通道
            in.transferTo(0, in.size(), out);// 连接两个通道，并且从in通道读取，然后写入out通道
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fi.close();
                in.close();
                fo.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 目标卡是否有足够空间
     *
     * @param 文件集合
     * @param path
     */
    public static boolean checkSpace(List<String> filelist, String path, Context mContext) {
        long maxSize = FileUtils.getAvailaleSDSize(path);
        boolean result = true;
        int length = 0;
        for (String str : filelist) {
            try {
                File file = new File(str);
                if (file.exists()) {
                    length += file.length();
                    if (length > maxSize) {
                        result = false;
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    //递归删除指定路径下的所有文件
    public static void deleteAll(File file) {
        if (file.isFile() && file.exists()) {
            file.delete();
        } else if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                deleteAll(f);//递归删除每一个文件
                f.delete();//删除该文件夹
            }
        }
    }

    /**
     * 根据文件夹和名字删除文件
     *
     * @param path
     * @param fileName
     */
    public static void deleteFileByPathAndName(String path, String fileName) {
        try {
            File file = new File(path, fileName);
            if (!file.exists()) {
                MCLog.d(FileUtils.class.getName(), " 删除文件：" + file.getAbsolutePath() + ", 不存在该文件！！！");
                return;
            } else {
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
