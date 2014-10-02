package org.robobinding.property;

import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Method;

import org.junit.Test;
import org.robobinding.itempresentationmodel.ItemPresentationModel;
import org.robobinding.util.MethodUtils;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class FactoryMethodImplTest {
	@Test
	public void givenItemPresentationModelFactory_whenNewPresentationModel_returnNewInstance() {
		Bean bean = new Bean();
		FactoryMethodImpl factoryMethod = new FactoryMethodImpl(bean, getFactoryMethod());
		ItemPresentationModel<Object> itemPresentationModel = factoryMethod.newRefreshableItemPresentationModel();

		assertNotNull(itemPresentationModel);
	}

	private Method getFactoryMethod() {
		return MethodUtils.getAccessibleMethod(Bean.class, Bean.FACTORY_METHOD, new Class[0]);
	}

	public static class Bean {
		public static final String FACTORY_METHOD = "factoryMethod";

		public ItemPresentationModelImpl factoryMethod() {
			return new ItemPresentationModelImpl();
		}
	}
}
