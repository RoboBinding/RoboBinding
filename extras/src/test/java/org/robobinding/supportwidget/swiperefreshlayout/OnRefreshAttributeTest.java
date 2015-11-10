package org.robobinding.supportwidget.swiperefreshlayout;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robobinding.widget.EventCommand;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
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
@Config(manifest = Config.NONE)
public class OnRefreshAttributeTest {

	private SwipeRefreshLayout view;
	private OnRefreshAttribute attribute;
	private EventCommand eventCommand;
	private SwipeRefreshLayoutAddOn viewAddOn;
	
	@Before
	public void setUp() throws Exception {
		//MockitoAnnotations.initMocks(this);
		view = new SwipeRefreshLayout(RuntimeEnvironment.application);
		attribute = new OnRefreshAttribute();
		eventCommand = new EventCommand();
		viewAddOn = new SwipeRefreshLayoutAddOn(view);
	}
	
	@Test
	public void givenBoundAttribute_whenPerformOnRefresh_thenEventReceived() throws Exception{
		bindAttribute();
		performOnRefresh();
		assertEventReceived();
	}

	/*@Test
	public void whenBinding_thenRegisterWithViewListeners() {
		bindAttribute();
		Mockito.when(viewAddOn.addOnRefreshListener(Mockito.any(OnRefreshListener.class))).;
		Mockito.verify(viewAddOn, Mockito.times(1)).addOnRefreshListener(Mockito.any());
	}*/
	private void performOnRefresh() throws Exception{
		getOnRefreshListener().onRefresh();
	}
	
	private void bindAttribute() {
		attribute.bind(viewAddOn, eventCommand, view);
	}
	
	private void assertEventReceived() {
		eventCommand.assertEventReceived(RefreshEvent.class);
		RefreshEvent refreshEvent = eventCommand.getEventReceived();
		assertTrue(refreshEvent.getView() == view);
	}
	
	private OnRefreshListener getOnRefreshListener() throws Exception {
		Field f = view.getClass().getDeclaredField("mListener");
		f.setAccessible(true);
		OnRefreshListener onRefreshListener = (OnRefreshListener) f.get(view);
		return onRefreshListener;
	}
}
