package org.robobinding.binder;

import org.robobinding.BinderProvider;
import org.robobinding.MenuBinder;
import org.robobinding.NonBindingViewInflater;
import org.robobinding.NonBindingViewInflaterImpl;
import org.robobinding.NonBindingViewInflaterProxy;
import org.robobinding.ViewBinder;
import org.robobinding.ViewCreationListenerInstaller;
import org.robobinding.attribute.PropertyAttributeParser;
import org.robobinding.presentationmodel.PresentationModelAdapterFactory;
import org.robobinding.viewattribute.grouped.GroupAttributesResolver;
import org.robobinding.viewattribute.grouped.ViewAttributeBinderFactories;
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

	private BindingAttributeParser bindingAttributeParser;
	private BindingAttributeResolver bindingAttributeResolver;
	private NonBindingViewInflater nonBindingViewInflater;
	private BindingViewInflater bindingViewInflater;
	private PresentationModelObjectLoader presentationModelObjectLoader;

	public SingletonAssembler(ViewBindingMap viewBindingMap, ViewAddOns viewAddOns, Context context) {
		this.viewBindingMap = viewBindingMap;
		this.viewAddOns = viewAddOns;
		this.context = context;
	}

	public ViewBinder createViewBinder() {
		createDependents();

		BinderProviderProxy proxy = new BinderProviderProxy();
		BindingContextFactory bindingContextFactory = createBindingContextFactory(proxy);
		ViewBindingLifecycle viewBindingLifecycle = new ViewBindingLifecycle(bindingContextFactory, new ErrorFormatterWithFirstErrorStackTrace());
		ViewBinder viewBinder = new ViewBinderImpl(bindingViewInflater, viewBindingLifecycle, presentationModelObjectLoader);
		proxy.setProvider(new BinderProviderImpl(bindingViewInflater, viewBindingLifecycle, nonBindingViewInflater, viewBinder));

		return viewBinder;
	}

	private void createDependents() {
		bindingAttributeParser = new BindingAttributeParser();
		bindingAttributeResolver = createBindingAttributeResolver();

		createViewInflaters();
		
		presentationModelObjectLoader = new PresentationModelObjectLoader();
	}

	private BindingAttributeResolver createBindingAttributeResolver() {
		ViewAttributeBinderFactories viewAttributeBinderFactories = new ViewAttributeBinderFactories(new PropertyAttributeParser(),
				new GroupAttributesResolver(), new ViewAddOnInjector(viewAddOns));
		ByBindingAttributeMappingsResolverFinder byBindingAttributeProviderResolverFinder = new ByBindingAttributeMappingsResolverFinder(
				viewBindingMap.buildInitializedBindingAttributeMappingsProviders(),	viewAttributeBinderFactories);
		BindingAttributeResolver bindingAttributeResolver = new BindingAttributeResolver(byBindingAttributeProviderResolverFinder);
		return bindingAttributeResolver;
	}

	private void createViewInflaters() {
		NonBindingViewInflaterProxy proxy = new NonBindingViewInflaterProxy();
		
		bindingViewInflater = new BindingViewInflater(
				proxy, bindingAttributeResolver, bindingAttributeParser);

		LayoutInflater layoutInflater = new ViewCreationListenerInstaller(bindingViewInflater)
			.installWith(LayoutInflater.from(context));

		nonBindingViewInflater = new NonBindingViewInflaterImpl(layoutInflater);
		proxy.setInflater(nonBindingViewInflater);
	}

	private BindingContextFactory createBindingContextFactory(BinderProvider binderProvider) {
		return new BindingContextFactory(context,
				new PresentationModelAdapterFactory(), binderProvider);
	}

	public MenuBinder createMenuBinder(MenuInflater menuInflater, Menu menu) {
		createDependents();
	
		BinderProviderProxy proxy = new BinderProviderProxy();
		BindingContextFactory bindingContextFactory = createBindingContextFactory(proxy);
		ViewBindingLifecycle viewBindingLifecycle = new ViewBindingLifecycle(bindingContextFactory, new ErrorFormatterWithFirstErrorStackTrace());
		ViewBinder viewBinder = new ViewBinderImpl(bindingViewInflater, viewBindingLifecycle, presentationModelObjectLoader);
		proxy.setProvider(new BinderProviderImpl(bindingViewInflater, viewBindingLifecycle, nonBindingViewInflater, viewBinder));
	
		BindingMenuInflater bindingMenuInflater = new BindingMenuInflater(context, menu, menuInflater, bindingAttributeParser, bindingAttributeResolver);
		return new MenuBinderImpl(bindingMenuInflater, viewBindingLifecycle, presentationModelObjectLoader);
	}
}