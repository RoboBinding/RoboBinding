package org.robobinding.viewattribute.listview;

import org.robobinding.viewattribute.ViewAttributeFactory;
import org.robobinding.viewattribute.adapterview.SubViewAttributes;

import android.widget.ListView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class HeaderAttributesFactory implements ViewAttributeFactory<SubViewAttributes<ListView>> {
    @Override
    public SubViewAttributes<ListView> create() {
	return new SubViewAttributes<ListView>(new HeaderAttributes());
    }
}
