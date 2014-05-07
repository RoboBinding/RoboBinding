package org.robobinding.viewattribute.view;

import android.view.View;
import android.view.View.OnFocusChangeListener;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class MockViewListeners extends ViewListeners {
    public boolean addOnFocusChangeListenerInvoked;

    public MockViewListeners(View view) {
	super(view);
    }

    @Override
    public void addOnFocusChangeListener(OnFocusChangeListener listener) {
	addOnFocusChangeListenerInvoked = true;
	view.setOnFocusChangeListener(listener);
    }
}
