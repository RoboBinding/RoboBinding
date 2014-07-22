package org.robobinding.widget.view;

import android.view.View;
import org.robobinding.viewattribute.property.PropertyViewAttribute;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Aurélien Catinon
 */
public class ActivatedAttribute implements PropertyViewAttribute<View, Boolean> {
    @Override
    public void updateView(View view, Boolean newValue) {
        view.setActivated(newValue);
    }
}
