package org.robobinding.supportwidget.swiperefreshlayout;

import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
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
@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class SwipeRefreshAddOnTest {
	private SwipeRefreshLayout view;
	private SwipeRefreshLayoutAddOn viewListeners;

	@Mock
	OnRefreshListener listener1;
	
	@Mock
	OnRefreshListener listener2;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		view = new SwipeRefreshLayout(RuntimeEnvironment.application);
		viewListeners = new SwipeRefreshLayoutAddOn(view);
	}

	@Test
	public void shouldSupportMultipleOnRefreshListeners() throws Exception {
		viewListeners.addOnRefreshListener(listener1);
		viewListeners.addOnRefreshListener(listener2);
		getOnRefreshListener().onRefresh();
		Mockito.verify(listener1, Mockito.times(1)).onRefresh();
		Mockito.verify(listener2, Mockito.times(1)).onRefresh();
	}

	// there is no way to trigger OnRefreshListener.onRefresh() in
	// SwipeRefreshLayout programmatically, use reflection to call.
	private OnRefreshListener getOnRefreshListener() throws Exception {
		Field f = view.getClass().getDeclaredField("mListener");
		f.setAccessible(true);
		OnRefreshListener onRefreshListener = (OnRefreshListener) f.get(view);
		return onRefreshListener;
	}
}
