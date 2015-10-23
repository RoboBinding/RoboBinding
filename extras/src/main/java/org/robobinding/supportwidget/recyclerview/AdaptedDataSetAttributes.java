package org.robobinding.supportwidget.recyclerview;

import static org.robobinding.attribute.ChildAttributeResolvers.predefinedMappingsAttributeResolver;
import static org.robobinding.attribute.ChildAttributeResolvers.propertyAttributeResolver;
import static org.robobinding.attribute.ChildAttributeResolvers.valueModelAttributeResolver;

import org.robobinding.BindingContext;
import org.robobinding.attribute.ChildAttributeResolverMappings;
import org.robobinding.attribute.ResolvedGroupAttributes;
import org.robobinding.viewattribute.grouped.ChildViewAttributesBuilder;
import org.robobinding.viewattribute.grouped.GroupedViewAttribute;
import org.robobinding.widget.adapterview.ItemMappingAttribute;
import org.robobinding.widget.adapterview.ItemMappingUpdater;
import org.robobinding.widget.adapterview.RowLayoutAttributeAdapter;
import org.robobinding.widget.adapterview.RowLayoutAttributeFactory;
import org.robobinding.widget.adapterview.SourceAttribute;

import android.support.v7.widget.RecyclerView;

/**
 * 
 * @since 1.0
 * @author Cheng Wei
 */
public class AdaptedDataSetAttributes implements GroupedViewAttribute<RecyclerView> {
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
	public void setupChildViewAttributes(RecyclerView view, 
			ChildViewAttributesBuilder<RecyclerView> childViewAttributesBuilder, BindingContext bindingContext) {
		dataSetAdapterBuilder = new DataSetAdapterBuilder(bindingContext);

		childViewAttributesBuilder.add(SOURCE, new SourceAttribute(dataSetAdapterBuilder));
		
		RowLayoutAttributeFactory factory = new RowLayoutAttributeFactory(view, new ItemLayoutUpdaterProvider(view, dataSetAdapterBuilder));
		childViewAttributesBuilder.add(ITEM_LAYOUT, new RowLayoutAttributeAdapter(factory));
		
		if (childViewAttributesBuilder.hasAttribute(ITEM_MAPPING)) {
			childViewAttributesBuilder.add(ITEM_MAPPING, new ItemMappingAttribute(new ItemMappingUpdater(dataSetAdapterBuilder)));
		}
	}

	@Override
	public void postBind(RecyclerView view, BindingContext bindingContext) {
		DataSetAdapter dataSetAdapter = dataSetAdapterBuilder.build();

		view.setAdapter(dataSetAdapter);
	}
}
