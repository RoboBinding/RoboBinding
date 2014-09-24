package org.robobinding.viewattribute.property;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.robobinding.attribute.Attributes.aValueModelAttribute;
import static org.robobinding.viewattribute.property.MockPresentationModelAdapterBuilder.aPresentationModelAdapterWithProperty;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robobinding.attribute.ValueModelAttribute;
import org.robobinding.presentationmodel.PresentationModelAdapter;
import org.robobinding.property.AbstractValueModel;
import org.robobinding.property.ValueModel;
import org.robobinding.property.ValueModelUtils;
import org.robobinding.util.RandomValues;
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
	private PropertyViewAttributeSpy viewAttributeSpy;
	private ValueModel<Integer> valueModel;

	@Before
	public void setUp() {
		view = Mockito.mock(View.class);
		viewAttributeSpy = new PropertyViewAttributeSpy();
		valueModel = ValueModelUtils.create(-1);
	}

	@Test
	public void givenABoundProperty_whenUpdateValueModel_thenViewIsSynchronized() {
		aBoundProperty();

		Integer newValue = RandomValues.anyInteger();
		valueModel.setValue(newValue);

		assertThat(viewAttributeSpy.viewValue, is(newValue));
	}

	private TwoWayBindingProperty<View, Integer> aBoundProperty() {
		ValueModelAttribute attribute = aValueModelAttribute(PROPERTY_NAME);
		TwoWayBindingProperty<View, Integer> bindingProperty = new TwoWayBindingProperty<View, Integer>(view, viewAttributeSpy, attribute);

		PresentationModelAdapter presentationModelAdapter = aPresentationModelAdapterWithProperty(PROPERTY_NAME, valueModel);
		bindingProperty.performBind(presentationModelAdapter);
		return bindingProperty;
	}

	@Test
	public void givenABoundProperty_whenTheViewIsUpdated_thenValueModelShouldBeUpdated() {
		aBoundProperty();

		Integer newValue = RandomValues.anyInteger();
		viewAttributeSpy.simulateViewUpdate(newValue);

		assertThat(valueModel.getValue(), is(newValue));
	}

	@Test
	public void givenABoundProperty_whenTheViewIsUpdated_thenViewShouldNotReceiveAFurtherUpdate() {
		aBoundProperty();

		Integer newValue = RandomValues.anyInteger();
		viewAttributeSpy.simulateViewUpdate(newValue);

		assertThat(viewAttributeSpy.viewUpdateNotificationCount, is(0));
	}

	@Test
	public void givenABoundProperty_whenValueModelIsUpdated_thenViewShouldReceiveOnlyASingleUpdate() {
		aBoundProperty();

		Integer newValue = RandomValues.anyInteger();
		valueModel.setValue(newValue);

		assertThat(viewAttributeSpy.viewUpdateNotificationCount, is(1));
	}

	@Test
	public void givenABoundPropertyOfAlwaysRestoringValue_whenTheViewIsUpdated_thenViewShouldReceiveAnUpdateWithRestoredValue() {
		valueModel = new AlwaysRestoreValueModel();
		aBoundProperty();

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
}
