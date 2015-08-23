package org.robobinding;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.LayoutInflater.Factory2;
import android.view.View;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class ViewFactory2 extends ViewFactory implements Factory2 {
	private final Factory2 original;

	public ViewFactory2(LayoutInflater layoutInflater, Factory2 original, 
			ViewNameResolver viewNameResolver, ViewCreationListener listener) {
		super(layoutInflater, original, viewNameResolver, listener);
		
		this.original = original;
	}
	@Override
	public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
		View view = original.onCreateView(parent, name, context, attrs);
		
		view = createViewByInflaterIfNull(view, name, attrs);

		notifyViewCreatedIfNotNull(attrs, view);

		return view;
	}

}
