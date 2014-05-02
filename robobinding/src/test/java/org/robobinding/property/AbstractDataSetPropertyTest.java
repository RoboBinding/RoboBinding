package org.robobinding.property;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.robobinding.presentationmodel.ItemPresentationModel;
import org.robobinding.viewattribute.RandomValues;

import com.google.common.collect.Lists;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class AbstractDataSetPropertyTest {
    @Test
    public void whenCreateDefaultConstructorDataSetProperty_thenDefaultConstructorFactoryCreated() {
	DataSetProperty dataSetProperty = new DataSetProperty(Bean.DEFAULT_CONSTRUCTOR_DATA_SET_PROPERTY);

	assertThat(dataSetProperty.factory, instanceOf(DefaultConstructorImpl.class));
    }

    @Test
    public void whenCreateFactoryMethodDataSetProperty_thenFactoryMethodFactoryCreated() {
	DataSetProperty dataSetProperty = new DataSetProperty(Bean.FACTORY_METHOD_DATA_SET_PROPERTY);

	assertThat(dataSetProperty.factory, instanceOf(FactoryMethodImpl.class));
    }

    @Test
    public void givenGetDataSet_whenGetDataSetAgain_thenReturnSameInstance() {
	DataSetProperty dataSetProperty = new DataSetProperty(Bean.RANDOM_DATA_SET_PROPERTY);

	Object dataSetFirstTime = dataSetProperty.getDataSet();

	Object dataSetSecondTime = dataSetProperty.getDataSet();

	assertSame(dataSetSecondTime, dataSetFirstTime);
    }

    @Test
    public void whenUpdateDataSetOnBean_thenDataSetPropertyReflectsChanges() {
	DataSetProperty dataSetProperty = new DataSetProperty(Bean.DATA_SET_PROPERTY);

	List<Object> newDataSet = Bean.randomObjectList();
	dataSetProperty.getBean().updateDataSet(newDataSet);

	assertSame(newDataSet, dataSetProperty.getDataSet());
    }

    static class DataSetProperty extends AbstractDataSetProperty<Object> {
	public DataSetProperty(String propertyName) {
	    super(new ObservableBean(new Bean()), PropertyAccessorUtils.createPropertyAccessor(Bean.class, propertyName));
	}

	@Override
	public int size() {
	    return 0;
	}

	@Override
	public Object getItem(int position) {
	    return null;
	}

	public Bean getBean() {
	    return (Bean) super.getBean();
	}
    }
    public static class Bean implements ObservableProperties {
	private static final String DEFAULT_CONSTRUCTOR_DATA_SET_PROPERTY = "defaultConstructorDataSetProperty";
	private static final String FACTORY_METHOD_DATA_SET_PROPERTY = "factoryMethodDataSetProperty";
	private static final String RANDOM_DATA_SET_PROPERTY = "randomDataSetProperty";
	private static final String DATA_SET_PROPERTY = "dataSetProperty";

	private PresentationModelPropertyChangeSupport propertyChangeSupport;

	public Bean() {
	    propertyChangeSupport = new PresentationModelPropertyChangeSupport(this);
	}

	@ItemPresentationModel(value = ItemPresentationModelImpl.class)
	public List<Object> getDefaultConstructorDataSetProperty() {
	    return null;
	}

	@ItemPresentationModel(value = ItemPresentationModelImpl.class, factoryMethod = "createFactoryMethodDataSetItem")
	public List<Object> getFactoryMethodDataSetProperty() {
	    return null;
	}

	public ItemPresentationModelImpl createFactoryMethodDataSetItem() {
	    return null;
	}

	@ItemPresentationModel(value = ItemPresentationModelImpl.class)
	public List<Object> getRandomDataSetProperty() {
	    return randomObjectList();
	}

	public static List<Object> randomObjectList() {
	    List<Object> randomObjectList = Lists.newArrayList();
	    int size = RandomValues.nextInt(5);
	    for (int i = 0; i < size; i++) {
		randomObjectList.add(new Object());
	    }
	    return randomObjectList;
	}

	private List<Object> dataSet = Lists.newArrayList();

	@ItemPresentationModel(value = ItemPresentationModelImpl.class)
	public List<Object> getDataSetProperty() {
	    return dataSet;
	}

	public void updateDataSet(List<Object> newDataSet) {
	    this.dataSet = newDataSet;
	    propertyChangeSupport.firePropertyChange(DATA_SET_PROPERTY);
	}

	@Override
	public void addPropertyChangeListener(String propertyName, PresentationModelPropertyChangeListener listener) {
	    propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
	}

	@Override
	public void removePropertyChangeListener(String propertyName, PresentationModelPropertyChangeListener listener) {
	    propertyChangeSupport.removePropertyChangeListener(propertyName, listener);
	}
    }
}
