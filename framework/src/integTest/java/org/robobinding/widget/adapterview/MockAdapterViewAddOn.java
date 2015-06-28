package org.robobinding.widget.adapterview;

import org.robobinding.widgetaddon.adapterview.AdapterViewAddOn;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class MockAdapterViewAddOn extends AdapterViewAddOn {
	private final AdapterView<?> adapterView;
	public boolean addOnItemSelectedListenerInvoked;
	public boolean addOnItemClickListenerInvoked;

	public MockAdapterViewAddOn(AdapterView<?> adapterView) {
		super(adapterView);
		this.adapterView = adapterView;
	}

	@Override
	public void addOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
		adapterView.setOnItemSelectedListener(onItemSelectedListener);
		addOnItemSelectedListenerInvoked = true;
	}

	@Override
	public void addOnItemClickListener(OnItemClickListener onItemClickListener) {
		adapterView.setOnItemClickListener(onItemClickListener);
		addOnItemClickListenerInvoked = true;
	}

}
