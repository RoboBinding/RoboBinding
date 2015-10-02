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
import android.widget.ListView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class FooterAttributesTest {
	private FooterAttributes footerAttributes = new FooterAttributes();
	private Context context = RuntimeEnvironment.application;
	private ListView listView = new ListView(context);
	private View subView = new View(context);

	@Test
	public void shouldAddSubViewToListView() {
		footerAttributes.addSubView(listView, subView, context);
		listView.setAdapter(new SingleChoiceAdapter(context));

		List<View> footerViews = Shadows.shadowOf(listView).getFooterViews();
		assertThat(footerViews.size(), is(1));
		assertThat(footerViews.get(0), is(subView));
	}
}
