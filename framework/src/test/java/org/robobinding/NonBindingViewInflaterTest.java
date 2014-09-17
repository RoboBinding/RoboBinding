package org.robobinding;

import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@RunWith(MockitoJUnitRunner.class)
public class NonBindingViewInflaterTest {
	@Mock
	private LayoutInflater layoutInflater;
	private int layoutId = 0;

	@Test
	public void whenInflateView_thenResultViewShouldBeReturned() {
		View resultView = mock(View.class);
		when(layoutInflater.inflate(layoutId, null)).thenReturn(resultView);
		NonBindingViewInflater viewInflater = new NonBindingViewInflater(layoutInflater);

		View view = viewInflater.inflate(layoutId);

		assertThat(view, sameInstance(resultView));
	}

	@Test
	public void givenAttachToRoot_whenInflateView_thenResultViewWithAttachingToRootShouldBeReturned() {
		ViewGroup rootView = mock(ViewGroup.class);
		View resultViewWithAttachingToRoot = mock(View.class);
		when(layoutInflater.inflate(layoutId, rootView, true)).thenReturn(resultViewWithAttachingToRoot);
		NonBindingViewInflater viewInflater = new NonBindingViewInflater(layoutInflater);

		View view = viewInflater.inflate(layoutId, rootView);

		assertThat(view, sameInstance(resultViewWithAttachingToRoot));
	}
}
