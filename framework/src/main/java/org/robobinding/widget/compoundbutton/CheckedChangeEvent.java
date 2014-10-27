package org.robobinding.widget.compoundbutton;

import org.robobinding.widget.view.AbstractViewEvent;

import android.widget.CompoundButton;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class CheckedChangeEvent extends AbstractViewEvent {
	private boolean checked;

	public CheckedChangeEvent(CompoundButton compoundButton, boolean isChecked) {
		super(compoundButton);
		checked = isChecked;
	}

	@Override
	public CompoundButton getView() {
		return (CompoundButton) super.getView();
	}

	public boolean isChecked() {
		return checked;
	}
}
