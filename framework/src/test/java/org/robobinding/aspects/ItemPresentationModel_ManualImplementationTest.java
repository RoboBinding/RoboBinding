package org.robobinding.aspects;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.not;

import org.junit.Assert;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.robobinding.itempresentationmodel.ItemPresentationModel;
import org.robobinding.presentationmodel.AbstractPresentationModel;
import org.robobinding.property.ObservableBean;
import org.robobinding.property.PropertyChangeListener;

/**
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 * 
 */
@RunWith(Theories.class)
public class ItemPresentationModel_ManualImplementationTest {

	@SuppressWarnings("rawtypes")
	@DataPoints
	public static ItemPresentationModel[] manualPresentationModelImplementations = { new ByInterface(), new BySubclassing() };

	@Theory
	public void whenImplementsPrensentationModelManually_thenNoAutoCodeGenerationTriggered(
			@SuppressWarnings("rawtypes") ItemPresentationModel manualItemPresentationModelImplementation) {
		Assert.assertThat(manualItemPresentationModelImplementation, not(instanceOf(PresentationModelMixin.class)));
	}

	public static class ByInterface implements ItemPresentationModel<Object>, ObservableBean {
		@Override
		public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		}

		@Override
		public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		}

		@Override
		public void updateData(int index, Object bean) {
		}
	}

	public static class BySubclassing extends AbstractPresentationModel implements ItemPresentationModel<Object> {
		@Override
		public void updateData(int index, Object bean) {
		}
	}

}
