package org.robobinding.viewattribute.adapterview;

import static org.robobinding.viewattribute.adapterview.AbstractAdaptedDataSetAttributes.ITEM_LAYOUT;
import static org.robobinding.viewattribute.adapterview.AbstractAdaptedDataSetAttributes.ITEM_MAPPING;
import static org.robobinding.viewattribute.adapterview.AbstractAdaptedDataSetAttributes.SOURCE;

import org.junit.Test;
import org.robobinding.viewattribute.AbstractGroupedViewAttributeTest;

import android.view.View;
import android.widget.ListView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class AdaptedDataSetAttributesTest extends AbstractGroupedViewAttributeTest<AdaptedDataSetAttributes> {
    private final Attribute source = attribute("source={source_property}");
    private final Attribute itemLayout = attribute("itemLayout=@layout/itemLayout");
    private final Attribute itemMapping = attribute("itemMapping=[text1.title:{title}]");

    @Test
    public void givenSourceAndItemLayoutAttributes_thenCreateInstancesOfBothAttributes() {
	givenAttributes(source, itemLayout);

	performInitialization();

	assertThatAttributesWereCreated(SOURCE, ITEM_LAYOUT);
    }

    @Test
    public void givenASourceItemLayoutAndItemMappingAttribute_thenCreateInstancesOfAllAttributes() {
	givenAttributes(source, itemLayout, itemMapping);

	performInitialization();

	assertThatAttributesWereCreated(SOURCE, ITEM_LAYOUT, ITEM_MAPPING);
    }

    @Test(expected = RuntimeException.class)
    public void givenASourceAttributeOnly_thenReject() {
	givenAttributes(source);

	performInitialization();
    }

    @Test(expected = RuntimeException.class)
    public void givenAnItemLayoutAttributeOnly_thenReject() {
	givenAttributes(itemLayout);

	performInitialization();
    }

    @Override
    protected Class<? extends View> overrideViewClass() {
	return ListView.class;
    }
}
