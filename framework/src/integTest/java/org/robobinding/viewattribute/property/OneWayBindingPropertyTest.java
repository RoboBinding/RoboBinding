package org.robobinding.viewattribute.property;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.robobinding.attribute.Attributes.aValueModelAttribute;
import static org.robobinding.viewattribute.MockBindingContextBuilder.aBindingContextWithReadOnlyProperty;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robobinding.BindingContext;
import org.robobinding.attribute.ValueModelAttribute;
import org.robobinding.property.ValueModel;
import org.robobinding.util.RandomValues;
import org.robobinding.viewattribute.ValueModelUtils;
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
public class OneWayBindingPropertyTest {
	private static final String PROPERTY_NAME = "readOnlyProperty1";
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

	private OneWayBindingProperty aBoundProperty() {
		ValueModelAttribute attribute = aValueModelAttribute(PROPERTY_NAME);
		OneWayBindingProperty bindingProperty = new OneWayBindingProperty(view, viewAttributeSpy, attribute);

		BindingContext bindingContext = aBindingContextWithReadOnlyProperty(PROPERTY_NAME, valueModel);
		bindingProperty.performBind(bindingContext);
		return bindingProperty;
	}
}
