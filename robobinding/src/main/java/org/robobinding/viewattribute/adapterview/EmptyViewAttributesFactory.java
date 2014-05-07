package org.robobinding.viewattribute.adapterview;

import org.robobinding.viewattribute.ViewAttributeFactory;

import android.widget.AdapterView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class EmptyViewAttributesFactory implements ViewAttributeFactory<SubViewAttributes<AdapterView<?>>> {
    @Override
    public SubViewAttributes<AdapterView<?>> create() {
	return new SubViewAttributes<AdapterView<?>>(new EmptyViewAttributes());
    }

}
