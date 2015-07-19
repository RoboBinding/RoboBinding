package org.robobinding.widgetaddon;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Maps;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ViewAddOnFactoriesTest {
	@Mock
	private Object view;
	
	@Test(expected=RuntimeException.class)
	public void givenViewWithoutViewAddOnFactory_whenCreateViewAddOn_thenThrowError() {
		Map<Class<?>, ViewAddOnFactory> emptyMappings = Collections.emptyMap();
		ViewAddOnFactories factories = new ViewAddOnFactories(emptyMappings);
		
		factories.createViewAddOn(view);
	}
	
	@Test
	public void givenViewWithViewAddOnFactory_whenCreateViewAddOn_thenReturnInstance() {
		ViewAddOnFactory factory = mock(ViewAddOnFactory.class);
		ViewAddOn viewAddOn = mock(ViewAddOn.class);
		when(factory.create(view)).thenReturn(viewAddOn);
		
		Map<Class<?>, ViewAddOnFactory> mappings = Maps.newHashMap();
		mappings.put(view.getClass(), factory);
		
		ViewAddOnFactories factories = new ViewAddOnFactories(mappings);
		
		ViewAddOn actual = factories.createViewAddOn(view);
		
		assertThat(actual, is(viewAddOn));
	}
}
