package org.robobinding.widget.listview;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robobinding.widget.abslistview.SingleChoiceAdapter;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;

import android.content.Context;
import android.view.View;
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
public class HeaderAttributesTest {
	private HeaderAttributes headerAttributes = new HeaderAttributes();

	@Test
	public void shouldNestSubViewWithinLinearLayoutContainerAndAddAsHeader() {
		Context context = RuntimeEnvironment.application;
		ListView listView = new ListView(context);
		View subView = new View(context);

		headerAttributes.addSubView(listView, subView, context);
		listView.setAdapter(new SingleChoiceAdapter(context));

		List<View> headerViews = Shadows.shadowOf(listView).getHeaderViews();
		assertThat(headerViews.size(), is(1));
		LinearLayout linearLayout = (LinearLayout) headerViews.get(0);
		assertThat(linearLayout.getChildAt(0), is(subView));
	}
}
