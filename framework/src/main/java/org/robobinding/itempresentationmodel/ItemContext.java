package org.robobinding.itempresentationmodel;

import android.view.View;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class ItemContext {
	private final View itemView;
	private final int position;
	
	public ItemContext(View itemView, int position) {
		this.itemView = itemView;
		this.position = position;
	}

	/**
	 * @return The item view. The use of the view should be limited to minimal as it may break the pure POJO PresentationModel rule.
	 */
	public View getItemView() {
		return itemView;
	}

	public int getPosition() {
		return position;
	}
}
