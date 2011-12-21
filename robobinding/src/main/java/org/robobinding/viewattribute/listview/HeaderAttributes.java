/**
 * HeaderSourceViewAttribute.java
 * Sep 6, 2011
 * Copyright 2009~2011 ESCA Mobile Ltd company, Inc. All rights reserved.
 * ESCA Mobile Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.robobinding.viewattribute.listview;

import org.apache.commons.lang3.Validate;
import org.robobinding.binding.ViewAttribute;
import org.robobinding.binding.customwidget.Attribute;
import org.robobinding.binding.viewattribute.BindingDetailsBuilder;
import org.robobinding.binding.viewattribute.PropertyBindingDetails;
import org.robobinding.binding.viewattribute.ResourceBindingDetails;
import org.robobinding.presentationmodel.PresentationModelAdapter;

import android.content.Context;
import android.view.View;
import android.widget.ListView;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class HeaderAttributes implements ViewAttribute
{
	private ListView listView;
	
	private Attribute headerLayout;
	private Attribute headerSource;
	
	private HeaderAttributes(Builder builder)
	{
		validate(builder);
		
		this.listView = builder.listView;
		this.headerLayout = builder.headerLayout;
		this.headerSource = builder.headerSource;
	}

	private void validate(Builder builder)
	{
		Validate.notNull(builder.listView);
		Validate.notNull(builder.headerLayout);
		Validate.notNull(builder.headerSource);
	}

	@Override
	public void bind(PresentationModelAdapter presentationModelAdapter, Context context)
	{
		BindingDetailsBuilder bindingDetailsBuilder = new BindingDetailsBuilder(headerLayout.getValue(), false);
		ResourceBindingDetails resourceBindingDetails = bindingDetailsBuilder.createResourceBindingDetails();
		int headerLayoutId = resourceBindingDetails.getResourceId(context);
		
		PropertyBindingDetails propertyBindingDetails = PropertyBindingDetails.createFrom(headerSource.getValue(), false);
		Object headerSourcePresentationModel = presentationModelAdapter.getReadOnlyPropertyValueModel(propertyBindingDetails.propertyName).getValue();
		
		SubviewBinder binder = new SubviewBinder(context, headerLayoutId);
		View headerView = binder.bindTo(headerSourcePresentationModel);
		listView.addHeaderView(headerView, null, false);
	}
	
	public static class Builder
	{
		private ListView listView;
		
		private Attribute headerLayout;
		private Attribute headerSource;
		public void setListView(ListView listView)
		{
			this.listView = listView;
		}
		public void setHeaderLayout(Attribute headerLayout)
		{
			this.headerLayout = headerLayout;
		}
		public void setHeaderSource(Attribute headerSource)
		{
			this.headerSource = headerSource;
		}
		public HeaderAttributes create()
		{
			return new HeaderAttributes(this);
		}
	}
}
