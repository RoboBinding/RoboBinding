package org.robobinding.aspects;

import org.junit.Before;
import org.junit.Test;
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

	@Before
	public void setUp() {
		propertyChangeListenerTester = new PropertyChangeListenerTester();
	}

	@Test
	public void givenObservePropertyChangeOnItemPresentationModel_whenSetData_thenListenerGetNotified() {
		AutoCodeGeneration itemPresentationModel = new AutoCodeGeneration();
		observePropertyChange(itemPresentationModel);

		updateData(itemPresentationModel);

		propertyChangeListenerTester.assertPropertyChangedOnce();
	}

	private void observePropertyChange(ObservableBean itemPresentationModel) {
		itemPresentationModel.addPropertyChangeListener(
				AutoCodeGeneration.PROPERTY,
				propertyChangeListenerTester);
	}

	private void updateData(ItemPresentationModel<Object> itemPresentationModel) {
		itemPresentationModel.updateData(0, new Object());
	}

	public static class AutoCodeGeneration implements ItemPresentationModel<Object> {
		Object bean;
		public static final String PROPERTY = "property";

		public void updateData(int index, Object bean) {
			this.bean = bean;
		}

		public boolean getProperty() {
			return true;
		}

	}
}
