package org.robobinding;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.Activity;
import android.view.View;

import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@RunWith(RobolectricTestRunner.class)
public class ActivityBinderTest {
    private Activity activity;
    private BinderImplementor binderImplementor;
    private Object presentationModel;
    private int layoutId;

    @Before
    public void setUp() {
	activity = mock(Activity.class);
	binderImplementor = mock(BinderImplementor.class);
	presentationModel = new Object();
	layoutId = 1;
    }

    @Test
    public void whenInflateAndBind_thenContentViewShouldBeSetToResultView() {
	View resultView = mock(View.class);
	when(binderImplementor.inflateAndBind(layoutId, presentationModel)).thenReturn(resultView);

	inflateAndBind();

	verify(activity).setContentView(resultView);
    }

    private void inflateAndBind() {
	ActivityBinder activityBinder = new ActivityBinder(activity, binderImplementor);
	activityBinder.inflateAndBind(layoutId, presentationModel);
    }
}
