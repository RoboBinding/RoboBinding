package org.robobinding.binder;

import static com.google.common.base.Preconditions.checkNotNull;

import org.robobinding.ActivityBinder;
import org.robobinding.BinderImplementor;
import org.robobinding.DialogBinder;
import org.robobinding.InternalViewBinder;
import org.robobinding.NonBindingViewInflater;
import org.robobinding.ViewFactoryInstaller;
import org.robobinding.attribute.PropertyAttributeParser;
import org.robobinding.presentationmodel.PresentationModelAdapter;
import org.robobinding.presentationmodel.PresentationModelAdapterFactory;
import org.robobinding.viewattribute.grouped.GroupAttributesResolver;
import org.robobinding.viewattribute.impl.BindingAttributeMappingsProviderMap;
import org.robobinding.widget.adapterview.DataSetAdapter;
import org.robobinding.widget.adapterview.DataSetAdapterBuilder;
import org.robobinding.widget.view.ViewListenersMap;

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
	checkNotNull(activity, "activity must not be null");
	
	NonBindingViewInflater nonBindingViewInflater = new NonBindingViewInflater(createLayoutInflater(activity));
	BinderImplementor binderImplementor = createBinderImplementor(activity, nonBindingViewInflater, withPreInitializingViews);
	return new ActivityBinder(activity, binderImplementor);
    }

    private LayoutInflater createLayoutInflater(Context context) {
	return LayoutInflater.from(context).cloneInContext(context);
    }

    private BinderImplementor createBinderImplementor(Context context, NonBindingViewInflater nonBindingViewInflater, boolean withPreInitializingViews) {
	BindingViewInflater bindingViewInflater = createBindingViewInflater(context);
	BindingContextFactory bindingContextFactory = new BindingContextFactory(context, withPreInitializingViews, nonBindingViewInflater, 
		new PresentationModelAdapterFactory());
	BinderImplementor binderImplementor = new InternalBinder(bindingViewInflater, bindingContextFactory, new ErrorFormatterWithFirstErrorStackTrace());
	return binderImplementor;
    }

    private BindingViewInflater createBindingViewInflater(Context context) {
	LayoutInflater layoutInflater = createLayoutInflater(context);
	NonBindingViewInflater nonBindingViewInflater = new NonBindingViewInflater(layoutInflater);
	ViewAttributeBinderFactoryProvider viewAttributeBinderFactoryProvider = new ViewAttributeBinderFactoryProvider(
		new PropertyAttributeParser(), new GroupAttributesResolver(), viewListenersMap);
	ByBindingAttributeMappingsResolverFinder byBindingAttributeProviderResolverFinder = new ByBindingAttributeMappingsResolverFinder(
		new BindingAttributeMappingsProviderResolver(bindingAttributeMappingsProviderMap),
		viewAttributeBinderFactoryProvider);
	BindingAttributeResolver bindingAttributeResolver = new BindingAttributeResolver(byBindingAttributeProviderResolverFinder);
	BindingViewInflater bindingViewInflater = new BindingViewInflater(nonBindingViewInflater, bindingAttributeResolver,
		new BindingAttributeParser());

	new ViewFactoryInstaller(bindingViewInflater).install(layoutInflater);
	return bindingViewInflater;
    }

    public DialogBinder createDialogBinder(Dialog dialog) {
	checkNotNull(dialog, "dialog must not be null");
	
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

	public DataSetAdapter<?> createDataSet(Context context, Object presentationModel, int itemLayoutId, String propertyName) {
		NonBindingViewInflater nonBindingViewInflater = new NonBindingViewInflater(createLayoutInflater(context));
		PresentationModelAdapterFactory presentationModelAdapterFactory = new PresentationModelAdapterFactory();
		BindingContextFactory bindingContextFactory = new BindingContextFactory(context, true, nonBindingViewInflater, presentationModelAdapterFactory);
		BinderImplementor binderImplementor = createBinderImplementor(context, nonBindingViewInflater, true);
		PresentationModelAdapter presentationModelAdapter = presentationModelAdapterFactory.create(presentationModel);
		DataSetAdapterBuilder builder = new DataSetAdapterBuilder(bindingContextFactory.create(binderImplementor, presentationModel));

		builder.setItemLayoutId(itemLayoutId);
		builder.setValueModel(presentationModelAdapter.getDataSetPropertyValueModel(propertyName));

		return builder.build();
	}

}
