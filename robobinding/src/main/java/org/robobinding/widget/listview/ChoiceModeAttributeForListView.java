package org.robobinding.widget.listview;

import org.robobinding.viewattribute.property.PropertyViewAttribute;

import android.widget.ListView;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class ChoiceModeAttributeForListView implements PropertyViewAttribute<ListView, Integer> {
    @Override
    public void updateView(ListView view, Integer newChoiceMode) {
        view.setChoiceMode(newChoiceMode);
    }

}
