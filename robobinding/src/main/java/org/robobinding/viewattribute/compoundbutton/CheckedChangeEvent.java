package org.robobinding.viewattribute.compoundbutton;

import org.robobinding.viewattribute.view.AbstractViewEvent;

import android.widget.CompoundButton;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class CheckedChangeEvent extends AbstractViewEvent {
    private boolean checked;

    CheckedChangeEvent(CompoundButton compoundButton, boolean isChecked) {
	super(compoundButton);
	checked = isChecked;
    }

    public CompoundButton getCompoundButton() {
	return (CompoundButton) getView();
    }

    public boolean isChecked() {
	return checked;
    }
}
