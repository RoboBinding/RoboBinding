package org.robobinding.binder;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.robobinding.NonBindingViewInflater;
import org.robobinding.PendingAttributesForView;
import org.robobinding.PendingAttributesForViewImpl;
import org.robobinding.PredefinedPendingAttributesForView;
import org.robobinding.ViewFactory.ViewCreationListener;
import org.robobinding.internal.guava.Lists;

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

    public BindingViewInflater(NonBindingViewInflater nonBindingViewInflater, 
	    BindingAttributeResolver bindingAttributeResolver,
	    BindingAttributeParser bindingAttributeParser) {
	this.nonBindingViewInflater = nonBindingViewInflater;
	this.bindingAttributeResolver = bindingAttributeResolver;
	this.bindingAttributeParser = bindingAttributeParser;
    }

    public InflatedViewWithRoot inflateView(int layoutId) {
	return inflateView(layoutId, (ViewGroup) null);
    }

    public InflatedViewWithRoot inflateView(int layoutId, ViewGroup attachToRoot) {
	resolvedBindingAttributesForChildViews = Lists.newArrayList();
	errors = new ViewHierarchyInflationErrorsException();

	View rootView = nonBindingViewInflater.inflate(layoutId, attachToRoot);

	return new InflatedViewWithRoot(rootView, resolvedBindingAttributesForChildViews, errors);
    }

    public InflatedViewWithRoot inflateView(int layoutId, Collection<PredefinedPendingAttributesForView> predefinedPendingAttributesForViewGroup) {
	resolvedBindingAttributesForChildViews = Lists.newArrayList();
	errors = new ViewHierarchyInflationErrorsException();

	View rootView = nonBindingViewInflater.inflate(layoutId);
	addPredefinedPendingAttributesForViewGroup(predefinedPendingAttributesForViewGroup, rootView);

	return new InflatedViewWithRoot(rootView, resolvedBindingAttributesForChildViews, errors);
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
