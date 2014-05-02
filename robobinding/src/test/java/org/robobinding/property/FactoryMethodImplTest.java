package org.robobinding.property;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.robobinding.itempresentationmodel.ItemPresentationModel;
import org.robobinding.itempresentationmodel.ItemPresentationModelFactory;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class FactoryMethodImplTest {
    private ObjectUnderTest objectUnderTest;

    @Before
    public void setUp() {
	objectUnderTest = new ObjectUnderTest();
    }

    @Test
    public void whenCreateUsingFactoryMethodWithValidReturnType_thenSuccessful() {
	new FactoryMethodImpl<Object>(objectUnderTest, ItemPresentationModelImpl.class, ObjectUnderTest.FACTORY_METHOD_WITH_VALID_RETURN_TYPE);
    }

    @Test
    public void givenItemPresentationModelFactory_whenNewPresentationModel_returnNewInstance() {
	ItemPresentationModelFactory<Object> factory = new FactoryMethodImpl<Object>(objectUnderTest, ItemPresentationModelImpl.class,
		ObjectUnderTest.FACTORY_METHOD_WITH_VALID_RETURN_TYPE);

	ItemPresentationModel<Object> itemPresentationModel = factory.newItemPresentationModel();

	Assert.assertNotNull(itemPresentationModel);
    }

    @Test(expected = RuntimeException.class)
    public void whenCreateUsingFactoryMethodWithInvalidReturnType_thenThrowException() {
	new FactoryMethodImpl<Object>(objectUnderTest, ItemPresentationModelImpl.class, ObjectUnderTest.FACTORY_METHOD_WITH_INVALID_RETURN_TYPE);
    }

    @Test(expected = RuntimeException.class)
    public void whenCreateUsingNonExistingFactoryMethod_thenThrowException() {
	new FactoryMethodImpl<Object>(objectUnderTest, ItemPresentationModelImpl.class, "nonExistingFactoryMethod");
    }

    public static class ObjectUnderTest {
	public static final String FACTORY_METHOD_WITH_VALID_RETURN_TYPE = "factoryMethodWithValidReturnType";
	public static final String FACTORY_METHOD_WITH_INVALID_RETURN_TYPE = "factoryMethodWithInvalidReturnType";

	public ItemPresentationModelImpl factoryMethodWithValidReturnType() {
	    return new ItemPresentationModelImpl();
	}

	public Object factoryMethodWithInvalidReturnType() {
	    return new Object();
	}
    }
}
