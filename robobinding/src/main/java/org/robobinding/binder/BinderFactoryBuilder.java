/**
 * Copyright 2013 Cheng Wei, Robert Taylor
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

import org.robobinding.attribute.PropertyAttributeParser;
import org.robobinding.viewattribute.BindingAttributeMapper;
import org.robobinding.viewattribute.absspinner.AbsSpinnerAttributeMapper;
import org.robobinding.viewattribute.adapterview.AdapterViewAttributeMapper;
import org.robobinding.viewattribute.adapterview.AdapterViewListeners;
import org.robobinding.viewattribute.compoundbutton.CompoundButtonAttributeMapper;
import org.robobinding.viewattribute.compoundbutton.CompoundButtonListeners;
import org.robobinding.viewattribute.edittext.EditTextAttributeMapper;
import org.robobinding.viewattribute.imageview.ImageViewAttributeMapper;
import org.robobinding.viewattribute.impl.BindingAttributeMappingsProviderMapBuilder;
import org.robobinding.viewattribute.listview.ListViewAttributeMapper;
import org.robobinding.viewattribute.progressbar.ProgressBarAttributeMapper;
import org.robobinding.viewattribute.ratingbar.RatingBarAttributeMapper;
import org.robobinding.viewattribute.ratingbar.RatingBarListeners;
import org.robobinding.viewattribute.seekbar.SeekBarAttributeMapper;
import org.robobinding.viewattribute.seekbar.SeekBarListeners;
import org.robobinding.viewattribute.textview.TextViewAttributeMapper;
import org.robobinding.viewattribute.view.ViewAttributeMapper;
import org.robobinding.viewattribute.view.ViewListeners;
import org.robobinding.viewattribute.view.ViewListenersMapBuilder;

import android.view.View;
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
 * @author Cheng Wei
 */
public class BinderFactoryBuilder {
    private final PropertyAttributeParser propertyAttributeParser;
    private final ViewListenersMapBuilder viewListenersMapBuilder;
    private final BindingAttributeMappingsProviderMapBuilder bindingAttributeMappingsProviderMapBuilder;
    
    public BinderFactoryBuilder() {
	this.propertyAttributeParser = new PropertyAttributeParser();
	this.viewListenersMapBuilder = defaultViewListenersMapBuilder();
	this.bindingAttributeMappingsProviderMapBuilder = defaultBindingAttributeMappingsProviderMapBuilder(propertyAttributeParser);
    }
    
    
    static ViewListenersMapBuilder defaultViewListenersMapBuilder() {
        ViewListenersMapBuilder viewListenersMap = new ViewListenersMapBuilder();
        viewListenersMap.put(View.class, ViewListeners.class);
        viewListenersMap.put(AdapterView.class, AdapterViewListeners.class);
        viewListenersMap.put(CompoundButton.class, CompoundButtonListeners.class);
        viewListenersMap.put(SeekBar.class, SeekBarListeners.class);
        viewListenersMap.put(RatingBar.class, RatingBarListeners.class);
        
        return viewListenersMap;
    }

    @SuppressWarnings("rawtypes")
    static BindingAttributeMappingsProviderMapBuilder defaultBindingAttributeMappingsProviderMapBuilder(
	    PropertyAttributeParser propertyAttributeParser) {
        BindingAttributeMappingsProviderMapBuilder bindingAttributeMappingsProviderMap = new BindingAttributeMappingsProviderMapBuilder(
        	propertyAttributeParser);
        bindingAttributeMappingsProviderMap.put(View.class, new ViewAttributeMapper());
        bindingAttributeMappingsProviderMap.put(TextView.class, new TextViewAttributeMapper());
        bindingAttributeMappingsProviderMap.put(EditText.class, new EditTextAttributeMapper());
        bindingAttributeMappingsProviderMap.put((Class<? extends AdapterView>) AdapterView.class, new AdapterViewAttributeMapper());
        bindingAttributeMappingsProviderMap.put(CompoundButton.class, new CompoundButtonAttributeMapper());
        bindingAttributeMappingsProviderMap.put(ImageView.class, new ImageViewAttributeMapper());
        bindingAttributeMappingsProviderMap.put(ProgressBar.class, new ProgressBarAttributeMapper());
        bindingAttributeMappingsProviderMap.put(SeekBar.class, new SeekBarAttributeMapper());
        bindingAttributeMappingsProviderMap.put(RatingBar.class, new RatingBarAttributeMapper());
        bindingAttributeMappingsProviderMap.put(ListView.class, new ListViewAttributeMapper());
        bindingAttributeMappingsProviderMap.put(AbsSpinner.class, new AbsSpinnerAttributeMapper());
        
        return bindingAttributeMappingsProviderMap;
    }

    public <T extends View> BinderFactoryBuilder mapView(Class<T> viewClass, BindingAttributeMapper<T> bindingAttributeMapper) {
	bindingAttributeMappingsProviderMapBuilder.put(viewClass, bindingAttributeMapper);
	return this;
    }
    
    public <T extends View> BinderFactoryBuilder mapView(Class<T> viewClass, BindingAttributeMapper<T> bindingAttributeMapper,
	    Class<? extends ViewListeners> viewListenersClass) {
	mapView(viewClass, bindingAttributeMapper);
	viewListenersMapBuilder.put(viewClass, viewListenersClass);
	return this;
    }
    
    public BinderFactory build() {
	return new BinderFactory(
		viewListenersMapBuilder.build(), 
		bindingAttributeMappingsProviderMapBuilder.build(), 
		propertyAttributeParser);
    }

}
