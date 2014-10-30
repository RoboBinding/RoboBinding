package org.robobinding.viewattribute;




/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class ViewTag<TagType> {
	private final ViewTagger viewTagger;
	private final int key;

	public ViewTag(ViewTagger viewTagger, int key) {
		this.viewTagger = viewTagger;
		this.key = key;
	}

	public boolean has() {
		return get() != null;
	}

	@SuppressWarnings("unchecked")
	public TagType get() {
		return (TagType)viewTagger.get(key);
	}

	public void set(TagType value) {
		viewTagger.set(key, value);
	}

}
