package org.robobinding.widget.view;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.robobinding.widget.seekbar.SeekBarListeners;

import android.view.View;
import android.widget.SeekBar;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@RunWith(MockitoJUnitRunner.class)
public class ViewListenersMapBuilderTest {
    @Test
    public void whenPutViewListenersForViews_thenTheMappingsAreAdded() {
	ViewListenersMapBuilder builder = new ViewListenersMapBuilder();
	builder.put(View.class, ViewListeners.class);
	builder.put(SeekBar.class, SeekBarListeners.class);

	ViewListenersMap map = builder.build();

	assertThat(map.mappings.size(), is(2));
    }

    @SuppressWarnings("rawtypes")
    @Test
    public void whenPutViewListenersForAViewSecondTime_thenKeepTheLatestMappings() {
	ViewListenersMapBuilder builder = new ViewListenersMapBuilder();
	builder.put(View.class, ViewListeners.class);
	builder.put(View.class, CustomViewListeners.class);

	ViewListenersMap map = builder.build();

	assertThat(map.mappings.size(), is(1));
	assertThat(map.getViewListenersClass(View.class), equalTo((Class) CustomViewListeners.class));
    }

    private static class CustomViewListeners extends ViewListeners {
	public CustomViewListeners(View view) {
	    super(view);
	}
    }

}
