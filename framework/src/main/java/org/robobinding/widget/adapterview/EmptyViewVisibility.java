package org.robobinding.widget.adapterview;

import org.robobinding.widget.view.AbstractVisibility;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
class EmptyViewVisibility extends AbstractVisibility {
	private final AdapterView<?> adapterView;
	private final View emptyView;

	EmptyViewVisibility(AdapterView<?> adapterView, View emptyView) {
		this.adapterView = adapterView;
		this.emptyView = emptyView;
	}

	@Override
	public void makeVisible() {
		if (adapterView.getEmptyView() == emptyView)
			return;

		ViewGroup viewGroupParent = (ViewGroup) adapterView.getParent();
		viewGroupParent.addView(emptyView);
		adapterView.setEmptyView(emptyView);
	}

	@Override
	public void makeGone() {
		if (adapterView.getEmptyView() == null)
			return;

		ViewGroup viewGroupParent = (ViewGroup) adapterView.getParent();
		viewGroupParent.removeView(emptyView);
		adapterView.setEmptyView(null);
	}

	@Override
	protected void makeInvisible() {
		makeGone();
	}
}
