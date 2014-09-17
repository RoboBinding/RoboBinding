package org.robobinding.property;

import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.robobinding.annotation.ItemPresentationModel;
import org.robobinding.itempresentationmodel.ItemPresentationModelFactory;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ItemPresentationModelFactoriesTest {
	private Bean bean;
	private ItemPresentationModelFactories itemPresentationModelFactories;

	@Before
	public void setUp() {
		bean = new Bean();
		itemPresentationModelFactories = new ItemPresentationModelFactories(bean);
	}

	@Test
	public void whenCreateUsingFactoryMethodWithValidReturnType_thenSuccessful() {
		PropertyAccessor propertyAccessor = createPropertyAccessor(bean, Bean.FACTORY_METHOD_WITH_VALID_RETURN_TYPE_PROPERTY);

		ItemPresentationModelFactory itemPresentationModelFactory = itemPresentationModelFactories.create(propertyAccessor);
		assertNotNull(itemPresentationModelFactory);
	}

	private PropertyAccessor createPropertyAccessor(Bean bean, String propertyName) {
		PropertyAccessor propertyAccessor = new PropertyAccessor(bean, getPropertyDescriptor(propertyName));
		return propertyAccessor;
	}

	private PropertyDescriptor getPropertyDescriptor(String propertyName) {
		Map<String, PropertyDescriptor> propertyDescriptorMap = PropertyUtils.getPropertyDescriptorMap(Bean.class);
		return propertyDescriptorMap.get(propertyName);
	}

	@Test(expected = RuntimeException.class)
	public void whenCreateUsingFactoryMethodWithInvalidReturnType_thenThrowException() {
		PropertyAccessor propertyAccessor = createPropertyAccessor(bean, Bean.FACTORY_METHOD_WITH_INVALID_RETURN_TYPE_PROPERTY);

		itemPresentationModelFactories.create(propertyAccessor);
	}

	@Test(expected = RuntimeException.class)
	public void whenCreateUsingNonExistingFactoryMethod_thenThrowException() {
		PropertyAccessor propertyAccessor = createPropertyAccessor(bean, Bean.PROPERTY_WITH_NONEXISTING_FACTORY_METHOD);

		itemPresentationModelFactories.create(propertyAccessor);
	}

	@Test
	public void whenCreateUsingDefaultConstructor_thenSuccessful() {
		PropertyAccessor propertyAccessor = createPropertyAccessor(bean, Bean.DEFAULT_CONSTRUCTOR_PROPERTY);

		itemPresentationModelFactories.create(propertyAccessor);
	}

	@Test(expected = RuntimeException.class)
	public void whenCreateFactoryUsingItemPresentationModelWithoutDefaultConstructor_thenThrowException() {
		PropertyAccessor propertyAccessor = createPropertyAccessor(bean, Bean.WITHOUT_DEFAULT_CONSTRUCTOR_PROPERTY);

		itemPresentationModelFactories.create(propertyAccessor);
	}

	public static class Bean {
		public static final String FACTORY_METHOD_WITH_VALID_RETURN_TYPE_PROPERTY = "factoryMethodWithValidReturnTypeProperty";
		public static final String FACTORY_METHOD_WITH_INVALID_RETURN_TYPE_PROPERTY = "factoryMethodWithInvalidReturnTypeProperty";
		public static final String PROPERTY_WITH_NONEXISTING_FACTORY_METHOD = "propertyWithNonExistingFactoryMethod";
		public static final String DEFAULT_CONSTRUCTOR_PROPERTY = "defaultConstructorProperty";
		public static final String WITHOUT_DEFAULT_CONSTRUCTOR_PROPERTY = "withoutDefaultConstructorProperty";

		@ItemPresentationModel(value = ItemPresentationModelImpl.class, factoryMethod = "factoryMethodWithValidReturnType")
		public List<Object> getFactoryMethodWithValidReturnTypeProperty() {
			return null;
		}

		public ItemPresentationModelImpl factoryMethodWithValidReturnType() {
			return new ItemPresentationModelImpl();
		}

		@ItemPresentationModel(value = ItemPresentationModelImpl.class, factoryMethod = "factoryMethodWithInvalidReturnType")
		public List<Object> getFactoryMethodWithInvalidReturnTypeProperty() {
			return null;
		}

		public Object factoryMethodWithInvalidReturnType() {
			return new Object();
		}

		@ItemPresentationModel(value = ItemPresentationModelImpl.class, factoryMethod = "nonExistingfactoryMethod")
		public List<Object> getPropertyWithNonExistingFactoryMethod() {
			return null;
		}

		@ItemPresentationModel(value = ItemPresentationModelWithDefaultConstructor.class)
		public List<Object> getDefaultConstructorProperty() {
			return null;
		}

		@ItemPresentationModel(value = ItemPresentationModelWithoutDefaultConstructor.class)
		public List<Object> getWithoutDefaultConstructorProperty() {
			return null;
		}
	}

	public static class ItemPresentationModelWithoutDefaultConstructor implements org.robobinding.itempresentationmodel.ItemPresentationModel<Object> {
		public ItemPresentationModelWithoutDefaultConstructor(boolean parameter) {
		}

		@Override
		public void updateData(int index, Object bean) {
		}
	}
}
