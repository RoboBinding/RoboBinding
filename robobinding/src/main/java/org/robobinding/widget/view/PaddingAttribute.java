package org.robobinding.widget.view;

import android.view.View;
import org.robobinding.viewattribute.property.PropertyViewAttribute;

/**
 * Created by aurel on 31/08/14.
 */
public class PaddingAttribute implements PropertyViewAttribute<View, Integer> {
    @Override
    public void updateView(View view, Integer newValue) {
        view.setPadding(newValue, newValue, newValue, newValue);
    }
}
