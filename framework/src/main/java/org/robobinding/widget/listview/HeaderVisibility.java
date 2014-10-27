package org.robobinding.widget.listview;

import org.robobinding.widget.view.AbstractVisibility;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class HeaderVisibility extends AbstractVisibility {
	private View headerView;

	public HeaderVisibility(View headerView) {
		this.headerView = headerView;
	}

	@Override
	public void makeGone() {
		headerView.setVisibility(View.GONE);
	}

	@Override
	public void makeVisible() {
		headerView.setVisibility(View.VISIBLE);
	}

	@Override
	protected void makeInvisible() {
		headerView.setVisibility(View.INVISIBLE);
	}
}
