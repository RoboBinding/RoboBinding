package org.robobinding;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.LayoutInflater.Factory2;
import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
class ViewFactory implements Factory2 {
	private final Factory2 original;
	private LayoutInflater layoutInflater;
	private final ViewNameResolver viewNameResolver;
	private final ViewCreationListener listener;

	public ViewFactory(LayoutInflater layoutInflater, Factory2 original, 
			ViewNameResolver viewNameResolver, ViewCreationListener listener) {
		this.layoutInflater = layoutInflater;
		this.original = original;
		this.viewNameResolver = viewNameResolver;
		this.listener = listener;
	}

	@Override
	public View onCreateView(String name, Context context, AttributeSet attrs) {
		View view = original.onCreateView(name, context, attrs);
		
		view = createViewByInflaterIfNull(view, name, attrs);

		notifyViewCreatedIfNotNull(attrs, view);

		return view;
	}
	
	private View createViewByInflaterIfNull(View viewOrNull, String name, AttributeSet attrs) {
		if(viewOrNull != null) return viewOrNull;
		
		String viewFullName = viewNameResolver.getViewNameFromLayoutTag(name);
		try {
			return layoutInflater.createView(viewFullName, null, attrs);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
		View view = original.onCreateView(parent, name, context, attrs);
		
		view = createViewByInflaterIfNull(view, name, attrs);

		notifyViewCreatedIfNotNull(attrs, view);

		return view;
	}

	private void notifyViewCreatedIfNotNull(AttributeSet attrs, View view) {
		if(view != null) {
			listener.onViewCreated(view, attrs);
		}
	}
}
