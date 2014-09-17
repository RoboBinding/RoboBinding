package org.robobinding.property;

import java.util.List;

import org.robobinding.itempresentationmodel.ItemPresentationModelFactory;
import org.robobinding.itempresentationmodel.TypedCursor;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class PropertyFactory {
	private final ObservableBean observableBean;
	private final ItemPresentationModelFactories itemPresentationModelFactories;

	public PropertyFactory(ObservableBean observableBean, ItemPresentationModelFactories itemPresentationModelFactories) {
		this.observableBean = observableBean;
		this.itemPresentationModelFactories = itemPresentationModelFactories;
	}

	public AbstractProperty createProperty(PropertyAccessor propertyAccessor) {
		return new SimpleProperty(observableBean, propertyAccessor);
	}

	public AbstractDataSetProperty createDataSetProperty(PropertyAccessor propertyAccessor) {

		if (propertyAccessor.hasAnnotation(org.robobinding.annotation.ItemPresentationModel.class)) {
			ItemPresentationModelFactory factory = itemPresentationModelFactories.create(propertyAccessor);
			AbstractDataSetProperty dataSetProperty = null;
			if (List.class.isAssignableFrom(propertyAccessor.getPropertyType())) {
				dataSetProperty = new ListDataSetProperty(observableBean, propertyAccessor, factory);
			} else if (propertyAccessor.getPropertyType().isArray()) {
				dataSetProperty = new ArrayDataSetProperty(observableBean, propertyAccessor, factory);
			} else if (TypedCursor.class.isAssignableFrom(propertyAccessor.getPropertyType())) {
				dataSetProperty = new CursorDataSetProperty(observableBean, propertyAccessor, factory);
			} else {
				throw new RuntimeException("The property '" + propertyAccessor.getShortDescription() + "' has an unsupported dataset type '"
						+ propertyAccessor.getPropertyType() + "'");
			}

			dataSetProperty.addPropertyChangeListener(dataSetProperty);
			return dataSetProperty;

		} else {
			throw new RuntimeException("The property '" + propertyAccessor.getShortDescription()
					+ "' that provides the dataset is missing the @ItemPresentationModel annotation");
		}
	}
}
