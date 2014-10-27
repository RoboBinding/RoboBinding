package org.robobinding.widget.radiogroup;

import org.robobinding.widget.view.AbstractViewEvent;

import android.widget.RadioGroup;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class CheckedChangeEvent extends AbstractViewEvent {
	private int checkedId;

	public CheckedChangeEvent(RadioGroup view, int checkedId) {
		super(view);
		this.checkedId = checkedId;
	}

	@Override
	public RadioGroup getView() {
		return (RadioGroup) super.getView();
	}

	public int getCheckedId() {
		return checkedId;
	}
}
