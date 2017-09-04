package com.whatyplugin.base.storage;



import java.io.File;
import java.io.IOException;

public class MCFileManager {
    public MCFileManager() {
        super();
    }

    public static String cacheFolder() {
        return null;
    }

    public static void copyDirectiory(String sourceDir, String targetDir) throws IOException {
        new File(targetDir).mkdirs();
        File v5 = new File(sourceDir);
        if(v5.exists()) {
            File[] v2 = v5.listFiles();
            int v3;
            for(v3 = 0; v3 < v2.length; ++v3) {
                if(v2[v3].isFile()) {
                    MCFileManager.copyFile(v2[v3], new File(String.valueOf(new File(targetDir).getAbsolutePath()) + File.separator + v2[v3].getName()));
                }

                if(v2[v3].isDirectory()) {
                    MCFileManager.copyDirectiory(String.valueOf(sourceDir) + "/" + v2[v3].getName(), String.valueOf(targetDir) + "/" + v2[v3].getName());
                }
            }
        }
    }

    public static void copyFile(File sourceFile, File targetFile) throws IOException {
       
    }

    public static String dataFolder() {
        return null;
    }

    public static boolean delAllFile(String path) {
        boolean flag;
        boolean v1 = false;
        File file = new File(path);
        if(!file.exists()) {
            flag = false;
        }
        else if(!file.isDirectory()) {
            flag = false;
        }
        else {
            String[] files = file.list();
            int i;
            for(i = 0; i < files.length; ++i) {
                File v4 = path.endsWith(File.separator) ? new File(String.valueOf(path) + files[i]) : new File(String.valueOf(path) + File.separator + files[i]);
                if(v4.isFile()) {
                    v4.delete();
                }

                if(v4.isDirectory()) {
                    MCFileManager.delAllFile(String.valueOf(path) + "/" + files[i]);
                    MCFileManager.delFolder(String.valueOf(path) + "/" + files[i]);
                    v1 = true;
                }
            }

            flag = v1;
        }

        return flag;
    }

    public static void delFolder(String folderPath) {
        try {
            MCFileManager.delAllFile(folderPath);
            new File(folderPath.toString()).delete();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteAllFilesOfDir(File path) {
        if(path.exists()) {
            if(path.isFile()) {
                path.delete();
            }
            else {
                File[] files = path.listFiles();
                int i;
                for(i = 0; i < files.length; ++i) {
                    MCFileManager.deleteAllFilesOfDir(files[i]);
                }

                path.delete();
            }
        }
    }

    public static String tempFoler() {
        return null;
    }
}

