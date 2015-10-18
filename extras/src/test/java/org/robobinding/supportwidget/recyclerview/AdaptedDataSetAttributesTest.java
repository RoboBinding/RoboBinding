package org.robobinding.supportwidget.recyclerview;

import org.junit.Test;
import org.robobinding.widget.AbstractGroupedViewAttributeTest;
import org.robolectric.annotation.Config;

import android.support.v7.widget.RecyclerView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@Config(manifest = Config.NONE)
public class AdaptedDataSetAttributesTest extends AbstractGroupedViewAttributeTest<RecyclerView, AdaptedDataSetAttributes> {
	private final Attribute source = attribute("source={source_property}");
	private final Attribute itemLayout = attribute("itemLayout=@layout/itemLayout");
	private final Attribute itemMapping = attribute("itemMapping=[text1.title:{title}]");

	@Test
	public void givenSourceAndItemLayoutAttributes_thenCreateInstancesOfBothAttributes() {
		givenAttributes(source, itemLayout);

		performInitialization();

		assertThatAttributesWereCreated(AdaptedDataSetAttributes.SOURCE, AdaptedDataSetAttributes.ITEM_LAYOUT);
	}

	@Test
	public void givenASourceItemLayoutAndItemMappingAttribute_thenCreateInstancesOfAllAttributes() {
		givenAttributes(source, itemLayout, itemMapping);

		performInitialization();

		assertThatAttributesWereCreated(AdaptedDataSetAttributes.SOURCE, AdaptedDataSetAttributes.ITEM_LAYOUT, AdaptedDataSetAttributes.ITEM_MAPPING);
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
}
