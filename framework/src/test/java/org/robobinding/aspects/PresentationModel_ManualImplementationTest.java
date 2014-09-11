package org.robobinding.aspects;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.not;

import org.junit.Assert;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.robobinding.presentationmodel.AbstractPresentationModel;
import org.robobinding.property.ObservableBean;
import org.robobinding.property.PropertyChangeListener;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@RunWith(Theories.class)
public class PresentationModel_ManualImplementationTest {


	@DataPoints
	public static ObservableBean[] manualPresentationModelImplementations = {
			new ByInterface(),
			new BySubclassing() };

	@Theory
	public void whenImplementsPrensentationModelManually_thenNoAutoCodeGenerationTriggered(
			ObservableBean manualPresentationModelImplementation) {
		Assert.assertThat(
				manualPresentationModelImplementation,
				not(instanceOf(PresentationModelMixin.class)));
	}
	
	public static class ByInterface implements ObservableBean {
		@Override
		public void addPropertyChangeListener(
				String propertyName,
				PropertyChangeListener listener) {
		}

		@Override
		public void removePropertyChangeListener(
				String propertyName,
				PropertyChangeListener listener) {
		}
	}
	
	public static class BySubclassing extends AbstractPresentationModel {
	}
}
