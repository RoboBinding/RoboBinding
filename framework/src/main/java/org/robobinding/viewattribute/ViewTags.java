package org.robobinding.viewattribute;

import android.view.View;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class ViewTags<TagType> {
	private final int key;

	public ViewTags(int key) {
		this.key = key;
	}

	public ViewTag<TagType> tagFor(View view) {
		Object o = view.getTag();
		if(o == null) {
			ViewTagger viewTagger = new ViewTagger();
			view.setTag(viewTagger);
			return new ViewTag<TagType>(viewTagger, key);
		} else if(isViewTagger(o)) {
			return new ViewTag<TagType>((ViewTagger)o, key);
		} else {
			 throw new RuntimeException("RoboBinding view tagging strategy cannot be applied, as view already has a tag");
		}
	}
	
	private boolean isViewTagger(Object o) {
		return o instanceof ViewTagger;
	}
}
