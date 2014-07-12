package it.open.androidlab.ui.base;


import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.graphics.Typeface;

public class Typefaces {

	private static final Map<String, Typeface> cache = new HashMap<String, Typeface>();

	public static Typeface get(Context c, String assetPath) {
		synchronized (cache) {
			if (!cache.containsKey(assetPath)) {
				try {
					Typeface t = Typeface.createFromAsset(c.getAssets(),
							assetPath);
					cache.put(assetPath, t);
				} catch (Exception e) {
					return null;
				}
			}
			return cache.get(assetPath);
		}
	}

}
