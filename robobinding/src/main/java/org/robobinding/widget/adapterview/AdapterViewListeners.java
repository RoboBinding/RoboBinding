package org.robobinding.widget.adapterview;

import org.robobinding.widget.view.ViewListenersForView;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class AdapterViewListeners extends ViewListenersForView {
    private final AdapterView<?> adapterView;
    private OnItemSelectedListeners onItemSelectedListeners;
    private OnItemClickListeners onItemClickListeners;

    public AdapterViewListeners(AdapterView<?> adapterView) {
	super(adapterView);
	this.adapterView = adapterView;
    }

    public void addOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
	ensureOnItemSelectedListenersInitialized();
	onItemSelectedListeners.addListener(onItemSelectedListener);
    }

    public void addOnItemClickListener(OnItemClickListener onItemClickListener) {
	ensureOnItemClickListenersInitialized();
	onItemClickListeners.addListener(onItemClickListener);
    }

    private void ensureOnItemSelectedListenersInitialized() {
	if (onItemSelectedListeners == null) {
	    onItemSelectedListeners = new OnItemSelectedListeners();
	    adapterView.setOnItemSelectedListener(onItemSelectedListeners);
	}
    }

    private void ensureOnItemClickListenersInitialized() {
	if (onItemClickListeners == null) {
	    onItemClickListeners = new OnItemClickListeners();
	    adapterView.setOnItemClickListener(onItemClickListeners);
	}

    }
}
