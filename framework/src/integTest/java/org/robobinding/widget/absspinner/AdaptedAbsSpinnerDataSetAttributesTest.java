package org.robobinding.widget.absspinner;

import static org.robobinding.widget.absspinner.AdaptedAbsSpinnerDataSetAttributes.DROPDOWN_LAYOUT;
import static org.robobinding.widget.absspinner.AdaptedAbsSpinnerDataSetAttributes.DROPDOWN_MAPPING;
import static org.robobinding.widget.adapterview.AbstractAdaptedDataSetAttributes.ITEM_LAYOUT;
import static org.robobinding.widget.adapterview.AbstractAdaptedDataSetAttributes.ITEM_MAPPING;
import static org.robobinding.widget.adapterview.AbstractAdaptedDataSetAttributes.SOURCE;

import org.junit.Test;
import org.robobinding.widget.AbstractGroupedViewAttributeTest;
import org.robolectric.annotation.Config;

import android.widget.Spinner;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@Config(manifest = Config.NONE)
public class AdaptedAbsSpinnerDataSetAttributesTest extends AbstractGroupedViewAttributeTest<Spinner, AdaptedAbsSpinnerDataSetAttributes> {
	private final Attribute source = attribute("source={source_property}");
	private final Attribute itemLayout = attribute("itemLayout=@layout/itemLayout");
	private final Attribute itemMapping = attribute("itemMapping=[text1.title:{title}]");
	private final Attribute dropdownLayout = attribute("dropdownLayout=@layout/dropdownLayout");
	private final Attribute dropdownMapping = attribute("dropdownMapping=[text1.title:{artist}]");

	@Test
	public void givenSourceItemLayoutAndDropdownLayout_thenCreateAllAttributes() {
		givenAttributes(source, itemLayout, dropdownLayout);

		performInitialization();

		assertThatAttributesWereCreated(SOURCE, ITEM_LAYOUT, DROPDOWN_LAYOUT);
	}

	@Test
	public void givenSourceItemLayoutDropdownLayoutAndDropdownMapping_thenCreateAllAttributes() {
		givenAttributes(source, itemLayout, dropdownLayout, dropdownMapping);

		performInitialization();

		assertThatAttributesWereCreated(SOURCE, ITEM_LAYOUT, DROPDOWN_LAYOUT, DROPDOWN_MAPPING);
	}

	@Test
	public void givenAllDataSetAttributes_thenCreateAllAttributes() {
		givenAttributes(source, itemLayout, itemMapping, dropdownLayout, dropdownMapping);

		performInitialization();

		assertThatAttributesWereCreated(SOURCE, ITEM_LAYOUT, ITEM_MAPPING, DROPDOWN_LAYOUT, DROPDOWN_MAPPING);
	}

	@Test(expected = RuntimeException.class)
	public void givenDropdownLayoutAttributeOnly_thenReject() {
		givenAttributes(dropdownLayout);

		performInitialization();
	}
}
