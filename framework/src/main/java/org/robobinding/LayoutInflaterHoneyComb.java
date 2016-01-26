package org.robobinding;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.LayoutInflater.Factory;
import android.view.LayoutInflater.Factory2;
import android.view.View;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class LayoutInflaterHoneyComb extends LayoutInflaterPreHoneyComb {
	LayoutInflaterHoneyComb() {}
	
	public static LayoutInflater create(LayoutInflater original, ViewCreationListener listener, 
			ViewNameResolver viewNameResolver) {
		LayoutInflater newLayoutInflater = clone(original);
		Factory factory = wrapFactory(original, listener);
		if (factory != null) {
			newLayoutInflater.setFactory(factory);
        }
		
		Factory2 factory2 = wrapFactory2(original.getFactory2(), listener);
		if(factory2 != null) {
			newLayoutInflater.setFactory2(factory2);
			forceSetFactory2IfRequired(newLayoutInflater);
		}
		
		Factory2 privateFactory2 = wrapPrivateFactory2(original, viewNameResolver, listener, newLayoutInflater);
		setPrivateFactory2(newLayoutInflater, privateFactory2);
		return newLayoutInflater;
	}
	
	/**
	 * A fix to android.view.LayoutInflater.setFactory2 bug in early versions
	 *  - https://code.google.com/p/android/issues/detail?id=73779
	 */
	private static void forceSetFactory2IfRequired(LayoutInflater layoutInflater) {
		if(layoutInflater.getFactory() != layoutInflater.getFactory2()) {
			ReflectionUtils.setField(layoutInflater, "mFactory2", 
					Factory2.class, (Factory2)layoutInflater.getFactory());
		}
	}
	/**
	 * Fix for proguard warning.
	 */
	private static void setPrivateFactory2(LayoutInflater layoutInflater, Factory2 factory2) {
		ReflectionUtils.setField(layoutInflater, "mPrivateFactory", Factory2.class, factory2);
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
			invokeCompatEnsureSubDecorPre5_0(factory2);
			return new WithViewCreatedNotification2(factory2, listener);
		}
	}
	
	private static void invokeCompatEnsureSubDecorPre5_0(Factory2 factory2) {
		if (Build.VERSION.SDK_INT < Build_VERSION_CODES_LOLLIPOP) {
			Object mDelegateFactory = ReflectionUtils.tryToGetCompatibleField(factory2, "mDelegateFactory", Object.class);
			if(mDelegateFactory != null) {
				ReflectionUtils.tryToInvokeMethod(mDelegateFactory, "ensureSubDecor", new Object[0]);
			}
		}
	}
	
	private static class MConstructorArgs2Sync {
		private LayoutInflater original;
		private LayoutInflater newLayoutInflater;
		public MConstructorArgs2Sync(LayoutInflater original, LayoutInflater newLayoutInflater) {
			this.original = original;
			this.newLayoutInflater = newLayoutInflater;
		}
		
		/**
		 * Sync mConstructorArgs[1], as it is weirdly assigned from some Class under same package.
		 */
		public void sync() {
			mConstructorArgsOf(newLayoutInflater)[1] = mConstructorArgsOf(original)[1];
		}
	}
	
	private static Object[] mConstructorArgsOf(LayoutInflater layoutInflater) {
		return ReflectionUtils.getField(layoutInflater, "mConstructorArgs", Object[].class);
	}
	
	private static int Build_VERSION_CODES_LOLLIPOP = 21;
	private static Factory2 wrapPrivateFactory2(LayoutInflater original, ViewNameResolver viewNameResolver, 
			ViewCreationListener listener, LayoutInflater newLayoutInflater) {
		Factory2 privateFactory = getPrivateFactory(original);
		Factory2 factory2 = (privateFactory != null) ? privateFactory : EMPTY_FACTORY2;
		MConstructorArgs2Sync mConstructorArgs2Sync = new MConstructorArgs2Sync(original, newLayoutInflater);
		if (Build.VERSION.SDK_INT >= Build_VERSION_CODES_LOLLIPOP) {
			return new WithViewCreatedNotification2(
					new WithViewCreationIfNull5_0(factory2, viewNameResolver, newLayoutInflater, mConstructorArgs2Sync), 
					listener);
		} else {
			return new WithViewCreatedNotification2(
					new WithViewCreationIfNullPre5_0(factory2, viewNameResolver, newLayoutInflater, mConstructorArgs2Sync), 
					listener);
		}
	}
	
	/*================Start:Workaround code for LayoutInflater===================*/
	private static Factory2 getPrivateFactory(LayoutInflater layoutInflater) {
		return ReflectionUtils.getField(layoutInflater, "mPrivateFactory", LayoutInflater.Factory2.class);
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
	
	private static class WithViewCreationIfNull5_0 extends WithViewCreationIfNull implements Factory2 {
		private final Factory2 delegate;
		private final ViewNameResolver viewNameResolver;
		private final LayoutInflater layoutInflater;
		private final MConstructorArgs2Sync mConstructorArgs2Sync;
		public WithViewCreationIfNull5_0(Factory2 delegate, ViewNameResolver viewNameResolver, 
				LayoutInflater layoutInflater, MConstructorArgs2Sync mConstructorArgs2Sync) {
			super(delegate, viewNameResolver, layoutInflater);
			this.delegate = delegate;
			this.viewNameResolver = viewNameResolver;
			this.layoutInflater = layoutInflater;
			this.mConstructorArgs2Sync = mConstructorArgs2Sync;
		}
		
		@Override
		public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
			View view = delegate.onCreateView(parent, name, context, attrs);
			return createViewIfNull(view, name, context, attrs);
		}
		
		private View createViewIfNull(View viewOrNull, String name, Context context, AttributeSet attrs) {
			if(viewOrNull != null) return viewOrNull;
			
			String viewFullName = viewNameResolver.getViewNameFromLayoutTag(name);
			mConstructorArgs2Sync.sync();
			
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
	
	private static class WithViewCreationIfNullPre5_0 extends WithViewCreationIfNull implements Factory2 {
		private final Factory2 delegate;
		private final ViewNameResolver viewNameResolver;
		private final LayoutInflater layoutInflater;
		private final MConstructorArgs2Sync mConstructorArgs2Sync;
		public WithViewCreationIfNullPre5_0(Factory2 delegate, ViewNameResolver viewNameResolver, 
				LayoutInflater layoutInflater, MConstructorArgs2Sync mConstructorArgs2Sync) {
			super(delegate, viewNameResolver, layoutInflater);
			this.delegate = delegate;
			this.viewNameResolver = viewNameResolver;
			this.layoutInflater = layoutInflater;
			this.mConstructorArgs2Sync = mConstructorArgs2Sync;
		}
		
		@Override
		public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
			View view = delegate.onCreateView(parent, name, context, attrs);
			return createViewIfNull(view, name, context, attrs);
		}
		
		private View createViewIfNull(View viewOrNull, String name, Context context, AttributeSet attrs) {
			if(viewOrNull != null) return viewOrNull;
			
			String viewFullName = viewNameResolver.getViewNameFromLayoutTag(name);
			mConstructorArgs2Sync.sync();

			try {
				return layoutInflater.createView(viewFullName, null, attrs);
			} catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
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
