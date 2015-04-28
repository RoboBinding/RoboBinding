package org.robobinding.widget.expandablelistview;

import android.widget.ExpandableListView;
import com.google.common.collect.ObjectArrays;
import org.robobinding.BindingContext;
import org.robobinding.attribute.ChildAttributeResolverMappings;
import org.robobinding.attribute.ResolvedGroupAttributes;
import org.robobinding.viewattribute.grouped.ChildViewAttributesBuilder;
import org.robobinding.widget.adapterview.*;

import static org.robobinding.attribute.ChildAttributeResolvers.*;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Jihun Lee
 */
public class AdaptedExpandableListViewDataSetAttributes extends AbstractAdaptedDataSetAttributes<ExpandableListView> {
    public static final String GROUP_SOURCE = "groupSource";
    public static final String CHILD_SOURCE = "childSource";
    public static final String GROUP_LAYOUT = "groupLayout";
    public static final String CHILD_LAYOUT = "childLayout";
    public static final String GROUP_MAPPING = "groupMapping";
    public static final String CHILD_MAPPING = "childMapping";

	protected DataSetExpandableListAdapterBuilder dataSetExpandableListAdapterBuilder;

	@Override
	public String[] getCompulsoryAttributes() {
        return new String[] { GROUP_SOURCE, GROUP_LAYOUT, CHILD_SOURCE, CHILD_LAYOUT };
	}

	@Override
	public void mapChildAttributeResolvers(ChildAttributeResolverMappings resolverMappings) {
		resolverMappings.map(valueModelAttributeResolver(), GROUP_SOURCE);
		resolverMappings.map(propertyAttributeResolver(), GROUP_LAYOUT);
		resolverMappings.map(predefinedMappingsAttributeResolver(), GROUP_MAPPING);

        resolverMappings.map(valueModelAttributeResolver(), CHILD_SOURCE);
        resolverMappings.map(propertyAttributeResolver(), CHILD_LAYOUT);
        resolverMappings.map(predefinedMappingsAttributeResolver(), CHILD_MAPPING);
	}

	@Override
	public void validateResolvedChildAttributes(ResolvedGroupAttributes groupAttributes) {
        super.validateResolvedChildAttributes(groupAttributes);
	}

    @Override
    public void setupChildViewAttributes(ExpandableListView view,
                                         ChildViewAttributesBuilder<ExpandableListView> childViewAttributesBuilder,
                                         BindingContext bindingContext) {
        dataSetExpandableListAdapterBuilder = new DataSetExpandableListAdapterBuilder(bindingContext);

        childViewAttributesBuilder.add(GROUP_SOURCE, new GroupSourceAttribute(dataSetExpandableListAdapterBuilder));
        childViewAttributesBuilder.add(GROUP_LAYOUT, new ExpandableRowLayoutAttributeAdapter(new GroupLayoutAttributeFactory(view, dataSetExpandableListAdapterBuilder)));

        childViewAttributesBuilder.add(CHILD_SOURCE, new ChildSourceAttribute(dataSetExpandableListAdapterBuilder));
        childViewAttributesBuilder.add(CHILD_LAYOUT, new ExpandableRowLayoutAttributeAdapter(new ChildLayoutAttributeFactory(view, dataSetExpandableListAdapterBuilder)));

        if(childViewAttributesBuilder.hasAttribute(GROUP_MAPPING))
            childViewAttributesBuilder.add(GROUP_MAPPING, new GroupMappingAttribute(new GroupMappingUpdater(dataSetExpandableListAdapterBuilder)));

        if(childViewAttributesBuilder.hasAttribute(CHILD_MAPPING))
            childViewAttributesBuilder.add(CHILD_MAPPING, new ChildMappingAttribute(new ChildMappingUpdater(dataSetExpandableListAdapterBuilder)));
    }

    @Override
    public void postBind(ExpandableListView view, BindingContext bindingContext) {
        DataSetExpandableListAdapter<?, ?> dataSetExpandableListAdapter = dataSetExpandableListAdapterBuilder.build();
        view.setAdapter(dataSetExpandableListAdapter);
    }
}
