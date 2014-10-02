package org.robobinding.property;

import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Constructor;

import org.junit.Test;
import org.robobinding.itempresentationmodel.ItemPresentationModel;
import org.robobinding.util.ConstructorUtils;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class DefaultConstructorImplTest {

	@Test
	public void givenItemPresentationModelFactory_whenNewItemPresentationModel_thenReturnNewInstance() {
		DefaultConstructorImpl factory = new DefaultConstructorImpl(getDefaultConstructor());

		ItemPresentationModel<Object> itemPresentationModel = factory.newRefreshableItemPresentationModel();

		assertNotNull(itemPresentationModel);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Constructor<ItemPresentationModel<Object>> getDefaultConstructor() {
		return (Constructor) ConstructorUtils.getAccessibleConstructor(ItemPresentationModelWithDefaultConstructor.class, new Class[0]);
	}

}
