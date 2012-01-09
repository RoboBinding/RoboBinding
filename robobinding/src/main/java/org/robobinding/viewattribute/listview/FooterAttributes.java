/**
 * FooterSourceViewAttribute.java
 * Sep 6, 2011
 * Copyright 2009~2011 ESCA Mobile Ltd company, Inc. All rights reserved.
 * ESCA Mobile Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.robobinding.viewattribute.listview;

import org.robobinding.binder.Binder;
import org.robobinding.presentationmodel.PresentationModelAdapter;
import org.robobinding.property.ValueModel;
import org.robobinding.viewattribute.AbstractGroupedViewAttribute;
import org.robobinding.viewattribute.BindingDetailsBuilder;
import org.robobinding.viewattribute.PropertyBindingDetails;
import org.robobinding.viewattribute.ResourceBindingDetails;

import android.content.Context;
import android.view.LayoutInflater;
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
	
	private View footerView;
	
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
		initializeFooterView(presentationModelAdapter, context);
		
		addFooterVisibilityIfPresent(presentationModelAdapter, context);
	}

	private void initializeFooterView(PresentationModelAdapter presentationModelAdapter, Context context)
	{
		int footerLayoutId = getFooterLayoutId(context);
		if(groupedAttributeDetails.hasAttribute(FOOTER_SOURCE))
		{
			Object footerSourcePresentationModel = getFooterSourcePresentationModel(presentationModelAdapter);
			footerView = Binder.bindView(context, footerLayoutId, footerSourcePresentationModel);
		}else
		{
			LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			footerView = inflater.inflate(footerLayoutId, null);
		}
		view.addFooterView(footerView, null, false);
	}

	private int getFooterLayoutId(Context context)
	{
		BindingDetailsBuilder bindingDetailsBuilder = new BindingDetailsBuilder(groupedAttributeDetails.attributeValueFor(FOOTER_LAYOUT));
		ResourceBindingDetails resourceBindingDetails = bindingDetailsBuilder.createResourceBindingDetails();
		return resourceBindingDetails.getResourceId(context);
	}

	private Object getFooterSourcePresentationModel(PresentationModelAdapter presentationModelAdapter)
	{
		PropertyBindingDetails propertyBindingDetails = PropertyBindingDetails.createFrom(groupedAttributeDetails.attributeValueFor(FOOTER_SOURCE));
		ValueModel<Object> valueModel = presentationModelAdapter.getReadOnlyPropertyValueModel(propertyBindingDetails.propertyName);
		return valueModel.getValue();
	}

	private void addFooterVisibilityIfPresent(PresentationModelAdapter presentationModelAdapter, Context context)
	{
		if(groupedAttributeDetails.hasAttribute(FOOTER_VISIBILITY))
		{
			HeaderOrFooterVisibilityAttribute footerVisibilityAttribute = new HeaderOrFooterVisibilityAttribute(footerView);
			footerVisibilityAttribute.setPreInitializeView(preInitializeViews);
			footerVisibilityAttribute.setPropertyBindingDetails(PropertyBindingDetails.createFrom(groupedAttributeDetails.attributeValueFor(FOOTER_VISIBILITY)));
			footerVisibilityAttribute.bind(presentationModelAdapter, context);
		}
	}
}
