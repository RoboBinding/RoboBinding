package org.robobinding.widget.adapterview;

import org.robobinding.attribute.AbstractPropertyAttribute;
import org.robobinding.attribute.PropertyAttributeVisitor;
import org.robobinding.attribute.StaticResourceAttribute;
import org.robobinding.attribute.StaticResourcesAttribute;
import org.robobinding.attribute.ValueModelAttribute;
import org.robobinding.viewattribute.grouped.ChildViewAttribute;
import org.robobinding.viewattribute.grouped.ChildViewAttributeAdapter;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public class RowLayoutAttributeFactory implements PropertyAttributeVisitor<ChildViewAttribute> {
	private final View view;
	private final UpdaterProvider updaterProvider;

	public RowLayoutAttributeFactory(View view, UpdaterProvider updaterProvider) {
		this.view = view;
		this.updaterProvider = updaterProvider;
	}

	public ChildViewAttribute createRowLayoutAttribute(AbstractPropertyAttribute attribute) {
		return attribute.accept(this);
	}
	
	@Override
	public ChildViewAttribute visitValueModel(ValueModelAttribute attribute) {
		DynamicLayoutAttribute layoutAttribute = new DynamicLayoutAttribute(
				updaterProvider.createRowLayoutUpdater(), 
				updaterProvider.createDataSetAdapterUpdater());
		return new ChildViewAttributeAdapter(view, layoutAttribute, attribute);
	}
	
	@Override
	public ChildViewAttribute visitStaticResource(StaticResourceAttribute attribute) {
		return new StaticLayoutAttribute(updaterProvider.createRowLayoutUpdater(), attribute);
	}
	
	@Override
	public ChildViewAttribute visitStaticResources(StaticResourcesAttribute attribute) {
		return new StaticLayoutsAttribute(updaterProvider.createRowLayoutsUpdater(), attribute);
	}
	
	public static interface UpdaterProvider {
		RowLayoutUpdater createRowLayoutUpdater();
		RowLayoutsUpdater createRowLayoutsUpdater();
		DataSetAdapterUpdater createDataSetAdapterUpdater();
	}
}
