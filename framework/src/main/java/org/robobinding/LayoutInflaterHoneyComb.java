package org.robobinding;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class LayoutInflaterHoneyComb extends LayoutInflaterPreHoneyComb {
	public LayoutInflaterHoneyComb(Context context, Factory factory, Filter filter,
			Factory2 factory2, Factory2 privateFactory2) {
		super(context, factory, filter);
		
		if(factory2 != null) {
			setFactory2(factory2);
			forceSetFactory2IfRequired();
		}
		
		setPrivateFactory(privateFactory2);
	}
	/**
	 * A fix to android.view.LayoutInflater.setFactory2 bug in early versions
	 *  - https://code.google.com/p/android/issues/detail?id=73779
	 */
	private void forceSetFactory2IfRequired() {
		if(getFactory() != getFactory2()) {
			ReflectionUtils.setField(this, "mFactory2", 
					LayoutInflater.Factory2.class, (LayoutInflater.Factory2)getFactory());
		}
	}
	
	public static LayoutInflater create(LayoutInflater original, ViewCreationListener listener, 
			ViewNameResolver viewNameResolver) {
		
		return new LayoutInflaterHoneyComb(
				original.getContext(), 
				wrapFactory(original, listener), 
				original.getFilter(),
				wrapFactory2(original.getFactory2(), listener),
				wrapPrivateFactory2(original, viewNameResolver, listener));
	}
	
	private static Factory wrapFactory(LayoutInflater original, ViewCreationListener listener) {
		Factory factory = original.getFactory();
		if((original.getFactory2() != null) || (factory == null)) {
			return null;
		} else {
			return new WithViewCreatedNotification(factory, listener);
		}
	}
	
	private static Factory2 wrapFactory2(Factory2 factory2, ViewCreationListener listener) {
		if(factory2 == null) {
			return null;
		} else {
			return new WithViewCreatedNotification2(factory2, listener);
		}
	}

	private static Factory2 wrapPrivateFactory2(LayoutInflater original, ViewNameResolver viewNameResolver, 
			ViewCreationListener listener) {
		Factory2 privateFactory = getPrivateFactory(original);
		Factory2 factory2 = (privateFactory != null) ? privateFactory : EMPTY_FACTORY2;
		return new WithViewCreatedNotification2(
				new WithViewCreationIfNull2(factory2, viewNameResolver, original), 
				listener);
	}
	
	/*================Start:Workaround code for LayoutInflater===================*/
	private static Factory2 getPrivateFactory(LayoutInflater layoutInflater) {
		return ReflectionUtils.getField(layoutInflater, "mPrivateFactory", LayoutInflater.Factory2.class);
	}
	
	private static Object[] mConstructorArgsOf(LayoutInflater layoutInflater) {
		return ReflectionUtils.getField(layoutInflater, "mConstructorArgs", Object[].class);
	}
	/*================End:Workaround code for LayoutInflater=====================*/
	
	private static class WithViewCreatedNotification2 extends WithViewCreatedNotification implements Factory2 {
		private final Factory2 delegate;
		public WithViewCreatedNotification2(Factory2 delegate, ViewCreationListener listener) {
			super(delegate, listener);
			
			this.delegate = delegate;
		}
		
		@Override
		public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
			View view = delegate.onCreateView(parent, name, context, attrs);
			notifyViewCreatedIfNotNull(view, attrs);
			return view;
		}
	}
	
	private static class WithViewCreationIfNull2 extends WithViewCreationIfNull implements Factory2 {
		private final Factory2 delegate;
		private final ViewNameResolver viewNameResolver;
		private final LayoutInflater layoutInflater;
		public WithViewCreationIfNull2(Factory2 delegate, ViewNameResolver viewNameResolver, 
				LayoutInflater layoutInflater) {
			super(delegate, viewNameResolver, layoutInflater);
			this.delegate = delegate;
			this.viewNameResolver = viewNameResolver;
			this.layoutInflater = layoutInflater;
		}
		
		@Override
		public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
			View view = delegate.onCreateView(parent, name, context, attrs);
			return createViewIfNull(view, name, context, attrs);
		}
		
		private View createViewIfNull(View viewOrNull, String name, Context context, AttributeSet attrs) {
			if(viewOrNull != null) return viewOrNull;
			
			String viewFullName = viewNameResolver.getViewNameFromLayoutTag(name);

			final Object lastContext = mConstructorArgsOf(layoutInflater)[0];
			mConstructorArgsOf(layoutInflater)[0] = context;
			try {
				return layoutInflater.createView(viewFullName, null, attrs);
			} catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
			} finally {
				mConstructorArgsOf(layoutInflater)[0] = lastContext;
			}
		}
	}
	
	private static Factory2 EMPTY_FACTORY2 = new Factory2() {
		
		@Override
		public View onCreateView(String name, Context context, AttributeSet attrs) {
			return null;
		}
		
		@Override
		public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
			return null;
		}
	};

}
