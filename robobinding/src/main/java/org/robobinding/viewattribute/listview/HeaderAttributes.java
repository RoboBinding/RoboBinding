/**
 * HeaderAttributes.java
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
public class HeaderAttributes extends AbstractGroupedViewAttribute<ListView>
{
	static final String HEADER_LAYOUT = "headerLayout";
	static final String HEADER_SOURCE = "headerSource";
	static final String HEADER_VISIBILITY = "headerVisibility";
	
	private View headerView;
	@Override
	protected void initializeChildViewAttributes()
	{
		validateAttributes();
	}

	private void validateAttributes()
	{
		assertAttributesArePresent(HEADER_LAYOUT);
	}

	@Override
	public void bind(PresentationModelAdapter presentationModelAdapter, Context context)
	{
		initializeHeaderView(presentationModelAdapter, context);
		
		addHeaderVisibilityIfPresent(presentationModelAdapter, context);
	}

	private void initializeHeaderView(PresentationModelAdapter presentationModelAdapter, Context context)
	{
		int headerLayoutId = getHeaderLayoutId(context);
		if(groupedAttributeDetails.hasAttribute(HEADER_SOURCE))
		{
			Object headerSourcePresentationModel = getHeaderSourcePresentationModel(presentationModelAdapter);
			headerView = Binder.bindView(context, headerLayoutId, headerSourcePresentationModel);
		}else
		{
			LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			headerView = inflater.inflate(headerLayoutId, null);
		}
		view.addHeaderView(headerView, null, false);
	}

	private int getHeaderLayoutId(Context context)
	{
		BindingDetailsBuilder bindingDetailsBuilder = new BindingDetailsBuilder(groupedAttributeDetails.attributeValueFor(HEADER_LAYOUT));
		ResourceBindingDetails resourceBindingDetails = bindingDetailsBuilder.createResourceBindingDetails();
		return resourceBindingDetails.getResourceId(context);
	}

	private Object getHeaderSourcePresentationModel(PresentationModelAdapter presentationModelAdapter)
	{
		PropertyBindingDetails propertyBindingDetails = PropertyBindingDetails.createFrom(groupedAttributeDetails.attributeValueFor(HEADER_SOURCE));
		ValueModel<Object> valueModel = presentationModelAdapter.getReadOnlyPropertyValueModel(propertyBindingDetails.propertyName);
		return valueModel.getValue();
	}
	
	private void addHeaderVisibilityIfPresent(PresentationModelAdapter presentationModelAdapter, Context context)
	{
		if(groupedAttributeDetails.hasAttribute(HEADER_VISIBILITY))
		{
			HeaderOrFooterVisibilityAttribute headerVisibilityAttribute = new HeaderOrFooterVisibilityAttribute(headerView);
			headerVisibilityAttribute.setPreInitializeView(preInitializeViews);
			headerVisibilityAttribute.setPropertyBindingDetails(PropertyBindingDetails.createFrom(groupedAttributeDetails.attributeValueFor(HEADER_VISIBILITY)));
			headerVisibilityAttribute.bind(presentationModelAdapter, context);
		}
	}
}
