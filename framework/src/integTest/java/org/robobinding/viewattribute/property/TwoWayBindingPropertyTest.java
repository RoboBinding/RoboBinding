package org.robobinding.viewattribute.property;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.robobinding.attribute.Attributes.aValueModelAttribute;
import static org.robobinding.viewattribute.MockBindingContextBuilder.aBindingContextWithProperty;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robobinding.BindingContext;
import org.robobinding.attribute.ValueModelAttribute;
import org.robobinding.property.AbstractValueModel;
import org.robobinding.property.ValueModel;
import org.robobinding.util.RandomValues;
import org.robobinding.viewattribute.ValueModelUtils;
import org.robobinding.widgetaddon.ViewAddOn;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@Config(manifest=Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class TwoWayBindingPropertyTest {
	private static final String PROPERTY_NAME = "property2";
	private View view;
	private ViewAddOn viewAddOn;
	private TwoWayPropertyViewAttributeSpy viewAttributeSpy;

	@Before
	public void setUp() {
		view = Mockito.mock(View.class);
		viewAddOn = Mockito.mock(ViewAddOn.class);
		viewAttributeSpy = new TwoWayPropertyViewAttributeSpy();
	}

	@Test
	public void givenABoundProperty_whenUpdateValueModel_thenViewIsSynchronized() {
		ValueModel<Integer> valueModel = ValueModelUtils.create(-1);
		aBoundProperty(valueModel);

		Integer newValue = RandomValues.anyInteger();
		valueModel.setValue(newValue);

		assertThat(viewAttributeSpy.viewValue, is(newValue));
	}

	private TwoWayBindingProperty aBoundProperty(ValueModel<Integer> valueModel) {
		ValueModelAttribute attribute = aValueModelAttribute(PROPERTY_NAME);
		TwoWayBindingProperty bindingProperty = new TwoWayBindingProperty(view, viewAddOn, viewAttributeSpy, attribute);

		BindingContext bindingContext = aBindingContextWithProperty(PROPERTY_NAME, valueModel);
		bindingProperty.performBind(bindingContext);
		return bindingProperty;
	}
	
	@Test
	public void givenABoundProperty_whenUpdateValueModel_thenShouldNotReceiveAReturnUpdateFromView() {
		ValueModelSpy valueModel = new ValueModelSpy(0);
		aBoundProperty(valueModel);

		Integer newValue = RandomValues.anyInteger();
		valueModel.setValue(newValue);

		assertThat(valueModel.updateCount, is(1));
	}

	@Test
	public void givenABoundProperty_whenTheViewIsUpdated_thenValueModelShouldBeUpdated() {
		ValueModel<Integer> valueModel = ValueModelUtils.create(-1);
		aBoundProperty(valueModel);

		Integer newValue = RandomValues.anyInteger();
		viewAttributeSpy.simulateViewUpdate(newValue);

		assertThat(valueModel.getValue(), is(newValue));
	}

	@Test
	public void givenABoundProperty_whenTheViewIsUpdated_thenViewShouldNotReceiveAFurtherUpdate() {
		ValueModel<Integer> valueModel = ValueModelUtils.create(-1);
		aBoundProperty(valueModel);

		Integer newValue = RandomValues.anyInteger();
		viewAttributeSpy.simulateViewUpdate(newValue);

		assertThat(viewAttributeSpy.viewUpdateNotificationCount, is(0));
	}

	@Test
	public void givenABoundProperty_whenValueModelIsUpdated_thenViewShouldReceiveOnlyASingleUpdate() {
		ValueModel<Integer> valueModel = ValueModelUtils.create(-1);
		aBoundProperty(valueModel);

		Integer newValue = RandomValues.anyInteger();
		valueModel.setValue(newValue);

		assertThat(viewAttributeSpy.viewUpdateNotificationCount, is(1));
	}

	@Test
	public void givenABoundPropertyOfAlwaysRestoringValue_whenTheViewIsUpdated_thenViewShouldReceiveAnUpdateWithRestoredValue() {
		ValueModel<Integer> valueModel = new AlwaysRestoreValueModel();
		aBoundProperty(valueModel);

		Integer newValue = RandomValues.anyInteger();
		viewAttributeSpy.simulateViewUpdate(newValue);

		assertThat(viewAttributeSpy.viewUpdateNotificationCount, is(1));
		assertThat(viewAttributeSpy.viewValue, is(AlwaysRestoreValueModel.ALWAYS_RESTORE_VALUE));
	}

	public static class AlwaysRestoreValueModel extends AbstractValueModel<Integer> {
		private static final int ALWAYS_RESTORE_VALUE = 888888888;

		public AlwaysRestoreValueModel() {
			super(ALWAYS_RESTORE_VALUE);
		}

		@Override
		public void setValue(Integer newValue) {
			super.setValue(newValue);
			if (newValue != ALWAYS_RESTORE_VALUE) {
				this.value = ALWAYS_RESTORE_VALUE;
				fireValueChange();
			}
		}
	}
	
	public static class ValueModelSpy extends AbstractValueModel<Integer> {
		int updateCount;
		
		public ValueModelSpy(int value) {
			super(value);
			updateCount = 0;
		}
		@Override
		public void setValue(Integer newValue) {
			updateCount++;
			super.setValue(newValue);
		}
	}
}
