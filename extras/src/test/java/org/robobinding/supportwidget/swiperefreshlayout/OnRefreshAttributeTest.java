package org.robobinding.supportwidget.swiperefreshlayout;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robobinding.widget.EventCommand;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;

/**
 * 
 * @since
 * @version
 * @author Liang Song
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE, shadows = {ShadowSwipeRefreshLayout.class})
public class OnRefreshAttributeTest {

	private SwipeRefreshLayout view;
	private OnRefreshAttribute attribute;
	private EventCommand eventCommand;
	
	@Before
	public void setUp() throws Exception {
		view = new SwipeRefreshLayout(RuntimeEnvironment.application);
		attribute = new OnRefreshAttribute();
		eventCommand = new EventCommand();
	}
	
	@Test
	public void givenBoundAttribute_whenPerformOnRefresh_thenEventReceived() throws Exception{
		bindAttribute(new SwipeRefreshLayoutAddOn(view));
		performOnRefresh();
		assertEventReceived();
	}

	private void performOnRefresh() throws Exception{
		ShadowSwipeRefreshLayout shadowView = (ShadowSwipeRefreshLayout)Shadows.shadowOf(view);
		shadowView.getOnRefreshListener().onRefresh();
	}
	
	private void bindAttribute(SwipeRefreshLayoutAddOn viewAddOn) {
		attribute.bind(viewAddOn, eventCommand, view);
	}
	
	private void assertEventReceived() {
		eventCommand.assertEventReceived(RefreshEvent.class);
		RefreshEvent refreshEvent = eventCommand.getEventReceived();
		assertTrue(refreshEvent.getView() == view);
	}
	
	@Test
	public void whenBinding_thenRegisterWithViewListeners() {
		SwipeRefreshLayoutAddOn viewAddOn = mock(SwipeRefreshLayoutAddOn.class);
		bindAttribute(viewAddOn);
		
		verify(viewAddOn, times(1)).addOnRefreshListener(any(OnRefreshListener.class));
	}
}
