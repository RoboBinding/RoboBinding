package org.robobinding.widget.adapterview;

import org.robobinding.widget.view.ClickEvent;

import android.view.View;
import android.widget.AdapterView;

/**
 * 
 * @since 1.0
 * @author Cheng Wei
 * @author Robert Taylor
 */
public class ItemClickEvent extends ClickEvent {
	private AdapterView<?> parent;
	private int position;
	private long id;

	ItemClickEvent(AdapterView<?> parent, View view, int position, long id) {
		super(view);
		this.parent = parent;
		this.position = position;
		this.id = id;
	}

	public int getPosition() {
		return position;
	}

	public long getId() {
		return id;
	}

	public AdapterView<?> getParent() {
		return parent;
	}
}
