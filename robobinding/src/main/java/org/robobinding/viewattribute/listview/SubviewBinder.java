package org.robobinding.viewattribute.listview;

import org.robobinding.binding.AbstractBinder;
import org.robobinding.binding.BindingViewFactory.InflatedView;
import org.robobinding.presentationmodel.PresentationModelAdapter;
import org.robobinding.presentationmodel.PresentationModelAdapterImpl;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

class SubviewBinder extends AbstractBinder
{
	private final int layoutId;
	
	public SubviewBinder(Context context, int layoutId)
	{
		super(context, true);
		this.layoutId = layoutId;
	}

	public View bindTo(Object presentationModel)
	{
		PresentationModelAdapter presentationModelAdapter = new PresentationModelAdapterImpl(presentationModel);
		InflatedView inflatedView = inflateAndBind(layoutId, presentationModelAdapter);
		return inflatedView.getRootView();
	}
	
	public View bindToAndAttachToRoot(Object presentationModel, ViewGroup viewGroup)
	{
		PresentationModelAdapter presentationModelAdapter = new PresentationModelAdapterImpl(presentationModel);
		InflatedView inflatedView = inflateAndBind_attachToRoot(layoutId, presentationModelAdapter, viewGroup);
		return inflatedView.getRootView();
	}
}