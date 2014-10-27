package org.robobinding.widget.listview;

import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class ListViewListenersTest {
	/**
	 * TODO: Not supported by Robolectric 2.x yet.
	 */
	@Test
	@Ignore
	public void shouldSupportMultipleOnOnScrollListeners() {
		ListView view = new ListView(Robolectric.application);
		ListViewListeners viewListeners = new ListViewListeners(view);

		MockOnScrollListener listener1 = new MockOnScrollListener();
		MockOnScrollListener listener2 = new MockOnScrollListener();

		viewListeners.addOnScrollListener(listener1);
		viewListeners.addOnScrollListener(listener2);

		view.smoothScrollToPositionFromTop(1, 0, 500);

		assertTrue(listener1.scrollEventFired);
		assertTrue(listener1.scrollStateChangedEventFired);
		assertTrue(listener2.scrollEventFired);
		assertTrue(listener2.scrollStateChangedEventFired);
	}

	private static class MockOnScrollListener implements OnScrollListener {
		private boolean scrollEventFired;
		private boolean scrollStateChangedEventFired;

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
			scrollEventFired = true;
		}

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			scrollStateChangedEventFired = true;
		}
	}

}
