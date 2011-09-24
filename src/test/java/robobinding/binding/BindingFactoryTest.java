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
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.Context;
import android.util.AttributeSet;
import android.view.InflateException;
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
	private LayoutInflater layoutInflater;
	private String name = "name";
	private AttributeSet attrs = MockAttributeSet.withAttributes(2, 2);
	private String prefix = null;
	private Context context = null;
	private View theView = new View(context);
	private BindingFactory bindingFactory;
	
	@Before
	public void setUp() throws Exception
	{
		layoutInflater = mock(LayoutInflater.class);
		bindingFactory = new BindingFactory(layoutInflater);
	}
	
	@Test
	public void whenCreatingABindingFactory_ThenShouldHaveAnEmptyViewBindingsMap()
	{
		assertTrue(bindingFactory.getViewBindingsMap().isEmpty());
	}
	
	@Test
	public void whenCreatingAView_ThenReturnViewInflatedFromLayoutInflater() throws Exception
	{
		when(layoutInflater.createView(name, prefix, attrs)).thenReturn(theView);
		assertThat(bindingFactory.onCreateView(name, context, attrs), equalTo(theView));
	}
	
	@Test
	public void whenCreatingAView_ThenAddViewToViewBindingsMap() throws Exception
	{
		when(layoutInflater.createView(name, prefix, attrs)).thenReturn(theView);
		bindingFactory.onCreateView(name, context, attrs);
		assertTrue(bindingFactory.getViewBindingsMap().containsKey(theView));
	}

	@Test
	public void whenCreatingAView_ThenAssociateBindingMapFromAttributeSetWithView() throws Exception
	{
		when(layoutInflater.createView(name, prefix, attrs)).thenReturn(theView);
		bindingFactory.onCreateView(name, context, attrs);
		assertThat(bindingFactory.getViewBindingsMap().get(theView), equalTo(BindingMap.createFrom(attrs)));
	}
	
	@Test
	public void whenCreatingMultipleViews_ThenAddEachOneToTheViewBindingsMap() throws Exception
	{
		View anotherView = new View(context);
		AttributeSet someOtherAttrs = MockAttributeSet.withAttributes(5, 5);
		
		when(layoutInflater.createView(name, prefix, attrs)).thenReturn(theView);
		when(layoutInflater.createView(name, prefix, someOtherAttrs)).thenReturn(anotherView);
		
		bindingFactory.onCreateView(name, context, attrs);
		bindingFactory.onCreateView(name, context, someOtherAttrs);
		
		assertViewBindingsMapContainsEntriesFor(theView, attrs);
		assertViewBindingsMapContainsEntriesFor(anotherView, someOtherAttrs);
	}

	@Test (expected=RuntimeException.class)
	public void whenCreatingAViewThrowsAnInflateException_ThenPropagateAsRuntimeException() throws Exception
	{
		when(layoutInflater.createView(name, prefix, attrs)).thenThrow(new InflateException());
		bindingFactory.onCreateView(name, context, attrs);
	}
	
	@Test (expected=RuntimeException.class)
	public void whenCreatingAViewThrowsAClassNotFoundException_ThenPropagateAsRuntimeException() throws Exception
	{
		when(layoutInflater.createView(name, prefix, attrs)).thenThrow(new ClassNotFoundException());
		bindingFactory.onCreateView(name, context, attrs);
	}
	
	private void assertViewBindingsMapContainsEntriesFor(View view, AttributeSet attrs)
	{
		assertTrue(bindingFactory.getViewBindingsMap().containsKey(view));
		assertThat(bindingFactory.getViewBindingsMap().get(view), equalTo(BindingMap.createFrom(attrs)));
	}
}
