package org.robobinding;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.LayoutInflater.Factory;
import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public class ViewFactory implements Factory {
    private final LayoutInflater layoutInflater;
    private final ViewNameResolver viewNameResolver;
    private final ViewCreationListener listener;

    public ViewFactory(LayoutInflater layoutInflater, ViewNameResolver viewNameResolver, 
	    ViewCreationListener listener) {
	this.layoutInflater = layoutInflater;
	this.viewNameResolver = viewNameResolver;
	this.listener = listener;

	layoutInflater.setFactory(this);
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
	try {
	    String viewFullName = viewNameResolver.getViewNameFromLayoutTag(name);

	    View view = layoutInflater.createView(viewFullName, null, attrs);

	    notifyViewCreated(attrs, view);

	    return view;
	} catch (ClassNotFoundException e) {
	    throw new RuntimeException(e);
	}
    }

    private void notifyViewCreated(AttributeSet attrs, View view) {
	listener.onViewCreated(view, attrs);
    }

    public interface ViewCreationListener {
	void onViewCreated(View view, AttributeSet attrs);
    }
}
