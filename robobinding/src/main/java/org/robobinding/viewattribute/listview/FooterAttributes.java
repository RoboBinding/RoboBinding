/**
 * FooterSourceViewAttribute.java
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
public class FooterAttributes implements ViewAttribute
{
	private ListView listView;
	
	private Attribute footerLayout;
	private Attribute footerVisibility;
	private Attribute footerSource;
	
	private FooterAttributes(Builder builder)
	{
		validate(builder);
		
		this.listView = builder.listView;
		this.footerLayout = builder.footerLayout;
		this.footerSource = builder.footerSource;
		this.footerVisibility = builder.footerVisibility;
	}

	private void validate(Builder builder)
	{
		Validate.notNull(builder.listView);
		Validate.notNull(builder.footerLayout);
		Validate.notNull(builder.footerSource);
	}

	@Override
	public void bind(PresentationModelAdapter presentationModelAdapter, Context context)
	{
		BindingDetailsBuilder bindingDetailsBuilder = new BindingDetailsBuilder(footerLayout.getValue(), false);
		ResourceBindingDetails resourceBindingDetails = bindingDetailsBuilder.createResourceBindingDetails();
		int footerLayoutId = resourceBindingDetails.getResourceId(context);
		
		PropertyBindingDetails propertyBindingDetails = PropertyBindingDetails.createFrom(footerSource.getValue(), false);
		Object footerSourcePresentationModel = presentationModelAdapter.getReadOnlyPropertyValueModel(propertyBindingDetails.propertyName).getValue();
		
		SubviewBinder binder = new SubviewBinder(context, footerLayoutId);
		View footerView = binder.bindTo(footerSourcePresentationModel);
		listView.addFooterView(footerView, null, false);
		
		if(footerVisibility != null)
		{
			FooterVisibilityAttribute footerVisibilityAttribute = new FooterVisibilityAttribute(listView, footerView, footerVisibility.getValue());
			footerVisibilityAttribute.bind(presentationModelAdapter, context);
		}
	}
	
	public static class Builder
	{
		private ListView listView;
		
		private Attribute footerLayout;
		private Attribute footerVisibility;
		private Attribute footerSource;
		public void setListView(ListView listView)
		{
			this.listView = listView;
		}
		public void setFooterLayout(Attribute footerLayout)
		{
			this.footerLayout = footerLayout;
		}
		public void setFooterVisibility(Attribute footerVisibility)
		{
			this.footerVisibility = footerVisibility;
		}
		public void setFooterSource(Attribute footerSource)
		{
			this.footerSource = footerSource;
		}
		public FooterAttributes create()
		{
			return new FooterAttributes(this);
		}
	}
}
