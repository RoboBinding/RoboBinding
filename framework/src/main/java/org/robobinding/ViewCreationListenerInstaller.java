package org.robobinding;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.LayoutInflater.Factory;
import android.view.LayoutInflater.Factory2;
import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 * 
 */
public class ViewCreationListenerInstaller {
	private final ViewCreationListener listener;

	public ViewCreationListenerInstaller(ViewCreationListener listener) {
		this.listener = listener;
	}

	public LayoutInflater installWith(final LayoutInflater layoutInflater) {
		LayoutInflater newLayoutInflater = clone(layoutInflater);
		if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) 
				&& (layoutInflater.getFactory2() != null)) {
			ViewFactory2 factory2 = createViewFactory2(newLayoutInflater, layoutInflater.getFactory2());
			
			newLayoutInflater.setFactory2(factory2);
			forceSetFactory2IfRequired(newLayoutInflater);
		} else if(layoutInflater.getFactory() != null) {
			ViewFactory factory = createViewFactory(newLayoutInflater, layoutInflater.getFactory());
			
			newLayoutInflater.setFactory(factory);
		} else {
			Factory nullFactory = new Factory() {
				
				@Override
				public View onCreateView(String name, Context context, AttributeSet attrs) {
					return null;
				}
			};
			
			newLayoutInflater.setFactory(createViewFactory(newLayoutInflater, nullFactory));
		}
		
		return newLayoutInflater;
	}
	
	private LayoutInflater clone(LayoutInflater layoutInflater) {
		return layoutInflater.cloneInContext(layoutInflater.getContext());
	}

	private ViewFactory2 createViewFactory2(LayoutInflater layoutInflater, Factory2 original) {
		return new ViewFactory2(layoutInflater, original, new ViewNameResolver(), listener);
	}

	/**
	 * A fix to android.view.LayoutInflater.setFactory2 bug in early versions
	 *  - https://code.google.com/p/android/issues/detail?id=73779
	 */
	private void forceSetFactory2IfRequired(LayoutInflater newLayoutInflater) {
		if(newLayoutInflater.getFactory() != newLayoutInflater.getFactory2()) {
			ReflectionUtils.setField(newLayoutInflater, "mFactory2", 
					LayoutInflater.Factory2.class, (LayoutInflater.Factory2)newLayoutInflater.getFactory());
		}
	}

	private ViewFactory createViewFactory(LayoutInflater layoutInflater, Factory original) {
		return new ViewFactory(layoutInflater, original, new ViewNameResolver(), listener);
	}
}
