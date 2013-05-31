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

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robobinding.binder.ViewFactory.ViewFactoryListener;

import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@RunWith(RobolectricTestRunner.class)
public class ViewFactoryTest {
    private LayoutInflater layoutInflater;
    private ViewFactory viewFactory;

    @Before
    public void setUp() {
	layoutInflater = mock(LayoutInflater.class);
	viewFactory = new ViewFactoryForTest(layoutInflater);
    }

    @Test
    public void whenInitialize_thenViewFactoryShouldBeSetOnLayoutInflater() {
	verify(layoutInflater).setFactory(viewFactory);
    }

    @Test(expected = RuntimeException.class)
    public void whenCreateAViewThrowsAClassNotFoundException_thenPropagateAsRuntimeException() throws ClassNotFoundException {
	when(layoutInflater.createView(anyString(), anyString(), any(AttributeSet.class))).thenThrow(new ClassNotFoundException());
	viewFactory.onCreateView(null, null, null);
    }

    @Test
    public void givenViewFactoryListenerRegistered_whenCreateView_thenListenerShouldBeCorrectlyNotified() throws ClassNotFoundException {
	View view = mock(View.class);
	when(layoutInflater.createView(anyString(), anyString(), any(AttributeSet.class))).thenReturn(view);
	AttributeSet attributeSet = mock(AttributeSet.class);
	ViewFactoryListener listener = mock(ViewFactoryListener.class);
	viewFactory.setListener(listener);

	viewFactory.onCreateView(null, null, attributeSet);

	verify(listener).onViewCreated(same(view), same(attributeSet));
    }

    private static class ViewFactoryForTest extends ViewFactory {
	public ViewFactoryForTest(LayoutInflater layoutInflater) {
	    super(layoutInflater);
	    viewNameResolver = mock(ViewNameResolver.class);
	}
    }
}
