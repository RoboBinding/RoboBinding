package org.robobinding.widget.adapterview;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class EmptyViewVisibilityTest {
	private View view;
	private AdapterView<?> adapterView;
	private ViewGroup parent;

	@Before
	public void setUp() {
		view = mock(View.class);
		adapterView = mock(AdapterView.class);
		parent = mock(ViewGroup.class);
		when(adapterView.getParent()).thenReturn(parent);
	}

	@Test
	public void givenViewIsNotEmptyView_whenMakingVisible_thenViewShouldBecomeSiblingOfAdapterView() {
		EmptyViewVisibility emptyViewVisibility = new EmptyViewVisibility(adapterView, view);

		emptyViewVisibility.makeVisible();

		verify(parent).addView(view);
	}

	@Test
	public void givenViewIsNotEmptyView_whenMakingVisible_thenViewShouldBecomeEmptyView() {
		EmptyViewVisibility emptyViewVisibility = new EmptyViewVisibility(adapterView, view);

		emptyViewVisibility.makeVisible();

		verify(adapterView).setEmptyView(view);
	}

	@Test
	public void givenViewIsEmptyView_whenMakingGone_thenViewShouldBeRemovedAsSiblingOfAdapterView() {
		when(adapterView.getEmptyView()).thenReturn(view);
		EmptyViewVisibility emptyViewVisibility = new EmptyViewVisibility(adapterView, view);

		emptyViewVisibility.makeGone();

		verify(parent).removeView(view);
	}

	@Test
	public void givenViewIsEmptyView_whenMakingGone_thenEmptyViewShouldBeSetToNull() {
		when(adapterView.getEmptyView()).thenReturn(view);
		EmptyViewVisibility emptyViewVisibility = new EmptyViewVisibility(adapterView, view);

		emptyViewVisibility.makeGone();

		verify(adapterView).setEmptyView(null);
	}
}
