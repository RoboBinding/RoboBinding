package org.robobinding.widget.expandablelistview;

import android.widget.ExpandableListView;
import org.robobinding.attribute.AbstractPropertyAttribute;
import org.robobinding.viewattribute.grouped.ChildViewAttribute;
import org.robobinding.viewattribute.grouped.ChildViewAttributeAdapter;
import org.robobinding.widget.adapterview.StaticLayoutAttribute;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Jihun Lee
 */
public abstract class ExpandableRowLayoutAttributeFactory {
    private final ExpandableListView expandableListView;
    private final DataSetExpandableListAdapterBuilder adapterBuilder;

    protected ExpandableRowLayoutAttributeFactory(
            ExpandableListView expandableListView,
            DataSetExpandableListAdapterBuilder adapterBuilder){
        this.expandableListView = expandableListView;
        this.adapterBuilder = adapterBuilder;
    }

    public ChildViewAttribute createChildLayoutAttribute(AbstractPropertyAttribute attribute) {
        ExpandableRowLayoutUpdater expandableRowLayoutUpdater = createChildLayoutUpdater(adapterBuilder);
        if(attribute.isStaticResource()){
            return createStaticLayoutAttribute(attribute, expandableRowLayoutUpdater);
        } else {
            return createDynamicLayoutAttribute(attribute, expandableRowLayoutUpdater);
        }
    }
    private ChildViewAttribute createDynamicLayoutAttribute(
            AbstractPropertyAttribute attribute,
            ExpandableRowLayoutUpdater expandableRowLayoutUpdater) {
        DataSetExpandableListAdapterUpdater adapter = new DataSetExpandableListAdapterUpdater(adapterBuilder, expandableListView);

        return new ChildViewAttributeAdapter<ExpandableListView>(
                expandableListView,
                new DynamicLayoutAttribute(expandableRowLayoutUpdater, adapter), attribute.asValueModelAttribute());
    }

    private ChildViewAttribute createStaticLayoutAttribute(AbstractPropertyAttribute attribute, ExpandableRowLayoutUpdater expandableRowLayoutUpdater) {
        return new StaticLayoutAttribute(expandableRowLayoutUpdater, attribute.asStaticResourceAttribute());
    }

    protected abstract ExpandableRowLayoutUpdater createChildLayoutUpdater(DataSetExpandableListAdapterBuilder adapterBuilder);

}
