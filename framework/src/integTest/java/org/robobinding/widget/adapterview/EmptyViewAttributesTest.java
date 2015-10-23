package org.robobinding.widget.adapterview;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class EmptyViewAttributesTest {
	private EmptyViewAttributes emptyViewAttributes = new EmptyViewAttributes();
	private Context context = RuntimeEnvironment.application;
	private AdapterView<?> adapterView = new ListView(context);
	private View emptyView = new View(context);

	@Test
	public void testLayoutAttribute() {
		assertThat(emptyViewAttributes.layoutAttribute(), is("emptyViewLayout"));
	}

	@Test
	public void testSubViewPresentationModelAttribute() {
		assertThat(emptyViewAttributes.subViewPresentationModelAttribute(), is("emptyViewPresentationModel"));
	}

	@Test
	public void testVisibilityAttribute() {
		assertThat(emptyViewAttributes.visibilityAttribute(), is("emptyViewVisibility"));
	}

	@Test
	public void givenAddSubViewHasBeenCalled_thenReturnEmptyViewVisibility() {
		ViewGroup parentViewGroup = new LinearLayout(context);
		parentViewGroup.addView(adapterView);

		emptyViewAttributes.addSubView(adapterView, emptyView, context);

		assertNotNull(emptyViewAttributes.createVisibility(adapterView, emptyView));
	}

	@Test(expected = IllegalStateException.class)
	public void givenSubViewHasNotBeenAdded_thenThrowIllegalStateException() {
		emptyViewAttributes.createVisibility(adapterView, emptyView);
	}
}
