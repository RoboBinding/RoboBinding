package org.robobinding.widget.view;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnLongClickListener;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class MockViewListeners extends ViewListeners {
    private final View view;
    public boolean addOnClickListenerInvoked;
    public boolean addOnLongClickListenerInvoked;
    public boolean addOnFocusChangeListenerInvoked;

    public MockViewListeners(View view) {
	super(view);
	this.view = view;
    }

    @Override
    public void addOnClickListener(OnClickListener listener) {
        addOnClickListenerInvoked = true;
        view.setOnClickListener(listener);
    }

    @Override
    public void addOnLongClickListener(OnLongClickListener listener) {
        addOnLongClickListenerInvoked = true;
        view.setOnLongClickListener(listener);
    }

    @Override
    public void addOnFocusChangeListener(OnFocusChangeListener listener) {
	addOnFocusChangeListenerInvoked = true;
	view.setOnFocusChangeListener(listener);
    }

}
