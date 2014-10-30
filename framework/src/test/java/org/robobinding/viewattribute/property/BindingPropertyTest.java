package org.robobinding.viewattribute.property;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.robobinding.attribute.ValueModelAttribute;
import org.robobinding.presentationmodel.PresentationModelAdapter;
import org.robobinding.property.ValueModel;
import org.robobinding.util.RandomValues;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@RunWith(MockitoJUnitRunner.class)
public class BindingPropertyTest {
	@Mock
	OneWayPropertyViewAttribute<View, Integer> viewAttribute;
	@Mock
	View view;
	private Integer newValue;

	@Test
	public void whenPreInitializeView_thenViewIsSynchronizedWithNewValueFromValueModel() {
		newValue = RandomValues.anyInteger();
		BindingPropertyForTest bindingProperty = new BindingPropertyForTest(view, viewAttribute, null);

		bindingProperty.preInitializeView(null);

		verify(viewAttribute).updateView(view, newValue);
	}

	private class BindingPropertyForTest extends AbstractBindingProperty<View, Integer> {
		public BindingPropertyForTest(View view, OneWayPropertyViewAttribute<View, Integer> viewAttribute, ValueModelAttribute attribute) {
			super(view, viewAttribute, attribute);
		}

		@Override
		public ValueModel<Integer> getPropertyValueModel(PresentationModelAdapter presentationModelAdapter) {
			return ValueModelUtils.createInteger(newValue);
		}

		@Override
		public void performBind(PresentationModelAdapter presentationModelAdapter) {
		}
	}
}
