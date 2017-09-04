package com.whatyplugin.imooc.logic.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.graphics.Bitmap;

public class UploadUtils {
	public static final int SO_TIMEOUT = 60;

	public UploadUtils() {
		super();
	}

	public static final boolean saveBitmapToFile(Bitmap bitmap, String filePath) {
		FileOutputStream outputStream = null;
		File file = new File(filePath);
		boolean result = false;

		try {
			outputStream = new FileOutputStream(file);
			result = bitmap.compress(Bitmap.CompressFormat.JPEG, 100,
					((OutputStream) outputStream));
		} catch (Exception e) {

			try {
				outputStream.flush();
				outputStream.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			return result;
		}

		return result;
	}
}
