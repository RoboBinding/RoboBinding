package org.robobinding.property;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.robobinding.property.MockPropertyAccessorBuilder.aPropertyAccessor;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.robobinding.annotation.ItemPresentationModel;
import org.robobinding.itempresentationmodel.TypedCursor;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class PropertyFactoryTest {
    private PropertyFactory propertyFactory;
    
    @Before
    public void setUp() {
	propertyFactory = new PropertyFactory(mock(ObservableBean.class), mock(ItemPresentationModelFactories.class));
    }

    @Test
    public void whenCreateListDataSetProperty_thenReturnCorrectInstance() {
	AbstractDataSetProperty dataSetProperty = propertyFactory.createDataSetProperty(
		aPropertyAccessor().withAnnotation(ItemPresentationModel.class, true).withPropertyType(List.class).build());

	assertThat(dataSetProperty, instanceOf(ListDataSetProperty.class));
    }

    @Test
    public void whenCreateArrayDataSetProperty_thenReturnCorrectInstance() {
	AbstractDataSetProperty dataSetProperty = propertyFactory.createDataSetProperty(
		aPropertyAccessor().withAnnotation(ItemPresentationModel.class, true).withPropertyType(Object[].class).build());

	assertThat(dataSetProperty, instanceOf(ArrayDataSetProperty.class));
    }

    @Test
    public void whenCreateCursorDataSetProperty_thenReturnInstance() {
	AbstractDataSetProperty dataSetProperty = propertyFactory.createDataSetProperty(
		aPropertyAccessor().withAnnotation(ItemPresentationModel.class, true).withPropertyType(TypedCursor.class).build());

	assertThat(dataSetProperty, instanceOf(CursorDataSetProperty.class));
     }

   @Test(expected = RuntimeException.class)
    public void whenCreateUnsupportedDataSetProperty_thenThrowException() {
	Class<Object> unsupportedDataSetType = Object.class;
	
	propertyFactory.createDataSetProperty(
		aPropertyAccessor().withAnnotation(ItemPresentationModel.class, true).withPropertyType(unsupportedDataSetType).build());
    }

    @Test(expected = RuntimeException.class)
    public void whenCreateDataSetPropertyWithoutItemPresentationModelAnnotation_thenThrowException() {
	propertyFactory.createDataSetProperty(
		aPropertyAccessor().withAnnotation(ItemPresentationModel.class, false).build());
    }
}
