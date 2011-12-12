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
package robobinding.binding.viewattribute;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import robobinding.presentationmodel.PresentationModelAdapter;
import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.shadows.ShadowView;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ViewCommandAttributeTester
{
	public View view;
	public final String commandName = "someCommand";
	public ShadowView shadowView;
	
	private Context context;
	private MockFunction mockFunction;
	private PresentationModelAdapter mockPresentationModelAdapter;
	
	public ViewCommandAttributeTester()
	{
		context = new Activity();
		view = new View(context);
		
		shadowView = Robolectric.shadowOf(view);
		
		mockFunction = new MockFunction();
		
		mockPresentationModelAdapter = mock(PresentationModelAdapter.class);
		when(mockPresentationModelAdapter.findFunction(eq(commandName), (Class<?>)any())).thenReturn(mockFunction);
	}
	
	public void bindAttribute(AbstractCommandViewAttribute attribute)
	{
		attribute.bind(mockPresentationModelAdapter, context);
	}
	
	public void assertEventReceived(Class<?> eventClass)
	{
		assertTrue(mockFunction.commandInvoked);
		assertThat(mockFunction.argsPassedToInvoke[0], instanceOf(eventClass));
	}
}
