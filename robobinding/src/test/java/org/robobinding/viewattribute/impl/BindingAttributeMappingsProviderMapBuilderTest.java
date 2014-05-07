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
