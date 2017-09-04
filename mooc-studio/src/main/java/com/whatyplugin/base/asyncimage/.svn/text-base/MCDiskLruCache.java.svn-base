package com.whatyplugin.base.asyncimage;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;

import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.jakewharton.disklrucache.DiskLruCache;
import com.jakewharton.disklrucache.DiskLruCache.Editor;
import com.jakewharton.disklrucache.DiskLruCache.Snapshot;

public class MCDiskLruCache implements ImageCache {
	private static int IO_BUFFER_SIZE = 0;
	private CompressFormat mCompressFormat;
	private int mCompressQuality;
	private DiskLruCache mDiskCache;

	static {
		MCDiskLruCache.IO_BUFFER_SIZE = 8192;
	}

	public MCDiskLruCache(Context context, String uniqueName, int diskCacheSize, CompressFormat compressFormat, int quality) {
		super();
		this.mCompressFormat = CompressFormat.JPEG;
		this.mCompressQuality = 70;
		try {
			this.mDiskCache = DiskLruCache.open(this.getDiskCacheDir(context, uniqueName), 1, 1, ((long) diskCacheSize));
			this.mCompressFormat = compressFormat;
			this.mCompressQuality = quality;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clearCache() {
		try {
			this.mDiskCache.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean containsKey(String key) {
		Snapshot snapshot = null;
		try {
			snapshot = this.mDiskCache.get(createKey(key));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (snapshot != null) {
				snapshot.close();
			}
		}
		return snapshot!=null;
	}

	public Bitmap getBitmap(String key) {
		Snapshot snapshot;
		Bitmap bitmap = null;
		InputStream in = null;
		try {
			snapshot = this.mDiskCache.get(createKey(key));
			if (snapshot != null) {
				in = snapshot.getInputStream(0);
				if (snapshot != null) {
					bitmap = BitmapFactory.decodeStream(new BufferedInputStream(in, MCDiskLruCache.IO_BUFFER_SIZE));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return bitmap;
	}

	public File getCacheFolder() {
		return this.mDiskCache.getDirectory();
	}

	private File getDiskCacheDir(Context context, String uniqueName) {
		File file = null;
		try {
			file = new File(String.valueOf(context.getCacheDir().getPath()) + File.separator + uniqueName);
			if(!file.exists()){
				file.mkdirs();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file;
	}

	public void putBitmap(String key, Bitmap data) {
		Editor editor = null;
		try {
			editor = this.mDiskCache.edit(createKey(key));
			if (editor == null) {
				return;
			}

			if (this.writeBitmapToFile(data, editor)) {
				this.mDiskCache.flush();
				editor.commit();
				return;
			}

			editor.abort();
		} catch (Exception exception) {
			if (editor == null) {
				return;
			}

			try {
				editor.abort();
			} catch (Exception v2) {
			}
		}
	}

	private boolean writeBitmapToFile(Bitmap bitmap, Editor editor) throws IOException, FileNotFoundException {
		OutputStream outStream = null;
		BufferedOutputStream bOutStream = null;
		try {
			outStream = new BufferedOutputStream(editor.newOutputStream(0), MCDiskLruCache.IO_BUFFER_SIZE);
			bitmap.compress(this.mCompressFormat, this.mCompressQuality, outStream);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (outStream != null) {
				outStream.close();
			}
			if (bOutStream != null) {
				bOutStream.close();
			}
		}
		return true;
	}

	private String createKey(String url) {
		return String.valueOf(url.hashCode());
	}
}
