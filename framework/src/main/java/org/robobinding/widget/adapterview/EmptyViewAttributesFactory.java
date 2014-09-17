package org.robobinding.widget.adapterview;

import org.robobinding.viewattribute.grouped.GroupedViewAttributeFactory;

import android.widget.AdapterView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class EmptyViewAttributesFactory implements GroupedViewAttributeFactory<AdapterView<?>> {
	@Override
	public SubViewAttributes<AdapterView<?>> create() {
		return new SubViewAttributes<AdapterView<?>>(new EmptyViewAttributes());
	}

}
