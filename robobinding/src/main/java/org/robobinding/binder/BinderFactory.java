package org.robobinding.binder;

import org.robobinding.ActivityBinder;
import org.robobinding.BinderImplementor;
import org.robobinding.DialogBinder;
import org.robobinding.InternalViewBinder;
import org.robobinding.NonBindingViewInflater;
import org.robobinding.ViewBinderImplementor;
import org.robobinding.ViewFactoryInstaller;
import org.robobinding.viewattribute.impl.BindingAttributeMappingsProviderMap;
import org.robobinding.viewattribute.impl.ViewAttributeInitializerFactory;
import org.robobinding.viewattribute.view.ViewListenersMap;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class BinderFactory {
    private final ViewListenersMap viewListenersMap;
    private final BindingAttributeMappingsProviderMap bindingAttributeMappingsProviderMap;
    public BinderFactory(ViewListenersMap viewListenersMap,
	   BindingAttributeMappingsProviderMap bindingAttributeMappingsProviderMap) {
	this.viewListenersMap = viewListenersMap;
	this.bindingAttributeMappingsProviderMap = bindingAttributeMappingsProviderMap;
    }

    public ActivityBinder createActivityBinder(Activity activity, boolean withPreInitializingViews) {
	NonBindingViewInflater nonBindingViewInflater = new NonBindingViewInflater(createLayoutInflater(activity));
	BinderImplementor binderImplementor = createBinderImplementor(activity, nonBindingViewInflater, withPreInitializingViews);
	return new ActivityBinder(activity, binderImplementor);
    }

    private LayoutInflater createLayoutInflater(Context context) {
	return LayoutInflater.from(context).cloneInContext(context);
    }

    private BinderImplementor createBinderImplementor(Context context, NonBindingViewInflater nonBindingViewInflater, boolean withPreInitializingViews) {
	BindingViewInflater bindingViewInflater = createBindingViewInflater(context);
	BindingContextFactory bindingContextFactory = new BindingContextFactory(context, withPreInitializingViews, nonBindingViewInflater);
	BinderImplementor binderImplementor = new InternalBinder(bindingViewInflater, bindingContextFactory, new PlainTextErrorFormatter());
	return binderImplementor;
    }

    private ViewBinderImplementor createViewBinderImplementor(Context context, NonBindingViewInflater nonBindingViewInflater, boolean withPreInitializingViews) {
	BindingViewInflater bindingViewInflater = createBindingViewInflater(context);
	BindingContextFactory bindingContextFactory = new BindingContextFactory(context, withPreInitializingViews, nonBindingViewInflater);
	ViewBinderImplementor viewBinderImplementor = new InternalViewBinder2(bindingViewInflater, bindingContextFactory, new PlainTextErrorFormatter());
	return viewBinderImplementor;
    }

    private BindingViewInflater createBindingViewInflater(Context context) {
	LayoutInflater layoutInflater = createLayoutInflater(context);
	NonBindingViewInflater nonBindingViewInflater = new NonBindingViewInflater(layoutInflater);
	ByBindingAttributeMappingsResolverFinder byBindingAttributeProviderResolverFinder = new ByBindingAttributeMappingsResolverFinder(
		new BindingAttributeMappingsProviderResolver(bindingAttributeMappingsProviderMap),
		new ViewAttributeInitializerFactory(viewListenersMap));
	BindingAttributeResolver bindingAttributeResolver = new BindingAttributeResolver(byBindingAttributeProviderResolverFinder);
	BindingViewInflater bindingViewInflater = new BindingViewInflater(nonBindingViewInflater, bindingAttributeResolver,
		new BindingAttributeParser());

	new ViewFactoryInstaller(bindingViewInflater).install(layoutInflater);
	return bindingViewInflater;
    }

    public DialogBinder createDialogBinder(Dialog dialog) {
	Context context = dialog.getContext();
	NonBindingViewInflater nonBindingViewInflater = new NonBindingViewInflater(createLayoutInflater(context));
	BinderImplementor binderImplementor = createBinderImplementor(context, nonBindingViewInflater, true);
	return new DialogBinder(dialog, binderImplementor);
    }

    public InternalViewBinder createInternalViewBinder(Context context) {
	NonBindingViewInflater nonBindingViewInflater = new NonBindingViewInflater(createLayoutInflater(context));
	BinderImplementor binderImplementor = createBinderImplementor(context, nonBindingViewInflater, true);
	return new InternalViewBinder(binderImplementor, nonBindingViewInflater);
    }

    public InternalViewBinder createInternalViewBinder2(Context context) {
	NonBindingViewInflater nonBindingViewInflater = new NonBindingViewInflater(createLayoutInflater(context));
	BinderImplementor binderImplementor = createViewBinderImplementor(context, nonBindingViewInflater, true);
	return new InternalViewBinder(binderImplementor, nonBindingViewInflater);
    }
}
