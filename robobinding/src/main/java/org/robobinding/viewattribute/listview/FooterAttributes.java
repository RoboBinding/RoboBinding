/**
 * FooterSourceViewAttribute.java
 * Sep 6, 2011
 * Copyright 2009~2011 ESCA Mobile Ltd company, Inc. All rights reserved.
 * ESCA Mobile Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.robobinding.viewattribute.listview;

import org.robobinding.viewattribute.adapterview.AbstractSubViewAttributes;
import org.robobinding.viewattribute.adapterview.SubViewVisibilityAttribute;

import android.content.Context;
import android.view.View;
import android.widget.ListView;

/**
 * @since 1.0
 * @author Cheng Wei
 * 
 */
public class FooterAttributes extends AbstractSubViewAttributes<ListView>
{
	static final String FOOTER_LAYOUT = "footerLayout";
	static final String FOOTER_PRESENTATION_MODEL = "footerPresentationModel";
	static final String FOOTER_VISIBILITY = "footerVisibility";
	
	@Override
	protected String layoutAttribute()
	{
		return FOOTER_LAYOUT;
	}
	
	@Override
	protected String subViewPresentationModelAttribute()
	{
		return FOOTER_PRESENTATION_MODEL;
	}
	
	@Override
	protected String visibilityAttribute()
	{
		return FOOTER_VISIBILITY;
	}
	
	@Override
	protected void addSubView(View subView, Context context)
	{
		view.addFooterView(subView);
	}
	
	@Override
	protected SubViewVisibilityAttribute createVisibilityAttribute(View subView)
	{
		FooterVisibility visibility = new FooterVisibility(view, subView);
		return new SubViewVisibilityAttribute(visibility);
	}
}
