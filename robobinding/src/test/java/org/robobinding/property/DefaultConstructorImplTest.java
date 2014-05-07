package org.robobinding.property;

import org.junit.Assert;
import org.junit.Test;
import org.robobinding.itempresentationmodel.ItemPresentationModel;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class DefaultConstructorImplTest {
    @Test
    public void whenCreateFactoryUsingItemPresentationModelWithDefaultConstructor_thenSuccessful() {
	new DefaultConstructorImpl<Object>(ItemPresentationModelWithDefaultConstructor.class);
    }

    @Test
    public void givenItemPresentationModelFactory_whenNewItemPresentationModel_thenReturnNewInstance() {
	DefaultConstructorImpl<Object> factory = new DefaultConstructorImpl<Object>(ItemPresentationModelWithDefaultConstructor.class);

	ItemPresentationModel<Object> itemPresentationModel = factory.newItemPresentationModel();

	Assert.assertNotNull(itemPresentationModel);
    }

    @Test(expected = NullPointerException.class)
    public void whenCreateFactoryUsingItemPresentationModelWithoutDefaultConstructor_thenThrowException() {
	new DefaultConstructorImpl<Object>(ItemPresentationModelWithoutDefaultConstructor.class);
    }

    public static class ItemPresentationModelWithDefaultConstructor implements ItemPresentationModel<Object> {
	@Override
	public void updateData(int index, Object bean) {
	}
    }

    public static class ItemPresentationModelWithoutDefaultConstructor implements ItemPresentationModel<Object> {
	public ItemPresentationModelWithoutDefaultConstructor(boolean parameter) {
	}

	@Override
	public void updateData(int index, Object bean) {
	}
    }
}
