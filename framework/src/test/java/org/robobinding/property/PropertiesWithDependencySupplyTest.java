package org.robobinding.property;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@RunWith(MockitoJUnitRunner.class)
public class PropertiesWithDependencySupplyTest {
	private static final String PROPERTY_NAME = "property";
	@Mock
	private Dependencies dependencies;
	@Mock
	private PropertySupply propertySupply;
	
	private PropertyWithDependencySupply withDependencySupply;
	
	@Before
	public void setUp() {
		withDependencySupply = new PropertyWithDependencySupply(getClass(), propertySupply, dependencies);
	}

	@Test
	public void whenCreatePropertyWithDependency_thenReturnDependencyProperty() {
		SimpleProperty property = mock(SimpleProperty.class);
		when(propertySupply.tryToCreateProperty(PROPERTY_NAME)).thenReturn(property);
		when(dependencies.hasDependency(PROPERTY_NAME)).thenReturn(true);

		PropertyValueModel propertyCreated = withDependencySupply.createProperty(PROPERTY_NAME);

		assertThat(propertyCreated, instanceOf(DependencyProperty.class));
	}

	@Test
	public void whenCreateDataSetPropertyWithDependency_thenReturnDataSetDependencyProperty() {
		DataSetProperty property = mock(DataSetProperty.class);
		when(propertySupply.tryToCreateDataSetProperty(PROPERTY_NAME)).thenReturn(property);
		when(dependencies.hasDependency(PROPERTY_NAME)).thenReturn(true);
		Dependency dependency = mock(Dependency.class);
		when(dependencies.createDependency(PROPERTY_NAME)).thenReturn(dependency);

		DataSetPropertyValueModel propertyCreated = withDependencySupply.createDataSetProperty(PROPERTY_NAME);

		assertThat(propertyCreated, instanceOf(DataSetDependencyProperty.class));
	}

	@Test
	public void whenCreateDataSetPropertyWithDependency_thenListenerAddedToDependency() {
		DataSetProperty property = mock(DataSetProperty.class);
		when(propertySupply.tryToCreateDataSetProperty(PROPERTY_NAME)).thenReturn(property);
		when(dependencies.hasDependency(PROPERTY_NAME)).thenReturn(true);
		Dependency dependency = mock(Dependency.class);
		when(dependencies.createDependency(PROPERTY_NAME)).thenReturn(dependency);

		withDependencySupply.createDataSetProperty(PROPERTY_NAME);

		verify(dependency).addListenerToDependentProperties(any(PropertyChangeListener.class));
	}
}
