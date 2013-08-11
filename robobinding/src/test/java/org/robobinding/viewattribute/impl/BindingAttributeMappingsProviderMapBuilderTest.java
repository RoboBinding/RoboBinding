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
package org.robobinding.viewattribute.impl;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.robobinding.attribute.PropertyAttributeParser;
import org.robobinding.viewattribute.BindingAttributeMapper;
import org.robobinding.viewattribute.BindingAttributeMappingsProvider;

import android.view.View;
import android.widget.ListView;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@RunWith(MockitoJUnitRunner.class)
public class BindingAttributeMappingsProviderMapBuilderTest {
    @Mock
    private PropertyAttributeParser propertyAttributeParser;
    @Mock
    private BindingAttributeMapper<View> bindingAttributeMapperForView1;
    @Mock
    private BindingAttributeMapper<View> bindingAttributeMapperForView2;
    @Mock
    private BindingAttributeMapper<ListView> bindingAttributeMapperForListView;
    
    @Test
    public void whenPutBindingAttributeMappingsForViews_thenTheMappingsAreAdded() {
	BindingAttributeMappingsProviderMapBuilder builder = new BindingAttributeMappingsProviderMapBuilder(propertyAttributeParser);
	builder.put(View.class, bindingAttributeMapperForView1);
	builder.put(ListView.class, bindingAttributeMapperForListView);
	
	BindingAttributeMappingsProviderMap map = builder.build();
	
	assertThat(map.mappings.size(), is(2));
    }
    
    @Test
    @SuppressWarnings("rawtypes")
    public void whenPutBindingAttributeMappingsForAViewSecondTime_thenKeepTheLatestMappings() {
	BindingAttributeMappingsProviderMapBuilder builder = new BindingAttributeMappingsProviderMapBuilder(propertyAttributeParser);
	builder.put(View.class, bindingAttributeMapperForView1);
	builder.put(View.class, bindingAttributeMapperForView2);
	
	BindingAttributeMappingsProviderMap map = builder.build();
	
	assertThat(map.mappings.size(), is(1));
	assertThat(map.find(View.class), 
		equalTo((BindingAttributeMappingsProvider) new BindingAttributeMapperAdapter<View>(bindingAttributeMapperForView2, propertyAttributeParser)));
    }
}
