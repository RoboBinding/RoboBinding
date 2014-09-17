package org.robobinding.widget.view;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
class ViewVisibility extends AbstractVisibility {
	private final View view;

	public ViewVisibility(View view) {
		this.view = view;
	}

	@Override
	public void makeVisible() {
		view.setVisibility(View.VISIBLE);
	}

	@Override
	public void makeGone() {
		view.setVisibility(View.GONE);
	}

	@Override
	protected void makeInvisible() {
		view.setVisibility(View.INVISIBLE);
	}

}
