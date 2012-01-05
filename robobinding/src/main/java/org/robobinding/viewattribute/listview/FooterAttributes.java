/**
 * FooterSourceViewAttribute.java
 * Sep 6, 2011
 * Copyright 2009~2011 ESCA Mobile Ltd company, Inc. All rights reserved.
 * ESCA Mobile Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.robobinding.viewattribute.listview;

import org.robobinding.presentationmodel.PresentationModelAdapter;
import org.robobinding.property.ValueModel;
import org.robobinding.viewattribute.AbstractGroupedViewAttribute;
import org.robobinding.viewattribute.BindingDetailsBuilder;
import org.robobinding.viewattribute.PropertyBindingDetails;
import org.robobinding.viewattribute.ResourceBindingDetails;

import android.content.Context;
import android.view.View;
import android.widget.ListView;

/**
 * @since 1.0
 * @author Cheng Wei
 * 
 */
public class FooterAttributes extends AbstractGroupedViewAttribute<ListView>
{
	static final String FOOTER_LAYOUT = "footerLayout";
	static final String FOOTER_SOURCE = "footerSource";
	static final String FOOTER_VISIBILITY = "footerVisibility";
	
	private int footerLayoutId;
	private Object footerSourcePresentationModel;
	
	@Override
	protected void initializeChildViewAttributes()
	{
		validateAttributes();
	}
	
	private void validateAttributes()
	{
		assertAttributesArePresent(FOOTER_LAYOUT, FOOTER_SOURCE);
	}

	@Override
	public void bind(PresentationModelAdapter presentationModelAdapter, Context context)
	{
		initializeFooterLayoutId(context);
		initializeFooterSourcePresentationModel(presentationModelAdapter);
		
		SubviewBinder binder = new SubviewBinder(context, footerLayoutId);
		View footerView = binder.bindTo(footerSourcePresentationModel);
		view.addFooterView(footerView, null, false);
		
		addFooterVisibilityIfPresent(footerView, presentationModelAdapter, context);
	}

	private void initializeFooterLayoutId(Context context)
	{
		BindingDetailsBuilder bindingDetailsBuilder = new BindingDetailsBuilder(groupedAttributeDetails.attributeValueFor(FOOTER_LAYOUT));
		ResourceBindingDetails resourceBindingDetails = bindingDetailsBuilder.createResourceBindingDetails();
		footerLayoutId = resourceBindingDetails.getResourceId(context);
	}

	private void initializeFooterSourcePresentationModel(PresentationModelAdapter presentationModelAdapter)
	{
		String propertyName = groupedAttributeDetails.attributeValueFor(FOOTER_SOURCE);
		ValueModel<Object> valueModel = presentationModelAdapter.getReadOnlyPropertyValueModel(propertyName);
		footerSourcePresentationModel = valueModel.getValue();
	}

	private void addFooterVisibilityIfPresent(View footerView, PresentationModelAdapter presentationModelAdapter, Context context)
	{
		if(groupedAttributeDetails.hasAttribute(FOOTER_VISIBILITY))
		{
			FooterVisibilityAttribute footerVisibilityAttribute = new FooterVisibilityAttribute(view);
			footerVisibilityAttribute.setPreInitializeView(preInitializeViews);
			footerVisibilityAttribute.setView(footerView);
			footerVisibilityAttribute.setPropertyBindingDetails(PropertyBindingDetails.createFrom(groupedAttributeDetails.attributeValueFor(FOOTER_VISIBILITY)));
			footerVisibilityAttribute.bind(presentationModelAdapter, context);
		}
	}
}
