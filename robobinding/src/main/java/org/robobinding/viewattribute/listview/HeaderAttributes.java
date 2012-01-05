/**
 * HeaderAttributes.java
 * Sep 6, 2011
 * Copyright 2009~2011 ESCA Mobile Ltd company, Inc. All rights reserved.
 * ESCA Mobile Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.robobinding.viewattribute.listview;

import org.robobinding.presentationmodel.PresentationModelAdapter;
import org.robobinding.property.ValueModel;
import org.robobinding.viewattribute.AbstractGroupedViewAttribute;
import org.robobinding.viewattribute.BindingDetailsBuilder;
import org.robobinding.viewattribute.ResourceBindingDetails;

import android.content.Context;
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
	
	private int headerLayoutId;
	private Object headerSourcePresentationModel;
	@Override
	protected void initializeChildViewAttributes()
	{
		validateAttributes();
	}

	private void validateAttributes()
	{
		assertAttributesArePresent(HEADER_LAYOUT, HEADER_SOURCE);
	}

	@Override
	public void bind(PresentationModelAdapter presentationModelAdapter, Context context)
	{
		initializeHeaderLayoutId(context);
		initializeHeaderSourcePresentationModel(presentationModelAdapter);
		
		SubviewBinder binder = new SubviewBinder(context, headerLayoutId);
		View headerView = binder.bindTo(headerSourcePresentationModel);
		view.addHeaderView(headerView, null, false);
	}

	private void initializeHeaderLayoutId(Context context)
	{
		BindingDetailsBuilder bindingDetailsBuilder = new BindingDetailsBuilder(groupedAttributeDetails.attributeValueFor(HEADER_LAYOUT));
		ResourceBindingDetails resourceBindingDetails = bindingDetailsBuilder.createResourceBindingDetails();
		headerLayoutId = resourceBindingDetails.getResourceId(context);
	}

	private void initializeHeaderSourcePresentationModel(PresentationModelAdapter presentationModelAdapter)
	{
		String propertyName = groupedAttributeDetails.attributeValueFor(HEADER_SOURCE);
		ValueModel<Object> valueModel = presentationModelAdapter.getReadOnlyPropertyValueModel(propertyName);
		headerSourcePresentationModel = valueModel.getValue();
	}
}
