package org.robobinding.binder;

import org.robobinding.MenuBinder;
import org.robobinding.NonBindingViewInflater;
import org.robobinding.ViewBinder;
import org.robobinding.ViewCreationListenerInstaller;
import org.robobinding.attribute.PropertyAttributeParser;
import org.robobinding.presentationmodel.AbstractPresentationModelObject;
import org.robobinding.presentationmodel.PresentationModelAdapter;
import org.robobinding.presentationmodel.PresentationModelAdapterFactory;
import org.robobinding.viewattribute.ViewListenersMap;
import org.robobinding.viewattribute.grouped.GroupAttributesResolver;
import org.robobinding.viewattribute.impl.BindingAttributeMappingsProviderMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;

import com.google.common.base.Preconditions;
import org.robobinding.widget.adapterview.DataSetAdapter;
import org.robobinding.widget.adapterview.DataSetAdapterBuilder;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class BinderFactory {
	private final ViewListenersMap viewListenersMap;
	private final BindingAttributeMappingsProviderMap bindingAttributeMappingsProviderMap;

	BinderFactory(ViewListenersMap viewListenersMap, BindingAttributeMappingsProviderMap bindingAttributeMappingsProviderMap) {
		this.viewListenersMap = viewListenersMap;
		this.bindingAttributeMappingsProviderMap = bindingAttributeMappingsProviderMap;
	}

	public ViewBinder createViewBinder(Context context) {
		return createViewBinder(context, true);
	}

	public ViewBinder createViewBinder(Context context, boolean withPreInitializingViews) {
		checkContext(context);

		SingleInstanceCreator creator = new SingleInstanceCreator(viewListenersMap, bindingAttributeMappingsProviderMap, context, withPreInitializingViews);
		return creator.createViewBinder();
	}

	private void checkContext(Context context) {
		Preconditions.checkNotNull(context, "context must not be null");
	}

	public MenuBinder createMenuBinder(Menu menu, MenuInflater menuInflater, Context context) {
		return createMenuBinder(menu, menuInflater, context, true);
	}

	public MenuBinder createMenuBinder(Menu menu, MenuInflater menuInflater, Context context, boolean withPreInitializingViews) {
		Preconditions.checkNotNull(menuInflater, "menuInflater must not be null");
		Preconditions.checkNotNull(menu, "menu must not be null");
		checkContext(context);

		SingleInstanceCreator creator = new SingleInstanceCreator(viewListenersMap, bindingAttributeMappingsProviderMap, context, withPreInitializingViews);
		return creator.createMenuBinder(menuInflater, menu);
	}

    public DataSetAdapter<?> createDataSet(Context context, Object presentationModel, int itemLayoutId, int dropDownLayoutId, String propertyName) {
        SingleInstanceCreator creator = new SingleInstanceCreator(viewListenersMap, bindingAttributeMappingsProviderMap, context, true);
        return creator.createDataSet(context, presentationModel, itemLayoutId, dropDownLayoutId, propertyName);
    }


        private static class SingleInstanceCreator {
		private final ViewListenersMap viewListenersMap;
		private final BindingAttributeMappingsProviderMap bindingAttributeMappingsProviderMap;
		private final Context context;
		private final boolean withPreInitializingViews;

		private BindingAttributeParser bindingAttributeParser;
		private BindingAttributeResolver bindingAttributeResolver;
		private NonBindingViewInflater nonBindingViewInflater;
		private BindingViewInflater bindingViewInflater;
		private PresentationModelObjectLoader presentationModelObjectLoader;

		public SingleInstanceCreator(ViewListenersMap viewListenersMap, BindingAttributeMappingsProviderMap bindingAttributeMappingsProviderMap,
				Context context, boolean withPreInitializingViews) {
			this.viewListenersMap = viewListenersMap;
			this.bindingAttributeMappingsProviderMap = bindingAttributeMappingsProviderMap;
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
			ViewAttributeBinderFactoryProvider viewAttributeBinderFactoryProvider = new ViewAttributeBinderFactoryProvider(new PropertyAttributeParser(),
					new GroupAttributesResolver(), viewListenersMap);
			ByBindingAttributeMappingsResolverFinder byBindingAttributeProviderResolverFinder = new ByBindingAttributeMappingsResolverFinder(
					bindingAttributeMappingsProviderMap, viewAttributeBinderFactoryProvider);
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

        public DataSetAdapter<?> createDataSet(Context context, Object presentationModel, int itemLayoutId, int dropDownLayoutId, String propertyName) {
            createDependents();

            AbstractPresentationModelObject presentationModelObject = presentationModelObjectLoader.load(presentationModel);
            PresentationModelAdapterFactory presentationModelAdapterFactory = new PresentationModelAdapterFactory();
            BindingContextFactory bindingContextFactory = new BindingContextFactory(context, true, presentationModelAdapterFactory);

            PresentationModelAdapter presentationModelAdapter = presentationModelAdapterFactory.create(presentationModelObject);
            DataSetAdapterBuilder builder = new DataSetAdapterBuilder(bindingContextFactory.create(presentationModelObject));

            builder.setItemLayoutId(itemLayoutId);
            builder.setDropDownLayoutId(dropDownLayoutId);
            builder.setValueModel(presentationModelAdapter.getDataSetPropertyValueModel(propertyName));

            return builder.build();
        }

    }

}
