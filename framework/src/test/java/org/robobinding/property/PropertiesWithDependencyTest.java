package org.robobinding.property;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.robobinding.property.MockPropertyAccessorBuilder.aPropertyAccessor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.robobinding.annotation.DependsOnStateOf;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@RunWith(MockitoJUnitRunner.class)
public class PropertiesWithDependencyTest {
	private static final String PROPERTY_NAME = "property";
	@Mock
	private DependencyFactory dependencyFactory;
	@Mock
	private PropertyFactory propertyFactory;

	@Test
	public void whenCreatePropertyWithDependency_thenReturnDependencyProperty() {
		PropertyAccessorFactory propertyAccessorFactory = mock(PropertyAccessorFactory.class);
		PropertyAccessor propertyAccessor = aPropertyAccessor().withAnnotation(DependsOnStateOf.class, true).build();
		when(propertyAccessorFactory.create(PROPERTY_NAME)).thenReturn(propertyAccessor);
		PropertiesWithDependency properties = new PropertiesWithDependency(propertyAccessorFactory, dependencyFactory, propertyFactory);

		PropertyValueModel property = properties.createProperty(PROPERTY_NAME);

		assertThat(property, instanceOf(DependencyProperty.class));
	}

	@Test
	public void whenCreateDataSetPropertyWithDependency_thenReturnDataSetDependencyProperty() {
		PropertyAccessorFactory propertyAccessorFactory = mock(PropertyAccessorFactory.class);
		PropertyAccessor propertyAccessor = aPropertyAccessor().withAnnotation(DependsOnStateOf.class, true).build();
		when(propertyAccessorFactory.create(PROPERTY_NAME)).thenReturn(propertyAccessor);

		when(dependencyFactory.create(propertyAccessor)).thenReturn(mock(Dependency.class));
		PropertiesWithDependency properties = new PropertiesWithDependency(propertyAccessorFactory, dependencyFactory, propertyFactory);

		DataSetPropertyValueModel dataSetProperty = properties.createDataSetProperty(PROPERTY_NAME);

		assertThat(dataSetProperty, instanceOf(DataSetDependencyProperty.class));
	}

	@Test
	public void whenCreateDataSetPropertyWithDependency_thenListenerAddedToDependency() {
		PropertyAccessorFactory propertyAccessorFactory = mock(PropertyAccessorFactory.class);
		PropertyAccessor propertyAccessor = aPropertyAccessor().withAnnotation(DependsOnStateOf.class, true).build();
		when(propertyAccessorFactory.create(PROPERTY_NAME)).thenReturn(propertyAccessor);

		Dependency dependency = mock(Dependency.class);
		when(dependencyFactory.create(propertyAccessor)).thenReturn(dependency);
		PropertiesWithDependency properties = new PropertiesWithDependency(propertyAccessorFactory, dependencyFactory, propertyFactory);

		properties.createDataSetProperty(PROPERTY_NAME);

		verify(dependency).addListenerToDependentProperties(any(PropertyChangeListener.class));
	}
}
