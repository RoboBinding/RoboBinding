package org.robobinding.widget.view;

import android.view.View;
import org.robobinding.viewattribute.property.PropertyViewAttribute;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Aurélien Catinon
 */
public class PaddingAttribute implements PropertyViewAttribute<View, Integer> {
    @Override
    public void updateView(View view, Integer newValue) {
        view.setPadding(newValue, newValue, newValue, newValue);
    }
}
