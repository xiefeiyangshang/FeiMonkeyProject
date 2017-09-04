package com.whatyplugin.imooc.logic.service_;

import android.content.Context;

public interface MCPersonServiceInterface {

	public void getPersonDetail(String uid, int provid, int cityid, int areaid, final MCAnalyzeBackBlock resultBack, Context context);

	public void saveHandImage(String file, Context context, final MCAnalyzeBackBlock resultBack);

}
