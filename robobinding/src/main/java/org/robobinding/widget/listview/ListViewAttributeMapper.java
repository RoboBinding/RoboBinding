package org.robobinding.widget.listview;

import static org.robobinding.widget.listview.FooterAttributes.FOOTER_LAYOUT;
import static org.robobinding.widget.listview.FooterAttributes.FOOTER_PRESENTATION_MODEL;
import static org.robobinding.widget.listview.FooterAttributes.FOOTER_VISIBILITY;
import static org.robobinding.widget.listview.HeaderAttributes.HEADER_LAYOUT;
import static org.robobinding.widget.listview.HeaderAttributes.HEADER_PRESENTATION_MODEL;
import static org.robobinding.widget.listview.HeaderAttributes.HEADER_VISIBILITY;

import org.robobinding.viewattribute.BindingAttributeMapper;
import org.robobinding.viewattribute.BindingAttributeMappings;

import android.widget.ListView;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ListViewAttributeMapper implements BindingAttributeMapper<ListView> {
    @Override
    public void mapBindingAttributes(BindingAttributeMappings<ListView> mappings) {
	mappings.mapProperty(CheckedItemPositionAttribute.class, "checkedItemPosition");
	mappings.mapMultiTypeProperty(CheckedItemPositionsAttribute.class, "checkedItemPositions");
	mappings.mapProperty(ChoiceModeAttribute.class, "choiceMode");

	mappings.mapGroupedAttribute(new HeaderAttributesFactory(), HEADER_LAYOUT, HEADER_PRESENTATION_MODEL, HEADER_VISIBILITY);
	mappings.mapGroupedAttribute(new FooterAttributesFactory(), FOOTER_LAYOUT, FOOTER_PRESENTATION_MODEL, FOOTER_VISIBILITY);
    }
}
