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
import org.robobinding.viewattribute.BindingAttributeMappingsProvider;
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
import android.view.LayoutInflater;
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
    private final Context context;
    
    private Binders(Context context) {
	this.context = context;
    }

    private ActivityBinder createActivityBinder(Activity activity, boolean withPreInitializingViews) {
	NonBindingViewInflater nonBindingViewInflater = new NonBindingViewInflater(createLayoutInflater());
	BinderImplementor binderImplementor = createBinderImplementor(nonBindingViewInflater, withPreInitializingViews);
	return new ActivityBinder(activity, binderImplementor);
    }
    
    private LayoutInflater createLayoutInflater() {
	return LayoutInflater.from(context).cloneInContext(context);
    }

    private BinderImplementor createBinderImplementor(NonBindingViewInflater nonBindingViewInflater, boolean withPreInitializingViews) {
	BindingViewInflater bindingViewInflater = createBindingViewInflater();
	BindingContextFactory bindingContextFactory = new BindingContextFactory(context, withPreInitializingViews, nonBindingViewInflater);
	BinderImplementor binderImplementor = new InternalBinder(bindingViewInflater, bindingContextFactory, new PlainTextErrorFormatter());
	return binderImplementor;
    }

    private BindingViewInflater createBindingViewInflater() {
	LayoutInflater layoutInflater = createLayoutInflater();
	NonBindingViewInflater nonBindingViewInflater = new NonBindingViewInflater(layoutInflater);
	ByBindingAttributeMappingsResolverFinder byBindingAttributeProviderResolverFinder = new ByBindingAttributeMappingsResolverFinder(
		new BindingAttributeMappingsProviderResolver(createBindingAttributeMappingsProviderMap()));
	BindingAttributeResolver bindingAttributeResolver = new BindingAttributeResolver(byBindingAttributeProviderResolverFinder);
	BindingViewInflater bindingViewInflater = new BindingViewInflater(nonBindingViewInflater, bindingAttributeResolver,
		new BindingAttributeParser());
	
	new ViewFactoryInstaller(bindingViewInflater).install(layoutInflater);
	return bindingViewInflater;
    }
   
    static Map<Class<?>, BindingAttributeMappingsProvider<? extends View>> createBindingAttributeMappingsProviderMap()
    {
	Map<Class<?>, BindingAttributeMappingsProvider<? extends View>> bindingAttributeMappingsProviderMap = newHashMap();
	bindingAttributeMappingsProviderMap.put(View.class, adapt(new ViewAttributeMapper()));
	bindingAttributeMappingsProviderMap.put(TextView.class, adapt(new TextViewAttributeMapper()));
	bindingAttributeMappingsProviderMap.put(EditText.class, adapt(new EditTextAttributeMapper()));
	bindingAttributeMappingsProviderMap.put(AdapterView.class, adapt(new AdapterViewAttributeMapper()));
	bindingAttributeMappingsProviderMap.put(CompoundButton.class, adapt(new CompoundButtonAttributeMapper()));
	bindingAttributeMappingsProviderMap.put(ImageView.class, adapt(new ImageViewAttributeMapper()));
	bindingAttributeMappingsProviderMap.put(ProgressBar.class, adapt(new ProgressBarAttributeMapper()));
	bindingAttributeMappingsProviderMap.put(SeekBar.class, adapt(new SeekBarAttributeMapper()));
	bindingAttributeMappingsProviderMap.put(RatingBar.class, adapt(new RatingBarAttributeMapper()));
	bindingAttributeMappingsProviderMap.put(ListView.class, adapt(new ListViewAttributeMapper()));
	bindingAttributeMappingsProviderMap.put(AbsSpinner.class, adapt(new AbsSpinnerAttributeMapper()));
	
	return bindingAttributeMappingsProviderMap;
    }
    
    private static <T extends View> BindingAttributeMappingsProvider<T> adapt(BindingAttributeMapper<T> mapper) {
	return new BindingAttributeMapperAdapter<T>(mapper);
    }
    
    private DialogBinder createDialogBinder(Dialog dialog) {
	NonBindingViewInflater nonBindingViewInflater = new NonBindingViewInflater(createLayoutInflater());
	BinderImplementor binderImplementor = createBinderImplementor(nonBindingViewInflater, true);
	return new DialogBinder(dialog, binderImplementor);
    }

    private InternalViewBinder createInternalViewBinder()
    {
	NonBindingViewInflater nonBindingViewInflater = new NonBindingViewInflater(createLayoutInflater());
	BinderImplementor binderImplementor = createBinderImplementor(nonBindingViewInflater, true);
	return new InternalViewBinder(binderImplementor, nonBindingViewInflater);
    }
  
    public static void bind(Activity activity, int layoutId, Object presentationModel) {
	ActivityBinder activityBinder = new Binders(activity).createActivityBinder(activity, true);
	activityBinder.inflateAndBind(layoutId, presentationModel);
    }
    public static void bindWithoutPreInitializingViews(Activity activity, int layoutId, Object presentationModel) {
	ActivityBinder activityBinder = new Binders(activity).createActivityBinder(activity, false);
	activityBinder.inflateAndBind(layoutId, presentationModel);
    }

    public static void bind(Dialog dialog, int layoutId, Object presentationModel) {
	DialogBinder dialogBinder = new Binders(dialog.getContext()).createDialogBinder(dialog);
	dialogBinder.inflateAndBind(layoutId, presentationModel);
    }

    public static View bindView(Context context, int layoutId, Object presentationModel) {
	InternalViewBinder viewBinder = new Binders(context).createInternalViewBinder();
	return viewBinder.inflateAndBind(layoutId, presentationModel);
    }
    
    public static View attachToRootAndBindView(ViewGroup parentView, Context context, int layoutId, Object presentationModel) {
	InternalViewBinder viewBinder = new Binders(context).createInternalViewBinder();
	return viewBinder.inflateAndBind(layoutId, presentationModel, parentView);
    }
}
