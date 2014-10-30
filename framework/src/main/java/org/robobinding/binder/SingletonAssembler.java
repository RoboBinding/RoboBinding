package org.robobinding.binder;

import org.robobinding.MenuBinder;
import org.robobinding.NonBindingViewInflater;
import org.robobinding.ViewBinder;
import org.robobinding.ViewCreationListenerInstaller;
import org.robobinding.attribute.PropertyAttributeParser;
import org.robobinding.presentationmodel.PresentationModelAdapterFactory;
import org.robobinding.viewattribute.grouped.GroupAttributesResolver;
import org.robobinding.viewattribute.grouped.ViewAttributeBinderFactory;
import org.robobinding.viewbinding.ViewBindingMap;
import org.robobinding.widgetaddon.ViewAddOnInjector;
import org.robobinding.widgetaddon.ViewAddOns;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
class SingletonAssembler {
	private final ViewBindingMap viewBindingMap;
	private final ViewAddOns viewAddOns;
	private final Context context;
	private final boolean withPreInitializingViews;

	private BindingAttributeParser bindingAttributeParser;
	private BindingAttributeResolver bindingAttributeResolver;
	private NonBindingViewInflater nonBindingViewInflater;
	private BindingViewInflater bindingViewInflater;
	private PresentationModelObjectLoader presentationModelObjectLoader;

	public SingletonAssembler(ViewBindingMap viewBindingMap, ViewAddOns viewAddOns,
			Context context, boolean withPreInitializingViews) {
		this.viewBindingMap = viewBindingMap;
		this.viewAddOns = viewAddOns;
		this.context = context;
		this.withPreInitializingViews = withPreInitializingViews;
	}

	public ViewBinder createViewBinder() {
		createDependents();

		BindingContextFactory bindingContextFactory = createBindingContextFactory();
		ViewBindingLifecycle viewBindingLifecycle = new ViewBindingLifecycle(bindingContextFactory, new ErrorFormatterWithFirstErrorStackTrace());
		ViewBinder viewBinder = new ViewBinderImpl(bindingViewInflater, viewBindingLifecycle, presentationModelObjectLoader);
		bindingContextFactory.setBinderProvider(new BinderProviderImpl(bindingViewInflater, viewBindingLifecycle, nonBindingViewInflater, viewBinder));

		return viewBinder;
	}

	private void createDependents() {
		bindingAttributeParser = new BindingAttributeParser();
		bindingAttributeResolver = createBindingAttributeResolver();

		LayoutInflater layoutInflater = createLayoutInflater();
		nonBindingViewInflater = new NonBindingViewInflater(layoutInflater);
		bindingViewInflater = createBindingViewInflater(layoutInflater);
		
		presentationModelObjectLoader = new PresentationModelObjectLoader();
	}

	private BindingAttributeResolver createBindingAttributeResolver() {
		ViewAttributeBinderFactory viewAttributeBinderFactory = new ViewAttributeBinderFactory(new PropertyAttributeParser(),
				new GroupAttributesResolver(), new ViewAddOnInjector(viewAddOns));
		ByBindingAttributeMappingsResolverFinder byBindingAttributeProviderResolverFinder = new ByBindingAttributeMappingsResolverFinder(
				viewBindingMap.buildInitializedBindingAttributeMappingsProviders(viewAttributeBinderFactory));
		BindingAttributeResolver bindingAttributeResolver = new BindingAttributeResolver(byBindingAttributeProviderResolverFinder);
		return bindingAttributeResolver;
	}

	private LayoutInflater createLayoutInflater() {
		return LayoutInflater.from(context).cloneInContext(context);
	}

	private BindingViewInflater createBindingViewInflater(LayoutInflater layoutInflater) {
		BindingViewInflater bindingViewInflater = new BindingViewInflater(nonBindingViewInflater, bindingAttributeResolver, bindingAttributeParser);

		new ViewCreationListenerInstaller(layoutInflater).install(bindingViewInflater);
		return bindingViewInflater;
	}

	private BindingContextFactory createBindingContextFactory() {
		return new BindingContextFactory(context, withPreInitializingViews, new PresentationModelAdapterFactory());
	}

	public MenuBinder createMenuBinder(MenuInflater menuInflater, Menu menu) {
		createDependents();
	
		BindingContextFactory bindingContextFactory = createBindingContextFactory();
		ViewBindingLifecycle viewBindingLifecycle = new ViewBindingLifecycle(bindingContextFactory, new ErrorFormatterWithFirstErrorStackTrace());
		ViewBinder viewBinder = new ViewBinderImpl(bindingViewInflater, viewBindingLifecycle, presentationModelObjectLoader);
		bindingContextFactory.setBinderProvider(new BinderProviderImpl(bindingViewInflater, viewBindingLifecycle, nonBindingViewInflater, viewBinder));
	
		BindingMenuInflater bindingMenuInflater = new BindingMenuInflater(context, menu, menuInflater, bindingAttributeParser, bindingAttributeResolver);
		return new MenuBinderImpl(bindingMenuInflater, viewBindingLifecycle, presentationModelObjectLoader);
	}
}