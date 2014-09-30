package org.robobinding.aspects;

import org.junit.Before;
import org.junit.Test;
import org.robobinding.itempresentationmodel.ItemContext;
import org.robobinding.itempresentationmodel.ItemPresentationModel;
import org.robobinding.property.ObservableBean;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ItemPresentationModel_AutoGenerationTest {
	private PropertyChangeListenerTester propertyChangeListenerTester;
	private AutoCodeGeneration itemPresentationModel;

	@Before
	public void setUp() {
		propertyChangeListenerTester = new PropertyChangeListenerTester();
		itemPresentationModel = new AutoCodeGeneration();
		observePropertyChange(itemPresentationModel);
	}

	@Test
	public void whenUpdateDataWithPreInitializeViews_thenListenerGetNotified() {
		updateData(true);

		propertyChangeListenerTester.assertPropertyChangedOnce();
	}
	
	@Test
	public void whenUpdateDataWithoutPreInitializeViews_thenListenerGetNoNotifications() {
		updateData(false);

		propertyChangeListenerTester.assertTimesOfPropertyChanged(0);
	}

	private void observePropertyChange(Object itemPresentationModel) {
		((ObservableBean)itemPresentationModel).addPropertyChangeListener(AutoCodeGeneration.PROPERTY, propertyChangeListenerTester);
	}

	private void updateData(boolean preInitializeViews) {
		itemPresentationModel.updateData(new Object(), new ItemContext(null, 0, preInitializeViews));
	}

	public static class AutoCodeGeneration implements ItemPresentationModel<Object> {
		Object bean;
		public static final String PROPERTY = "property";

		public void updateData(Object bean, ItemContext itemContext) {
			this.bean = bean;
		}

		public boolean getProperty() {
			return true;
		}

	}
}
