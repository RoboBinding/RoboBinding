package org.robobinding.annotation;

/**
 * Used to indicate whether to pre-initializing views.
 * 
 * @since 1.0
 * @author Cheng Wei
 *
 */
public enum PreInitializingViews {
	/**
	 * To pre-initialize views.
	 */
	YES {
		@Override
		public boolean asBooleanWithDefault(boolean preInitializeViews) {
			return true;
		}
	}, 
	/**
	 * Not to pre-initialize views.
	 */
	NO {
		@Override
		public boolean asBooleanWithDefault(boolean preInitializeViews) {
			return false;
		}
	}, 
	/**
	 * Use default preInitializingViews settings.
	 */
	DEFAULT {
		@Override
		public boolean asBooleanWithDefault(boolean preInitializeViews) {
			return preInitializeViews;
		}
	};

	public abstract boolean asBooleanWithDefault(boolean preInitializeViews);
}
