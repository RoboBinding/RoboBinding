package org.robobinding.widget.listview;

import static org.robobinding.widget.listview.FooterAttributes.FOOTER_LAYOUT;
import static org.robobinding.widget.listview.FooterAttributes.FOOTER_PRESENTATION_MODEL;
import static org.robobinding.widget.listview.FooterAttributes.FOOTER_VISIBILITY;
import static org.robobinding.widget.listview.HeaderAttributes.HEADER_LAYOUT;
import static org.robobinding.widget.listview.HeaderAttributes.HEADER_PRESENTATION_MODEL;
import static org.robobinding.widget.listview.HeaderAttributes.HEADER_VISIBILITY;

import org.robobinding.viewbinding.BindingAttributeMappings;
import org.robobinding.viewbinding.ViewBinding;

import android.widget.ListView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ListViewBinding implements ViewBinding<ListView> {
	@Override
	public void mapBindingAttributes(BindingAttributeMappings<ListView> mappings) {
		mappings.mapGroupedAttribute(new HeaderAttributesFactory(), HEADER_LAYOUT, HEADER_PRESENTATION_MODEL, HEADER_VISIBILITY);
		mappings.mapGroupedAttribute(new FooterAttributesFactory(), FOOTER_LAYOUT, FOOTER_PRESENTATION_MODEL, FOOTER_VISIBILITY);
	}
}
