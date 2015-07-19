package org.robobinding.widget.view;

import org.robobinding.widgetaddon.view.ViewAddOnForView;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class MockViewAddOnForView extends ViewAddOnForView {
	private final View view;
	public boolean addOnClickListenerInvoked;
	public boolean addOnLongClickListenerInvoked;
	public boolean addOnFocusChangeListenerInvoked;
	public boolean addOnTouchListenerInvoked;

	public MockViewAddOnForView(View view) {
		super(null);
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

	@Override
	public void addOnTouchListener(OnTouchListener listener) {
		addOnTouchListenerInvoked = true;
		view.setOnTouchListener(listener);
	}

}
