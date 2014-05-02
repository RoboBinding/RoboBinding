package org.robobinding.viewattribute.listview;

import org.robobinding.viewattribute.AbstractReadOnlyPropertyViewAttribute;

import android.widget.ListView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class ChoiceModeAttribute extends AbstractReadOnlyPropertyViewAttribute<ListView, Integer> {
    @Override
    protected void valueModelUpdated(Integer newChoiceMode) {
	view.setChoiceMode(newChoiceMode);
    }

}
