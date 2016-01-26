package org.robobinding;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.LayoutInflater.Factory;
import android.view.View;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class LayoutInflaterPreHoneyComb {
	LayoutInflaterPreHoneyComb() {}
	
	public static LayoutInflater create(LayoutInflater original, ViewCreationListener listener, 
			ViewNameResolver viewNameResolver) {
		LayoutInflater newLayoutInflater = clone(original);
		newLayoutInflater.setFactory(wrapFactory(original, newLayoutInflater, viewNameResolver, listener));
		return newLayoutInflater;
	}
	
	protected static LayoutInflater clone(LayoutInflater original) {
		LayoutInflater newLayoutInflater = original.cloneInContext(original.getContext());
		newLayoutInflater.setFilter(original.getFilter());
		return newLayoutInflater;
	}

	private static Factory wrapFactory(LayoutInflater original, LayoutInflater layoutInflater,
			ViewNameResolver viewNameResolver, ViewCreationListener listener) {
		Factory safeFactory = (original.getFactory() != null) ? original.getFactory() : EMPTY_FACTORY;
		return new WithViewCreatedNotification(
				new WithViewCreationIfNull(safeFactory, viewNameResolver, layoutInflater), 
				listener);
	}
	
	protected static class WithViewCreatedNotification implements Factory {
		private final Factory delegate;
		private final ViewCreationListener listener;
		public WithViewCreatedNotification(Factory delegate, ViewCreationListener listener) {
			this.delegate = delegate;
			this.listener = listener;
		}
		
		@Override
		public View onCreateView(String name, Context context, AttributeSet attrs) {
			View view = delegate.onCreateView(name, context, attrs);
			notifyViewCreatedIfNotNull(view, attrs);
			return view;
		}

		protected void notifyViewCreatedIfNotNull(View view, AttributeSet attrs) {
			if(view != null) {
				listener.onViewCreated(view, attrs);
			}
		}
	}
	
	protected static class WithViewCreationIfNull implements Factory {
		private final Factory delegate;
		private final ViewNameResolver viewNameResolver;
		private final LayoutInflater layoutInflater;
		public WithViewCreationIfNull(Factory delegate, ViewNameResolver viewNameResolver, 
				LayoutInflater layoutInflater) {
			this.delegate = delegate;
			this.viewNameResolver = viewNameResolver;
			this.layoutInflater = layoutInflater;
		}
		
		@Override
		public View onCreateView(String name, Context context, AttributeSet attrs) {
			View view = delegate.onCreateView(name, context, attrs);
			return createViewIfNull(view, name, attrs);
		}
		
		private View createViewIfNull(View viewOrNull, String name, AttributeSet attrs) {
			if(viewOrNull != null) return viewOrNull;
			
			String viewFullName = viewNameResolver.getViewNameFromLayoutTag(name);
			try {
				return layoutInflater.createView(viewFullName, null, attrs);
			} catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	private static Factory EMPTY_FACTORY = new Factory() {
		public View onCreateView(String name, Context context, AttributeSet attrs) {
			return null;
		}
	};
}
