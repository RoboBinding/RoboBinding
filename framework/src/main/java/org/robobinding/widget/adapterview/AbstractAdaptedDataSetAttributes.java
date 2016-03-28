package org.robobinding.widget.adapterview;

import static org.robobinding.attribute.ChildAttributeResolvers.predefinedMappingsAttributeResolver;
import static org.robobinding.attribute.ChildAttributeResolvers.propertyAttributeResolver;
import static org.robobinding.attribute.ChildAttributeResolvers.valueModelAttributeResolver;

import org.robobinding.BindingContext;
import org.robobinding.attribute.ChildAttributeResolverMappings;
import org.robobinding.attribute.ResolvedGroupAttributes;
import org.robobinding.viewattribute.grouped.ChildViewAttributesBuilder;
import org.robobinding.viewattribute.grouped.GroupedViewAttribute;

import android.widget.AdapterView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public abstract class AbstractAdaptedDataSetAttributes<T extends AdapterView<?>> implements GroupedViewAttribute<T> {
	public static final String SOURCE = "source";
	public static final String ITEM_LAYOUT = "itemLayout";
	public static final String ITEM_MAPPING = "itemMapping";
	protected DataSetAdapterBuilder dataSetAdapterBuilder;

	@Override
	public String[] getCompulsoryAttributes() {
		return new String[] { SOURCE, ITEM_LAYOUT };
	}

	@Override
	public void mapChildAttributeResolvers(ChildAttributeResolverMappings resolverMappings) {
		resolverMappings.map(valueModelAttributeResolver(), SOURCE);
		resolverMappings.map(propertyAttributeResolver(), ITEM_LAYOUT);
		resolverMappings.map(predefinedMappingsAttributeResolver(), ITEM_MAPPING);
	}

	@Override
	public void validateResolvedChildAttributes(ResolvedGroupAttributes groupAttributes) {
	}

	@Override
	public void setupChildViewAttributes(T view, ChildViewAttributesBuilder<T> childViewAttributesBuilder) {
		dataSetAdapterBuilder = new DataSetAdapterBuilder();

		childViewAttributesBuilder.add(SOURCE, new SourceAttribute(dataSetAdapterBuilder));
		
		RowLayoutAttributeFactory factory = new RowLayoutAttributeFactory(view, 
				new ItemLayoutUpdaterProvider(view, dataSetAdapterBuilder));
		childViewAttributesBuilder.add(ITEM_LAYOUT, new RowLayoutAttributeAdapter(factory));

		if (childViewAttributesBuilder.hasAttribute(ITEM_MAPPING))
			childViewAttributesBuilder.add(ITEM_MAPPING, new ItemMappingAttribute(new ItemMappingUpdater(dataSetAdapterBuilder)));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void postBind(T view, BindingContext bindingContext) {
		DataSetAdapter dataSetAdapter = dataSetAdapterBuilder.build();

		((AdapterView) view).setAdapter(dataSetAdapter);
	}
}
