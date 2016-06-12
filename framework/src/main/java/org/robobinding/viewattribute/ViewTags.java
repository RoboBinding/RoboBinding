package org.robobinding.viewattribute;

import android.os.Build;
import android.view.View;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class ViewTags<TagType> {
	public static final int USED_KEY1 = 1;
	public static final int USED_KEY2 = 2;
	private static final int ROBOBINDING_TAG = 0x100000ee;
	
	private final int key;

	public ViewTags(int key) {
		this.key = key;
	}

	public ViewTag<TagType> tagFor(View view) {
		Object o = getTag(view);
		if(o == null) {
			ViewTagger viewTagger = new ViewTagger();
			setTag(view, viewTagger);
			return new ViewTag<TagType>(viewTagger, key);
		} else if(isViewTagger(o)) {
			return new ViewTag<TagType>((ViewTagger)o, key);
		} else {
			 throw new RuntimeException("RoboBinding view tagging strategy cannot be applied, as view already has a tag");
		}
	}
	
	static Object getTag(View view) {
		if (shouldUseKeyedTag()) {
			return view.getTag(ROBOBINDING_TAG);
		} else {
			return view.getTag();
		}
	}
	
	private static boolean shouldUseKeyedTag() {
		// ICS (v 14) fixes a leak when using setTag(int, Object)
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
	}
	
	static void setTag(View view, Object viewTagger) {
		if (shouldUseKeyedTag()) {
			view.setTag(ROBOBINDING_TAG, viewTagger);
		} else {
			view.setTag(viewTagger);
		}
	}
	
	private boolean isViewTagger(Object o) {
		return o instanceof ViewTagger;
	}
}
