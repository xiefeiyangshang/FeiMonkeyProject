package com.whatyplugin.base.resource;


import android.content.Context;
import android.graphics.drawable.Drawable;

public class MCResource {
    private Context mContext;
    private static MCResource mMCResource;
    private String packageName;
    private int skin;

    private MCResource(Context context) {
        this(context, null);
    }

    private MCResource(Context context, String packageName) {
        super();
        this.mContext = context;
        this.packageName = packageName;
    }

    private Context createContextByPackageName(String packageName) {
        Context v1 = this.mContext;
        if(packageName != null) {
            int v3 = 2;
            try {
                v1 = v1.createPackageContext(packageName, v3);
                Context v2 = v1;
                return v2;
            }
            catch(Exception exception) {
                exception.printStackTrace();
            }
        }

        return v1;
    }

    public static MCResource getMCResourceInstance(Context context, String packageNmae) {
        if(MCResource.mMCResource == null) {
            if(packageNmae != null && !packageNmae.isEmpty()) {
                MCResource.mMCResource = new MCResource(context, packageNmae);
                return MCResource.mMCResource;
            }

            MCResource.mMCResource = new MCResource(context);
        }

        return MCResource.mMCResource;
    }

    public int getSkin() {
        return this.skin;
    }

    public Drawable localizedDrawableForKey(int drawableId, int defaultsId) {
        return this.createContextByPackageName(this.packageName).getResources().getDrawable(drawableId);
    }

    public String localizedStringForKey(int stingId, int defaultsId) {
        return this.createContextByPackageName(this.packageName).getResources().getString(stingId);
    }

    public void setSkin(int skin) {
        this.skin = skin;
    }
}

