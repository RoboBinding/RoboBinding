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
	private final boolean preInitializeViews;
	
	public ItemContext(View itemView, int position, boolean preInitializeViews) {
		this.itemView = itemView;
		this.position = position;
		this.preInitializeViews = preInitializeViews;
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

	/**
	 * @return This is exposed for framework use only for now.　It might be removed in future.
	 */
	@Deprecated
	public boolean isPreInitializeViews() {
		return preInitializeViews;
	}

}
