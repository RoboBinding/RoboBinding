package org.robobinding.widget.listview;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@RunWith(RobolectricTestRunner.class)
public class HeaderAttributesTest {
    private HeaderAttributes headerAttributes = new HeaderAttributes();
    private Context context = new Activity();
    private ListView listView = new ListView(context);
    private View subView = new View(context);

    @Test
    public void testLayoutAttribute() {
	assertThat(headerAttributes.layoutAttribute(), is("headerLayout"));
    }

    @Test
    public void testSubViewPresentationModelAttribute() {
	assertThat(headerAttributes.subViewPresentationModelAttribute(), is("headerPresentationModel"));
    }

    @Test
    public void testVisibilityAttribute() {
	assertThat(headerAttributes.visibilityAttribute(), is("headerVisibility"));
    }

    @Test
    public void shouldNestSubViewWithinLinearLayoutContainerAndAddAsHeader() {
	headerAttributes.addSubView(listView, subView, context);

	List<View> headerViews = Robolectric.shadowOf(listView).getHeaderViews();
	assertThat(headerViews.size(), is(1));
	LinearLayout linearLayout = (LinearLayout) headerViews.get(0);
	assertThat(linearLayout.getChildAt(0), is(subView));
    }

    @Test
    public void shouldCreateNewHeaderVisibility() {
	assertNotNull(headerAttributes.createVisibility(listView, subView));
    }
}
