package org.robobinding.widget.listview;

import org.robobinding.widget.adapterview.SubViewAttributesStrategy;

import android.content.Context;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class HeaderAttributes implements SubViewAttributesStrategy<ListView> {
	static final String HEADER_LAYOUT = "headerLayout";
	static final String HEADER_PRESENTATION_MODEL = "headerPresentationModel";
	static final String HEADER_VISIBILITY = "headerVisibility";

	@Override
	public String layoutAttribute() {
		return HEADER_LAYOUT;
	}

	@Override
	public String subViewPresentationModelAttribute() {
		return HEADER_PRESENTATION_MODEL;
	}

	@Override
	public String visibilityAttribute() {
		return HEADER_VISIBILITY;
	}

	@Override
	public void addSubView(ListView listView, View subView, Context context) {
		LinearLayout outerContainer = new LinearLayout(context);
		AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
		outerContainer.setLayoutParams(layoutParams);
		outerContainer.setOrientation(LinearLayout.VERTICAL);
		outerContainer.addView(subView);
		listView.addHeaderView(outerContainer);
	}

	@Override
	public HeaderVisibility createVisibility(ListView listView, View subView) {
		return new HeaderVisibility(subView);
	}
}
