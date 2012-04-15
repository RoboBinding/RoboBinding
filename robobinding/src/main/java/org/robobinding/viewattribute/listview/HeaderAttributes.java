/**
 * HeaderAttributes.java
 * Sep 6, 2011
 * Copyright 2009~2011 ESCA Mobile Ltd company, Inc. All rights reserved.
 * ESCA Mobile Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.robobinding.viewattribute.listview;

import org.robobinding.viewattribute.adapterview.AbstractSubViewAttributes;

import android.content.Context;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class HeaderAttributes extends AbstractSubViewAttributes<ListView>
{
	static final String HEADER_LAYOUT = "headerLayout";
	static final String HEADER_PRESENTATION_MODEL = "headerPresentationModel";
	static final String HEADER_VISIBILITY = "headerVisibility";
	
	@Override
	protected String layoutAttribute()
	{
		return HEADER_LAYOUT;
	}
	
	@Override
	protected String subViewPresentationModelAttribute()
	{
		return HEADER_PRESENTATION_MODEL;
	}
	
	@Override
	protected String visibilityAttribute()
	{
		return HEADER_VISIBILITY;
	}
	
	@Override
	protected void addSubView(View subView, Context context)
	{
		LinearLayout outerContainer = new LinearLayout(context);
		AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(AbsListView.LayoutParams.FILL_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
		outerContainer.setLayoutParams(layoutParams);
		outerContainer.setOrientation(LinearLayout.VERTICAL);
		outerContainer.addView(subView);
		view.addHeaderView(outerContainer);
	}
	
	@Override
	protected HeaderVisibility createVisibility(View subView)
	{
		return new HeaderVisibility(subView);
	}
}
