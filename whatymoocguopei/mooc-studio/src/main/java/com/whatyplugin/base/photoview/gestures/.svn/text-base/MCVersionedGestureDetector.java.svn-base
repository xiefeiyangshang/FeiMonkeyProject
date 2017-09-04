package com.whatyplugin.base.photoview.gestures;

import android.content.Context;

public final class MCVersionedGestureDetector {
	public MCVersionedGestureDetector() {
		super();
	}

	public static MCGestureDetector newInstance(Context context, MCOnGestureListener listener) {
		MCEclairGestureDetector mcEclairGestureDetector = new MCFroyoGestureDetector(context);

		mcEclairGestureDetector.setOnGestureListener(listener);
		return mcEclairGestureDetector;
	}
}
