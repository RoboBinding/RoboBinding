package org.robobinding.widget.abslistview;

import org.robobinding.viewattribute.property.PropertyViewAttribute;

import android.widget.AbsListView;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class ChoiceModeAttribute implements PropertyViewAttribute<AbsListView, Integer> {
    @Override
    public void updateView(AbsListView view, Integer newChoiceMode) {
        new AbsListViewBackCompatible(view).setChoiceMode(newChoiceMode);
    }

}
