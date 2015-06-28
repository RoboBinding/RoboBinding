package org.robobinding.aspects;

import org.junit.Before;
import org.junit.Test;
import org.robobinding.annotation.PresentationModel;
import org.robobinding.presentationmodel.HasPresentationModelChangeSupport;
import org.robobinding.presentationmodel.PresentationModelChangeSupport;

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
		PresentationModelChangeSupport changeSupport = ((HasPresentationModelChangeSupport)presentationModel).getPresentationModelChangeSupport();
		changeSupport.addPropertyChangeListener(propertyName, propertyChangeListenerTester);
	}

	@Test
	public void whenSetCustomProperty_thenReceivedNoNotification() {
		AutoDefinedChangeSupport presentationModel = new AutoDefinedChangeSupport();
		observePropertyChange(presentationModel, AutoDefinedChangeSupport.CUSTOM_PROPERTY);

		presentationModel.setCustomProperty(true);

		propertyChangeListenerTester.assertTimesOfPropertyChanged(0);
	}
	
	@Test
	public void givenUserDefinedPresentationModelChangeSupport_whenSetProperty_thenReceivedChangeNotification() {
		UserDefinedChangeSupport presentationModel = new UserDefinedChangeSupport();
		observePropertyChange(presentationModel, UserDefinedChangeSupport.PROPERTY);

		presentationModel.setProperty(true);

		propertyChangeListenerTester.assertPropertyChangedOnce();
	}

	@Test
	public void givenUserDefinedPresentationModelChangeSupport_whenRefreshPresentationModel_thenReceivedChangeNotification() {
		UserDefinedChangeSupport presentationModel = new UserDefinedChangeSupport();
		observePropertyChange(presentationModel, UserDefinedChangeSupport.PROPERTY);

		presentationModel.refresh();

		propertyChangeListenerTester.assertPropertyChangedOnce();
	}

	@Test
	public void givenNoPresentationModelAnnotation_whenSetProperty_thenReceivedNoNotification() {
		NoPresentationModelAnnotation presentationModel = new NoPresentationModelAnnotation();
		observePropertyChange(presentationModel, UserDefinedChangeSupport.PROPERTY);

		presentationModel.setProperty(true);

		propertyChangeListenerTester.assertTimesOfPropertyChanged(0);
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
	public static class UserDefinedChangeSupport implements HasPresentationModelChangeSupport {
		public static final String PROPERTY = "property";
		private final PresentationModelChangeSupport changeSupport;

		public UserDefinedChangeSupport() {
			changeSupport = new PresentationModelChangeSupport(this);
		}

		public void refresh() {
			changeSupport.refreshPresentationModel();
		}

		public void setProperty(boolean value) {
		}
		
		public PresentationModelChangeSupport getPresentationModelChangeSupport() {
			return changeSupport;
		}
	}
	
	public static class NoPresentationModelAnnotation implements HasPresentationModelChangeSupport {
		public static final String PROPERTY = "property";
		private final PresentationModelChangeSupport changeSupport;

		public NoPresentationModelAnnotation() {
			changeSupport = new PresentationModelChangeSupport(this);
		}

		public void setProperty(boolean value) {
		}
		
		public PresentationModelChangeSupport getPresentationModelChangeSupport() {
			return changeSupport;
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
