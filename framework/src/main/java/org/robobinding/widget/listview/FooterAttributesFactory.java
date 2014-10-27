package org.robobinding.widget.listview;

import org.robobinding.viewattribute.grouped.GroupedViewAttributeFactory;
import org.robobinding.widget.adapterview.SubViewAttributes;

import android.widget.ListView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class FooterAttributesFactory implements GroupedViewAttributeFactory<ListView> {
	@Override
	public SubViewAttributes<ListView> create() {
		return new SubViewAttributes<ListView>(new FooterAttributes());
	}

}
