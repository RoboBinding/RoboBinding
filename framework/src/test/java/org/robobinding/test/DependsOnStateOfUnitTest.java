package org.robobinding.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.robobinding.annotation.DependsOnStateOf;
import org.robobinding.presentationmodel.HasPresentationModelChangeSupport;
import org.robobinding.presentationmodel.PresentationModelChangeSupport;
import org.robobinding.util.RandomValues;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class DependsOnStateOfUnitTest {
	private SamplePresentationModel presentationModel;

	@Before
	public void setUp() {
		presentationModel = new SamplePresentationModel();
	}

	@Test
	public void whenChangeProperty1_thenProperty2ChangeReceived() {
		PresentationModelPropertyChangeSpy spy = PresentationModelTester.spyPropertyChange(presentationModel, SamplePresentationModel.PROPERTY2);

		presentationModel.changeProperty1();

		assertTrue(spy.isPropertyChanged());
	}

	@Test
	public void whenChangeProperty1_thenProperty2ChangeReceivedByBoth() {
		PresentationModelPropertyChangeSpy spy1 = PresentationModelTester.spyPropertyChange(presentationModel, SamplePresentationModel.PROPERTY2);
		PresentationModelPropertyChangeSpy spy2 = PresentationModelTester.spyPropertyChange(presentationModel, SamplePresentationModel.PROPERTY2);

		presentationModel.changeProperty1();

		assertTrue(spy1.isPropertyChanged());
		assertTrue(spy2.isPropertyChanged());
	}

	@Test
	public void whenChangeProperty1Twice_thenProperty2ChangeReceivedTwice() {
		PresentationModelPropertyChangeSpy spy = PresentationModelTester.spyPropertyChange(presentationModel, SamplePresentationModel.PROPERTY2);

		presentationModel.changeProperty1();
		presentationModel.changeProperty1();

		assertThat(spy.getPropertyChangedCount(), is(2));
	}

	public static class SamplePresentationModel implements HasPresentationModelChangeSupport{
		private static final String PROPERTY1 = "property1";
		private static final String PROPERTY2 = "property2";
		private PresentationModelChangeSupport changeSupport;
		private boolean property1Value;
		
		public SamplePresentationModel() {
			changeSupport = new PresentationModelChangeSupport(this);
		}

		public boolean getProperty1() {
			return property1Value;
		}

		public void changeProperty1() {
			property1Value = RandomValues.trueOrFalse();
			changeSupport.firePropertyChange(PROPERTY1);
		}

		@DependsOnStateOf(PROPERTY1)
		public boolean getProperty2() {
			return !property1Value;
		}
		
		@Override
		public PresentationModelChangeSupport getPresentationModelChangeSupport() {
			return changeSupport;
		}
	}
}
