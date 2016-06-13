package org.robobinding.binder;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.robobinding.NonBindingViewInflater;
import org.robobinding.PendingAttributesForView;
import org.robobinding.PendingAttributesForViewImpl;
import org.robobinding.PredefinedPendingAttributesForView;
import org.robobinding.ViewCreationListener;
import org.robobinding.util.Lists;

import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public class BindingViewInflater implements ViewCreationListener {
	private final NonBindingViewInflater nonBindingViewInflater;
	private final BindingAttributeResolver bindingAttributeResolver;
	private final BindingAttributeParser bindingAttributeParser;
	private ViewHierarchyInflationErrorsException errors;
	private List<ResolvedBindingAttributesForView> resolvedBindingAttributesForChildViews;

	public BindingViewInflater(NonBindingViewInflater nonBindingViewInflater, BindingAttributeResolver bindingAttributeResolver,
			BindingAttributeParser bindingAttributeParser) {
		this.nonBindingViewInflater = nonBindingViewInflater;
		this.bindingAttributeResolver = bindingAttributeResolver;
		this.bindingAttributeParser = bindingAttributeParser;
	}

	public InflatedViewWithRoot inflateView(int layoutId) {
		resolvedBindingAttributesForChildViews = Lists.newArrayList();
		errors = new ViewHierarchyInflationErrorsException();

		View rootView = nonBindingViewInflater.inflateWithoutRoot(layoutId);

		return createInflatedViewWithRoot(rootView);
	}

	private InflatedViewWithRoot createInflatedViewWithRoot(View rootView) {
		InflatedViewWithRoot inflatedView = new InflatedViewWithRoot(rootView, resolvedBindingAttributesForChildViews, errors);
		resolvedBindingAttributesForChildViews = null;
		errors = null;
		return inflatedView;
	}

	public InflatedViewWithRoot inflateView(int layoutId, ViewGroup root, boolean attachToRoot) {
		return inflateView(layoutId, Collections.<PredefinedPendingAttributesForView>emptyList(), root, attachToRoot);
	}

	public InflatedViewWithRoot inflateView(int layoutId, Collection<PredefinedPendingAttributesForView> predefinedPendingAttributesForViewGroup,
			ViewGroup root, boolean attachToRoot) {
		resolvedBindingAttributesForChildViews = Lists.newArrayList();
		errors = new ViewHierarchyInflationErrorsException();

		View rootView = nonBindingViewInflater.inflate(layoutId, root, attachToRoot);
		addPredefinedPendingAttributesForViewGroup(predefinedPendingAttributesForViewGroup, rootView);

		return createInflatedViewWithRoot(rootView);
	}

	private void addPredefinedPendingAttributesForViewGroup(Collection<PredefinedPendingAttributesForView> predefinedPendingAttributesForViewGroup,
			View rootView) {
		for (PredefinedPendingAttributesForView predefinedPendingAttributesForView : predefinedPendingAttributesForViewGroup) {
			PendingAttributesForView pendingAttributesForView = predefinedPendingAttributesForView.createPendingAttributesForView(rootView);
			resolveAndAddViewBindingAttributes(pendingAttributesForView);
		}
	}

	@Override
	public void onViewCreated(View childView, AttributeSet attrs) {
		Map<String, String> pendingAttributeMappings = bindingAttributeParser.parse(attrs);
		if (!pendingAttributeMappings.isEmpty()) {
			PendingAttributesForView pendingAttributesForView = new PendingAttributesForViewImpl(childView, pendingAttributeMappings);
			resolveAndAddViewBindingAttributes(pendingAttributesForView);
		}
	}

	private void resolveAndAddViewBindingAttributes(PendingAttributesForView pendingAttributesForView) {
		ViewResolutionResult viewResolutionResult = bindingAttributeResolver.resolve(pendingAttributesForView);
		viewResolutionResult.addPotentialErrorTo(errors);
		resolvedBindingAttributesForChildViews.add(viewResolutionResult.getResolvedBindingAttributes());
	}
}
