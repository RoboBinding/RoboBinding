package org.robobinding;

import android.content.Context;
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
		if (layoutInflater.getFactory2() != null) {
			ViewFactory factory = createViewFactory(newLayoutInflater, layoutInflater.getFactory2());
			
			newLayoutInflater.setFactory2(factory);
		} else if(layoutInflater.getFactory() != null) {
			ViewFactory factory = createViewFactory(newLayoutInflater, new FactoryToFactory2(layoutInflater.getFactory()));
			
			newLayoutInflater.setFactory(factory);
		} else {
			Factory nullFactory = new Factory() {
				
				@Override
				public View onCreateView(String name, Context context, AttributeSet attrs) {
					return null;
				}
			};
			
			newLayoutInflater.setFactory(createViewFactory(newLayoutInflater, new FactoryToFactory2(nullFactory)));
		}
		
		return newLayoutInflater;
	}
	
	private LayoutInflater clone(LayoutInflater layoutInflater) {
		return layoutInflater.cloneInContext(layoutInflater.getContext());
	}
	
	private ViewFactory createViewFactory(LayoutInflater layoutInflater, Factory2 original) {
		return new ViewFactory(layoutInflater, original, new ViewNameResolver(), listener);
	}
	
	private static class FactoryToFactory2 implements Factory2 {
		private final Factory factory;
		
		public FactoryToFactory2(Factory factory) {
			this.factory = factory;
		}

		@Override
		public View onCreateView(String name, Context context, AttributeSet attrs) {
			return factory.onCreateView(name, context, attrs);
		}
		
		@Override
		public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
			return factory.onCreateView(name, context, attrs);
		}
	}
}
