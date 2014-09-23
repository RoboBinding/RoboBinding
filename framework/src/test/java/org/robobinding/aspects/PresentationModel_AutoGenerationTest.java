package org.robobinding.aspects;

import org.junit.Before;
import org.junit.Test;
import org.robobinding.presentationmodel.PresentationModelChangeSupport;
import org.robobinding.property.ObservableBean;

/**
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 * 
 */
public class PresentationModel_AutoGenerationTest {
	private PropertyChangeListenerTester propertyChangeListenerTester;

	@Before
	public void setUp() {
		propertyChangeListenerTester = new PropertyChangeListenerTester();
	}

	@Test
	public void whenSetProperty_thenReceivedChangeNotification() {
		AutoDefinedChangeSupport presentationModel = new AutoDefinedChangeSupport();
		observePropertyChange(presentationModel, AutoDefinedChangeSupport.PROPERTY);

		presentationModel.setProperty(true);

		propertyChangeListenerTester.assertPropertyChangedOnce();
	}

	private void observePropertyChange(Object presentationModel, String propertyName) {
		((ObservableBean)presentationModel).addPropertyChangeListener(propertyName, propertyChangeListenerTester);
	}

	@Test
	public void whenSetCustomProperty_thenReceivedNoNotification() {
		AutoDefinedChangeSupport presentationModel = new AutoDefinedChangeSupport();
		observePropertyChange(presentationModel, AutoDefinedChangeSupport.CUSTOM_PROPERTY);

		presentationModel.setCustomProperty(true);

		propertyChangeListenerTester.assertTimesOfPropertyChanged(0);
	}

	@Test
	public void givenUserDefinedPresentationModelChangeSupport_whenRefreshPresentationModel_thenReceivedChangeNotification() {
		UserDefinedChangeSupport presentationModel = new UserDefinedChangeSupport();
		observePropertyChange(presentationModel, UserDefinedChangeSupport.PROPERTY);

		presentationModel.refresh();

		propertyChangeListenerTester.assertPropertyChangedOnce();
	}

	@Test(expected = RuntimeException.class)
	public void whenUserDefinePresentationModelChangeSupportTwice_thenThrowsException() {
		new DoubleUserDefinedChangeSupport();
	}
	
	@Test
	public void whenInvokeASetterInContructor_thenNoExceptionIsThrown() {
		new SetterInvocationInConstructor();
	}

	@PresentationModel
	public static class AutoDefinedChangeSupport {
		public static final String PROPERTY = "property";
		public static final String CUSTOM_PROPERTY = "customProperty";

		public void setProperty(boolean b) {
		}

		@CustomSetter
		public void setCustomProperty(boolean b) {
		}
	}

	@PresentationModel
	public static class UserDefinedChangeSupport {
		public static final String PROPERTY = "property";
		private final PresentationModelChangeSupport presentationModelChangeSupport;

		public UserDefinedChangeSupport() {
			presentationModelChangeSupport = new PresentationModelChangeSupport(this);
		}

		public void refresh() {
			presentationModelChangeSupport.refreshPresentationModel();
		}

		public void setProperty(boolean value) {

		}
	}

	@PresentationModel
	public static class DoubleUserDefinedChangeSupport {
		PresentationModelChangeSupport presentationModelChangeSupport;

		public DoubleUserDefinedChangeSupport() {
			presentationModelChangeSupport = new PresentationModelChangeSupport(this);
			presentationModelChangeSupport = new PresentationModelChangeSupport(this);
		}
	}
	
	@PresentationModel
	public static class SetterInvocationInConstructor {
		public SetterInvocationInConstructor() {
			setProperty(true);
		}

		public void setProperty(boolean value) {

		}
	}
}
