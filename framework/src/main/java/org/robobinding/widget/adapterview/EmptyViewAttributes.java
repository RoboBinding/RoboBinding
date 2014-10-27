package org.robobinding.widget.adapterview;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class EmptyViewAttributes implements SubViewAttributesStrategy<AdapterView<?>> {
	static final String EMPTY_VIEW_LAYOUT = "emptyViewLayout";
	static final String EMPTY_VIEW_PRESENTATION_MODEL = "emptyViewPresentationModel";
	static final String EMPTY_VIEW_VISIBILITY = "emptyViewVisibility";
	private EmptyViewVisibility emptyViewVisibility;

	@Override
	public String layoutAttribute() {
		return EMPTY_VIEW_LAYOUT;
	}

	@Override
	public String subViewPresentationModelAttribute() {
		return EMPTY_VIEW_PRESENTATION_MODEL;
	}

	@Override
	public String visibilityAttribute() {
		return EMPTY_VIEW_VISIBILITY;
	}

	@Override
	public void addSubView(AdapterView<?> adapterView, View emptyView, Context context) {
		emptyViewVisibility = new EmptyViewVisibility(adapterView, emptyView);
		emptyViewVisibility.makeVisible();
	}

	@Override
	public EmptyViewVisibility createVisibility(AdapterView<?> adapterView, View emptyView) {
		if (emptyViewVisibility == null)
			throw new IllegalStateException("Expected addSubView() to be called first");

		return emptyViewVisibility;
	}
}
