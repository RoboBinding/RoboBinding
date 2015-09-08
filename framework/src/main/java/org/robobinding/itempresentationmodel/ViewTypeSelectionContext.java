package org.robobinding.itempresentationmodel;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class ViewTypeSelectionContext<T> {
	private int viewTypeCount;
	private T item;
	private int position;
	
	public ViewTypeSelectionContext(int viewTypeCount, T item, int position) {
		this.viewTypeCount = viewTypeCount;
		this.item = item;
		this.position = position;
	}

	public int getViewTypeCount() {
		return viewTypeCount;
	}

	public T getItem() {
		return item;
	}

	public int getPosition() {
		return position;
	}

}
