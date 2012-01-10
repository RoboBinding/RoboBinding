/**
 * FooterSourceViewAttribute.java
 * Sep 6, 2011
 * Copyright 2009~2011 ESCA Mobile Ltd company, Inc. All rights reserved.
 * ESCA Mobile Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.robobinding.viewattribute.listview;

import android.view.View;

/**
 * @since 1.0
 * @author Cheng Wei
 * 
 */
public class FooterAttributes extends AbstractHeaderOrFooterAttributes
{
	static final String FOOTER_LAYOUT = "footerLayout";
	static final String FOOTER_SOURCE = "footerSource";
	static final String FOOTER_VISIBILITY = "footerVisibility";
	
	@Override
	protected String layoutAttribute()
	{
		return FOOTER_LAYOUT;
	}
	
	@Override
	protected String sourceAttribute()
	{
		return FOOTER_SOURCE;
	}
	
	@Override
	protected String visibilityAttribute()
	{
		return FOOTER_VISIBILITY;
	}
	
	@Override
	protected void addSubView(View subView)
	{
		view.addFooterView(subView);
	}
}
