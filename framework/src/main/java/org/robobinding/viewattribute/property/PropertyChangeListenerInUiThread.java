package org.robobinding.viewattribute.property;

import org.robobinding.property.PropertyChangeListener;

import android.os.Looper;

/**
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 *
 */
public class PropertyChangeListenerInUiThread implements PropertyChangeListener{
	private final PropertyChangeListener forwarding;
	public PropertyChangeListenerInUiThread(PropertyChangeListener forwarding) {
		this.forwarding = forwarding;
	}
	
	@Override
	public void propertyChanged() {
		if(notInUiThread()) {
			throw new RuntimeException("Updates to a PresentationModel have to be within the UI thread");
		}
		
		forwarding.propertyChanged();
	}
	
	private boolean notInUiThread() {
		return Thread.currentThread() != Looper.getMainLooper().getThread();
	}
}
