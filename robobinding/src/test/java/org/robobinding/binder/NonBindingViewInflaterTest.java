/**
 * Copyright 2012 Cheng Wei, Robert Taylor
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

import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class NonBindingViewInflaterTest {
    private LayoutInflater layoutInflater;
    private int layoutId = 0;

    @Before
    public void setUp() {
	layoutInflater = mock(LayoutInflater.class);
    }

    @Test
    public void whenInflateView_thenResultViewShouldBeReturned() {
	View resultView = mock(View.class);
	when(layoutInflater.inflate(layoutId, null)).thenReturn(resultView);

	View view = inflateView();

	assertThat(view, sameInstance(resultView));
    }

    @Test
    public void givenAttachToParentView_whenInflateView_thenResultViewWithAttachingToParentViewShouldBeReturned() {
	ViewGroup parentView = mock(ViewGroup.class);
	View resultViewWithAttachingToParentView = mock(View.class);
	when(layoutInflater.inflate(layoutId, parentView, true)).thenReturn(resultViewWithAttachingToParentView);

	View view = inflateViewAndAttachTo(parentView);

	assertThat(view, sameInstance(resultViewWithAttachingToParentView));
    }

    private View inflateView() {
	return inflateViewAndAttachTo(null);
    }

    private View inflateViewAndAttachTo(ViewGroup parentView) {
	NonBindingViewInflater viewInflater = new NonBindingViewInflater(layoutInflater, parentView);
	View view = viewInflater.inflateView(layoutId);
	return view;
    }
}
