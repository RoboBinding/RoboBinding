package org.robobinding.widget.listview;

import org.robobinding.widget.adapterview.SubViewAttributesStrategy;

import android.content.Context;
import android.view.View;
import android.widget.ListView;

/**
 * @since 1.0
 * @author Cheng Wei
 * @author Jihun Lee
 */
public class FooterAttributes implements SubViewAttributesStrategy<ListView> {
	static final String FOOTER_LAYOUT = "footerLayout";
	static final String FOOTER_PRESENTATION_MODEL = "footerPresentationModel";
	static final String FOOTER_VISIBILITY = "footerVisibility";
	private FooterVisibility footerVisibility;

	@Override
	public String layoutAttribute() {
		return FOOTER_LAYOUT;
	}

	@Override
	public String subViewPresentationModelAttribute() {
		return FOOTER_PRESENTATION_MODEL;
	}

	@Override
	public String visibilityAttribute() {
		return FOOTER_VISIBILITY;
	}

	@Override
	public void addSubView(ListView listView, View subView, Context context) {
		listView.addFooterView(subView);
		if(footerVisibility == null) {
			footerVisibility = new FooterVisibility(listView, subView);
		}
	}

	@Override
	public FooterVisibility createVisibility(ListView listView, View subView) {
		if(footerVisibility == null) {
			footerVisibility = new FooterVisibility(listView, subView);
		}

		return footerVisibility;
	}
}
