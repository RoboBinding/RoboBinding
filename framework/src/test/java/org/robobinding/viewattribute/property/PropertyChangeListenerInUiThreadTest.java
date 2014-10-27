package org.robobinding.viewattribute.property;

import static org.fest.assertions.api.Assertions.fail;

import java.lang.Thread.UncaughtExceptionHandler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robobinding.property.PropertyChangeListener;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

/**
 * @since 1.0
 * @version
 * @author Cheng Wei
 * 
 */
@Config(manifest=Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class PropertyChangeListenerInUiThreadTest {
	@Test
	public void whenRunPropertyChangeListenerInUiThread_thenNoException() {
		/**
		 * Robolectric is running tests in UI thread by default.
		 */
		newPropertyChangeListenerInUiThread().propertyChanged();
	}

	private PropertyChangeListenerInUiThread newPropertyChangeListenerInUiThread() {
		return new PropertyChangeListenerInUiThread(new PropertyChangeListener() {
			@Override
			public void propertyChanged() {
			}
		});
	}

	@Test(expected = RuntimeException.class)
	public void whenRunPropertyChangeListenerInNonUiThread_thenThrowsException() throws Throwable {
		ThrowableUncaughtExceptionHandler throwableHandler = new ThrowableUncaughtExceptionHandler();
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				newPropertyChangeListenerInUiThread().propertyChanged();
			}
		});
		t.setUncaughtExceptionHandler(throwableHandler);
		t.start();
		t.join();
		throwableHandler.throwExceptionIfExists();
		fail("Expect errors when running PropertyChangeListener in non-UI thread");
	}
	
	private static class ThrowableUncaughtExceptionHandler implements UncaughtExceptionHandler {
		private Throwable caughtException;
		@Override
		public void uncaughtException(Thread t, Throwable e) {
			caughtException =  e;
		}
		
		public void throwExceptionIfExists() throws Throwable {
			if(caughtException != null) {
				throw caughtException;
			}
		}
	}
}
