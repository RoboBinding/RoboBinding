/**
 * HeaderAttributes.java
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
public class HeaderAttributes extends AbstractHeaderOrFooterAttributes
{
	static final String HEADER_LAYOUT = "headerLayout";
	static final String HEADER_SOURCE = "headerSource";
	static final String HEADER_VISIBILITY = "headerVisibility";
	
	@Override
	protected String layoutAttribute()
	{
		return HEADER_LAYOUT;
	}
	
	@Override
	protected String sourceAttribute()
	{
		return HEADER_SOURCE;
	}
	
	@Override
	protected String visibilityAttribute()
	{
		return HEADER_VISIBILITY;
	}
	
	@Override
	protected void addSubView(View subView)
	{
		view.addHeaderView(subView);
	}
}
