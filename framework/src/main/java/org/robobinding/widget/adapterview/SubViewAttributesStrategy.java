package org.robobinding.widget.adapterview;

import org.robobinding.widget.view.AbstractVisibility;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public interface SubViewAttributesStrategy<T extends AdapterView<?>> {
	String layoutAttribute();

	String subViewPresentationModelAttribute();

	String visibilityAttribute();

	void addSubView(T adapterView, View subView, Context context);

	AbstractVisibility createVisibility(T adapterView, View subView);
}
