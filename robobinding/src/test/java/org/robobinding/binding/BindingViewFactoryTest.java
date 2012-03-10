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
package org.robobinding.binding;

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
import org.robobinding.binding.BindingAttributeProcessor.ViewBindingAttributes;
import org.robobinding.viewattribute.RandomValues;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@RunWith(RobolectricTestRunner.class)
public class BindingViewFactoryTest
{
	private String viewName = "View";
	private String fullyQualifiedViewName = "android.view.View";
	
	private AttributeSet attrs = MockAttributeSet.withAttributes(2, 2);
	private String prefix = null;
	private Context context = null;
	private View theView = new View(context);
	private ViewBindingAttributes sampleViewAttributes = new ViewBindingAttributes(null);
	private LayoutInflater layoutInflater;
	private BindingAttributeProcessor bindingAttributesProcessor;
	private BindingViewFactory bindingViewFactory;
	
	@Before
	public void setUp() throws Exception
	{
		layoutInflater = mock(LayoutInflater.class);
		bindingAttributesProcessor = mock(BindingAttributeProcessor.class);
		when(bindingAttributesProcessor.read(theView, attrs)).thenReturn(sampleViewAttributes);
		bindingViewFactory = new BindingViewFactory(layoutInflater, bindingAttributesProcessor);
	}
	
	@Test
	public void whenInitializing_thenInitialChildViewBindingAttributesShouldBeEmpty()
	{
		assertTrue(bindingViewFactory.getChildViewBindingAttributes().isEmpty());
	}
	
	@Test
	public void whenInitializing_thenLayoutInflaterFactoryShouldBeSetAsThis()
	{
		verify(layoutInflater).setFactory(bindingViewFactory);
	}
	
	@Test
	public void whenCreatingAView_thenReturnViewInflatedFromLayoutInflater() throws Exception
	{
		when(layoutInflater.createView(fullyQualifiedViewName, prefix, attrs)).thenReturn(theView);
		assertThat(bindingViewFactory.onCreateView(viewName, context, attrs), equalTo(theView));
	}
	
	@Test
	public void whenCreatingAView_thenAddNewViewBindingAttributes() throws Exception
	{
		when(layoutInflater.createView(fullyQualifiedViewName, prefix, attrs)).thenReturn(theView);
		bindingViewFactory.onCreateView(viewName, context, attrs);
		
		List<ViewBindingAttributes> viewHierarchyBindingAttributes = bindingViewFactory.getChildViewBindingAttributes();
		assertTrue(viewHierarchyBindingAttributes.size() == 1);
		assertThat(viewHierarchyBindingAttributes.get(0), equalTo(sampleViewAttributes));
	}

	@Test
	public void whenCreatingMultipleViews_thenAddNewViewBindingAttributesForEach() throws Exception
	{
		View anotherView = new View(context);
		AttributeSet someOtherAttrs = MockAttributeSet.withAttributes(5, 5);
		
		when(layoutInflater.createView(fullyQualifiedViewName, prefix, attrs)).thenReturn(theView);
		when(layoutInflater.createView(fullyQualifiedViewName, prefix, someOtherAttrs)).thenReturn(anotherView);
		
		bindingViewFactory.onCreateView(viewName, context, attrs);
		bindingViewFactory.onCreateView(viewName, context, someOtherAttrs);
		
		assertTrue(bindingViewFactory.getChildViewBindingAttributes().size() == 2);
		verify(bindingAttributesProcessor).read(theView, attrs);
		verify(bindingAttributesProcessor).read(anotherView, someOtherAttrs);
	}

	@Test
	public void whenInflatingAndAttachingToRoot_thenLayoutInflaterShouldInflateRootViewWith() throws Exception
	{
		int resourceId = RandomValues.anyInteger();
		ViewGroup viewGroup = mock(ViewGroup.class);
		
		bindingViewFactory.inflateViewAndAttachToRoot(resourceId, viewGroup);
	
		verify(layoutInflater).inflate(resourceId, viewGroup, true);
	}
	
	@Test (expected=RuntimeException.class)
	public void whenCreatingAViewThrowsAClassNotFoundException_thenPropagateAsRuntimeException() throws Exception
	{
		when(layoutInflater.createView(fullyQualifiedViewName, prefix, attrs)).thenThrow(new ClassNotFoundException());
		bindingViewFactory.onCreateView(viewName, context, attrs);
	}
}
