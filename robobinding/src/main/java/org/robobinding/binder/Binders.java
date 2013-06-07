/**
 * Copyright 2011 Cheng Wei, Robert Taylor
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 */
package org.robobinding.binder;

import static com.google.common.collect.Maps.newHashMap;

import java.util.Map;

import org.robobinding.ActivityBinder;
import org.robobinding.BinderImplementor;
import org.robobinding.DialogBinder;
import org.robobinding.InternalViewBinder;
import org.robobinding.NonBindingViewInflater;
import org.robobinding.ViewFactoryInstaller;
import org.robobinding.viewattribute.BindingAttributeMapper;
import org.robobinding.viewattribute.BindingAttributeProvider;
import org.robobinding.viewattribute.absspinner.AbsSpinnerAttributeMapper;
import org.robobinding.viewattribute.adapterview.AdapterViewAttributeMapper;
import org.robobinding.viewattribute.compoundbutton.CompoundButtonAttributeMapper;
import org.robobinding.viewattribute.edittext.EditTextAttributeMapper;
import org.robobinding.viewattribute.imageview.ImageViewAttributeMapper;
import org.robobinding.viewattribute.impl.BindingAttributeMapperAdapter;
import org.robobinding.viewattribute.listview.ListViewAttributeMapper;
import org.robobinding.viewattribute.progressbar.ProgressBarAttributeMapper;
import org.robobinding.viewattribute.ratingbar.RatingBarAttributeMapper;
import org.robobinding.viewattribute.seekbar.SeekBarAttributeMapper;
import org.robobinding.viewattribute.textview.TextViewAttributeMapper;
import org.robobinding.viewattribute.view.ViewAttributeMapper;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsSpinner;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class Binders {
    public static void bind(Activity activity, int layoutId, Object presentationModel) {
	ActivityBinder activityBinder = createActivityBinder(activity, true);
	activityBinder.inflateAndBind(layoutId, presentationModel);
    }

    private static ActivityBinder createActivityBinder(Activity activity, boolean withPreInitializingViews) {
	BinderImplementor binderImplementor = createBinderImplementor(activity, withPreInitializingViews);
	return new ActivityBinder(activity, binderImplementor);
    }

    private static BinderImplementor createBinderImplementor(Context context, boolean withPreInitializingViews) {
	NonBindingViewInflater nonBindingViewInflater = NonBindingViewInflater.create(context);
	ByBindingAttributeMappingsResolverFinder byBindingAttributeProviderResolverFinder = new ByBindingAttributeMappingsResolverFinder(
		createAttributeProvidersResolver());
	BindingAttributeResolver bindingAttributeResolver = new BindingAttributeResolver(byBindingAttributeProviderResolverFinder);
	BindingViewInflater bindingViewInflater = new BindingViewInflater(nonBindingViewInflater, bindingAttributeResolver,
		new BindingAttributeParser());
	nonBindingViewInflater.installViewFactory(new ViewFactoryInstaller(bindingViewInflater));
	BindingContextFactory bindingContextFactory = new BindingContextFactory(context, withPreInitializingViews);
	BinderImplementor binderImplementor = new InternalBinder(bindingViewInflater, bindingContextFactory, new PlainTextErrorFormatter());
	return binderImplementor;
    }
    
    static BindingAttributeProvidersResolver createAttributeProvidersResolver()
    {
	Map<Class<?>, BindingAttributeProvider<? extends View>> bindingAttributeProvidersMap = newHashMap();
	bindingAttributeProvidersMap.put(View.class, adapt(new ViewAttributeMapper()));
	bindingAttributeProvidersMap.put(TextView.class, adapt(new TextViewAttributeMapper()));
	bindingAttributeProvidersMap.put(EditText.class, adapt(new EditTextAttributeMapper()));
	bindingAttributeProvidersMap.put(AdapterView.class, adapt(new AdapterViewAttributeMapper()));
	bindingAttributeProvidersMap.put(CompoundButton.class, adapt(new CompoundButtonAttributeMapper()));
	bindingAttributeProvidersMap.put(ImageView.class, adapt(new ImageViewAttributeMapper()));
	bindingAttributeProvidersMap.put(ProgressBar.class, adapt(new ProgressBarAttributeMapper()));
	bindingAttributeProvidersMap.put(SeekBar.class, adapt(new SeekBarAttributeMapper()));
	bindingAttributeProvidersMap.put(RatingBar.class, adapt(new RatingBarAttributeMapper()));
	bindingAttributeProvidersMap.put(ListView.class, adapt(new ListViewAttributeMapper()));
	bindingAttributeProvidersMap.put(AbsSpinner.class, adapt(new AbsSpinnerAttributeMapper()));
	
	return new BindingAttributeProvidersResolver(bindingAttributeProvidersMap);
    }
    
    private static <T extends View> BindingAttributeProvider<T> adapt(BindingAttributeMapper<T> mapper) {
	return new BindingAttributeMapperAdapter<T>(mapper);
    }

    public static void bindWithoutPreInitializingViews(Activity activity, int layoutId, Object presentationModel) {
	ActivityBinder activityBinder = createActivityBinder(activity, false);
	activityBinder.inflateAndBind(layoutId, presentationModel);
    }

    public static void bind(Dialog dialog, int layoutId, Object presentationModel) {
	DialogBinder dialogBinder = createDialogBinder(dialog);
	dialogBinder.inflateAndBind(layoutId, presentationModel);
    }
    
    private static DialogBinder createDialogBinder(Dialog dialog) {
	BinderImplementor binderImplementor = createBinderImplementor(dialog.getContext(), true);
	return new DialogBinder(dialog, binderImplementor);
    }

    public static View bindView(Context context, int layoutId, Object presentationModel) {
	InternalViewBinder viewBinder = createInternalViewBinder(context);
	return viewBinder.inflateAndBind(layoutId, presentationModel);
    }

    private static InternalViewBinder createInternalViewBinder(Context context)
    {
	BinderImplementor binderImplementor = createBinderImplementor(context, true);
	return new InternalViewBinder(binderImplementor, NonBindingViewInflater.create(context));
    }
    
    public static View attachToRootAndBindView(ViewGroup parentView, Context context, int layoutId, Object presentationModel) {
	InternalViewBinder viewBinder = createInternalViewBinder(context);
	return viewBinder.inflateAndBind(layoutId, presentationModel, parentView);
    }
}
