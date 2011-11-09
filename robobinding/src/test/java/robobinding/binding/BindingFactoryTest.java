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
package robobinding.binding;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import robobinding.binding.BindingAttributesLoader.ViewBindingAttributes;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 *
 */
@RunWith(RobolectricTestRunner.class)
public class BindingFactoryTest
{
	private String viewName = "View";
	private String fullyQualifiedViewName = "android.view.View";
	private String widgetName = "TextView";
	private String fullyQualifiedWidgetName = "android.widget.TextView";
	private String customName = "robobinding.widget.CustomView";
	private String fullyQualifiedCustomName = "robobinding.widget.CustomView";
	
	private AttributeSet attrs = MockAttributeSet.withAttributes(2, 2);
	private String prefix = null;
	private Context context = null;
	private View theView = new View(context);
	private ViewBindingAttributes sampleViewBindingAttributes = new ViewBindingAttributes(null);
	private LayoutInflater layoutInflater;
	private BindingAttributesLoader bindingAttributesLoader;
	private BindingViewFactory bindingViewFactory;
	
	@Before
	public void setUp() throws Exception
	{
		layoutInflater = mock(LayoutInflater.class);
		bindingAttributesLoader = mock(BindingAttributesLoader.class);
		when(bindingAttributesLoader.load(theView, attrs)).thenReturn(sampleViewBindingAttributes);
		bindingViewFactory = new BindingViewFactory(layoutInflater, bindingAttributesLoader);
	}
	
	@Test
	public void whenInitializing_ThenInitialChildViewBindingAttributesShouldBeEmpty()
	{
		assertTrue(bindingViewFactory.getChildViewBindingAttributes().isEmpty());
	}
	
	@Test
	public void whenInitializing_ThenLayoutInflaterFactoryShouldBeSetAsThis()
	{
		verify(layoutInflater).setFactory(bindingViewFactory);
	}
	
	@Test
	public void whenCreatingAViewInTheViewPackage_ThenReturnViewInflatedFromLayoutInflater() throws Exception
	{
		when(layoutInflater.createView(fullyQualifiedViewName, prefix, attrs)).thenReturn(theView);
		assertThat(bindingViewFactory.onCreateView(viewName, context, attrs), equalTo(theView));
	}
	
	@Test
	public void whenCreatingAViewInTheWidgetPackage_ThenReturnViewInflatedFromLayoutInflater() throws Exception
	{
		when(layoutInflater.createView(fullyQualifiedWidgetName, prefix, attrs)).thenReturn(theView);
		assertThat(bindingViewFactory.onCreateView(widgetName, context, attrs), equalTo(theView));
	}
	
	@Test
	public void whenCreatingACustomWidget_ThenReturnViewInflatedFromLayoutInflater() throws Exception
	{
		when(layoutInflater.createView(fullyQualifiedCustomName, prefix, attrs)).thenReturn(theView);
		assertThat(bindingViewFactory.onCreateView(customName, context, attrs), equalTo(theView));
	}
	
	@Test
	public void whenCreatingAView_ThenAddNewViewBindingAttributes() throws Exception
	{
		when(layoutInflater.createView(fullyQualifiedViewName, prefix, attrs)).thenReturn(theView);
		bindingViewFactory.onCreateView(viewName, context, attrs);
		
		List<ViewBindingAttributes> viewHierarchyBindingAttributes = bindingViewFactory.getChildViewBindingAttributes();
		assertTrue(viewHierarchyBindingAttributes.size() == 1);
		assertThat(viewHierarchyBindingAttributes.get(0), equalTo(sampleViewBindingAttributes));
	}

	@Test
	public void whenCreatingMultipleViews_ThenAddNewViewBindingAttributesForEach() throws Exception
	{
		View anotherView = new View(context);
		AttributeSet someOtherAttrs = MockAttributeSet.withAttributes(5, 5);
		
		when(layoutInflater.createView(fullyQualifiedViewName, prefix, attrs)).thenReturn(theView);
		when(layoutInflater.createView(fullyQualifiedViewName, prefix, someOtherAttrs)).thenReturn(anotherView);
		
		bindingViewFactory.onCreateView(viewName, context, attrs);
		bindingViewFactory.onCreateView(viewName, context, someOtherAttrs);
		
		assertTrue(bindingViewFactory.getChildViewBindingAttributes().size() == 2);
		verify(bindingAttributesLoader).load(theView, attrs);
		verify(bindingAttributesLoader).load(anotherView, someOtherAttrs);
	}

	@Test (expected=RuntimeException.class)
	public void whenCreatingAViewThrowsAClassNotFoundException_ThenPropagateAsRuntimeException() throws Exception
	{
		when(layoutInflater.createView(fullyQualifiedViewName, prefix, attrs)).thenThrow(new ClassNotFoundException());
		bindingViewFactory.onCreateView(viewName, context, attrs);
	}
}
