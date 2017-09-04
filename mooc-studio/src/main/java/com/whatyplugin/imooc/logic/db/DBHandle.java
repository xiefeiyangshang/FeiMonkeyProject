package com.whatyplugin.imooc.logic.db;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;

public abstract class DBHandle {
	public Context mContext;

	protected DBHandle(Context context) {
		super();
		this.mContext = context;
	}

	public int delete(Uri uri, String whereClause) {
		int result = 0;
		try {
			result = this.mContext.getContentResolver().delete(uri,
					whereClause, null);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public Uri insert(Uri uri, ContentValues cv) {
		Uri result = null;
		if (cv == null) {
			return result;
		}

		try {
			result = this.mContext.getContentResolver().insert(uri, cv);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean multiInsert(Uri uri, ContentValues[] cv) {
		boolean result = false;
		if (cv == null) {
			return result;
		}

		try {
			this.mContext.getContentResolver().bulkInsert(uri, cv);
		} catch (Exception v1) {
			v1.printStackTrace();
		}
		result = true;
		return result;
	}

	public boolean multiUpdate(Uri uri, String key, ContentValues[] cv) {
		if (cv != null) {
			for (ContentValues contentValues : cv) {
				if (this.mContext.getContentResolver().update(uri,
						contentValues,
						String.valueOf(key) + "=" + contentValues.get(key),
						null) < 1) {
					continue;
				} else {
					return false;
				}

			}
		}
		return true;
	}

	public int update(Uri uri, String selection, ContentValues cv) {
		int result = 0;
		if (cv != null) {
			try {
				if (!TextUtils.isEmpty(((CharSequence) selection))) {
					result = this.mContext.getContentResolver().update(uri, cv,
							selection, null);
				} else {
					return 0;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}
