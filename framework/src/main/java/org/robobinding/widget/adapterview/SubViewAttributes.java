package org.robobinding.widget.adapterview;

import static org.robobinding.attribute.ChildAttributeResolvers.staticResourceAttributeResolver;
import static org.robobinding.attribute.ChildAttributeResolvers.valueModelAttributeResolver;

import org.robobinding.BindingContext;
import org.robobinding.attribute.ChildAttributeResolverMappings;
import org.robobinding.attribute.ResolvedGroupAttributes;
import org.robobinding.viewattribute.grouped.ChildViewAttribute;
import org.robobinding.viewattribute.grouped.ChildViewAttributeFactory;
import org.robobinding.viewattribute.grouped.ChildViewAttributesBuilder;
import org.robobinding.viewattribute.grouped.GroupedViewAttribute;
import org.robobinding.widget.view.AbstractVisibility;
import org.robobinding.widget.view.VisibilityAttributeFactory;
import org.robobinding.widget.view.VisibilityFactory;

import android.view.View;
import android.widget.AdapterView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class SubViewAttributes<T extends AdapterView<?>> implements GroupedViewAttribute<T>, SubViewHolder {
	private View subView;
	private SubViewAttributesStrategy<T> subViewAttributesStrategy;

	public SubViewAttributes(SubViewAttributesStrategy<T> subViewAttributesStrategy) {
		this.subViewAttributesStrategy = subViewAttributesStrategy;
	}

	@Override
	public String[] getCompulsoryAttributes() {
		return new String[] { layoutAttribute() };
	}

	@Override
	public void mapChildAttributeResolvers(ChildAttributeResolverMappings resolverMappings) {
		resolverMappings.map(staticResourceAttributeResolver(), layoutAttribute());
		resolverMappings.map(valueModelAttributeResolver(), subViewPresentationModel());
		resolverMappings.map(valueModelAttributeResolver(), visibilityAttribute());
	}

	@Override
	public void validateResolvedChildAttributes(ResolvedGroupAttributes groupAttributes) {
	}

	@Override
	public void setupChildViewAttributes(T view, ChildViewAttributesBuilder<T> childViewAttributesBuilder) {
		childViewAttributesBuilder.failOnFirstBindingError();

		SubViewLayoutAttribute subViewLayoutAttribute = new SubViewLayoutAttribute();
		childViewAttributesBuilder.add(layoutAttribute(), subViewLayoutAttribute);

		boolean hasSubViewPresentationModel = childViewAttributesBuilder.hasAttribute(subViewPresentationModel());
		childViewAttributesBuilder.addDependent(subViewPresentationModel(), new SubViewAttributeFactory(view, subViewLayoutAttribute, hasSubViewPresentationModel));

		if (childViewAttributesBuilder.hasAttribute(visibilityAttribute())) {
			childViewAttributesBuilder.add(visibilityAttribute(), new VisibilityAttributeFactory<T>(new SubViewVisibilityFactory()));
		}
	}

	@Override
	public void postBind(T view, BindingContext bindingContext) {
		subViewAttributesStrategy.addSubView(view, subView, bindingContext.getContext());
	}

	private String visibilityAttribute() {
		return subViewAttributesStrategy.visibilityAttribute();
	}

	private String subViewPresentationModel() {
		return subViewAttributesStrategy.subViewPresentationModelAttribute();
	}

	private String layoutAttribute() {
		return subViewAttributesStrategy.layoutAttribute();
	}

	@Override
	public void setSubView(View subView) {
		this.subView = subView;
	}

	private class SubViewAttributeFactory implements ChildViewAttributeFactory {
		private final T view;
		private final SubViewLayoutAttribute subViewLayoutAttribute;
		private final boolean hasSubViewPresentationModel;

		public SubViewAttributeFactory(T view, SubViewLayoutAttribute subViewLayoutAttribute, boolean hasSubViewPresentationModel) {
			this.view = view;
			this.subViewLayoutAttribute = subViewLayoutAttribute;
			this.hasSubViewPresentationModel = hasSubViewPresentationModel;
		}

		@Override
		public ChildViewAttribute create() {
			int layoutId = subViewLayoutAttribute.getLayoutId();
			return hasSubViewPresentationModel ? new SubViewPresentationModelAttribute(view, layoutId, SubViewAttributes.this)
					: new SubViewWithoutPresentationModelAttribute(view, layoutId, SubViewAttributes.this);
		}
	}

	private class SubViewVisibilityFactory implements VisibilityFactory<T> {
		public AbstractVisibility create(T view) {
			return subViewAttributesStrategy.createVisibility(view, subView);
		}
	}

}