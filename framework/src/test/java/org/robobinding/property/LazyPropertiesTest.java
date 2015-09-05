package org.robobinding.property;

import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import com.google.common.collect.Sets;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class LazyPropertiesTest {
	private static final String PROPERTY_NAME = "property";

	@Test
	public void givenGetReadOnlyPropertyOnce_whenGetReadOnlyPropertyAgain_thenReturnSameInstance() {
		PropertyWithDependencySupply supply = mock(PropertyWithDependencySupply.class);
		when(supply.createProperty(PROPERTY_NAME)).thenReturn(mock(PropertyValueModel.class));
		LazyProperties lazyProperties = createLazyProperties(supply);

		ValueModel<Object> property = lazyProperties.getReadOnlyProperty(PROPERTY_NAME);

		ValueModel<Object> cachedProperty = lazyProperties.getReadOnlyProperty(PROPERTY_NAME);

		assertThat(cachedProperty, sameInstance(property));
	}
	
	private LazyProperties createLazyProperties(PropertyWithDependencySupply supply) {
		return new LazyProperties(getClass(), Sets.newHashSet(PROPERTY_NAME), Sets.newHashSet(PROPERTY_NAME), supply);
	}

	@Test
	public void givenAccessReadWritePropertyUsingGetReadOnlyPropertyAtFirst_whenAccessUsingGetReadWritePropertyLater_thenReturnSameInstance() {
		PropertyWithDependencySupply supply = mock(PropertyWithDependencySupply.class);
		when(supply.createProperty(PROPERTY_NAME)).thenReturn(mock(PropertyValueModel.class));
		LazyProperties lazyProperties = createLazyProperties(supply);

		ValueModel<Boolean> propertyUsingGetReadOnlyProperty = lazyProperties.getReadOnlyProperty(PROPERTY_NAME);

		ValueModel<Boolean> propertyUsingGetReadWriteProperty = lazyProperties.getReadWriteProperty(PROPERTY_NAME);

		assertThat(propertyUsingGetReadWriteProperty, sameInstance(propertyUsingGetReadOnlyProperty));
	}

	@Test
	public void givenGetDatSetPropertyOnce_whenGetDataSetPropertyAgain_thenReturnSameInstance() {
		PropertyWithDependencySupply supply = mock(PropertyWithDependencySupply.class);
		when(supply.createDataSetProperty(PROPERTY_NAME)).thenReturn(mock(DataSetPropertyValueModel.class));
		LazyProperties lazyProperties = createLazyProperties(supply);

		DataSetValueModel dataSetProperty = lazyProperties.getDataSetProperty(PROPERTY_NAME);

		DataSetValueModel cachedDataSetProperty = lazyProperties.getDataSetProperty(PROPERTY_NAME);

		assertThat(cachedDataSetProperty, sameInstance(dataSetProperty));
	}
}
