package org.robobinding.widget.progressbar;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.robobinding.robolectric.TestUtil;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.res.Attribute;
import org.robolectric.res.ResName;
import org.robolectric.shadows.RoboAttributeSet;

import android.widget.ProgressBar;

import com.google.android.collect.Lists;

/**
 * @since 1.0
 * @version 
 * @author Cheng Wei
 *
 */
@RunWith(RobolectricTestRunner.class)
public abstract class AbstractProgressBarAttributeTest {
	protected ProgressBar view;
	
	@Before
	public void initializeView() {
		view = new ProgressBar(Robolectric.application, new RoboAttributeSet(
				Lists.newArrayList(
						new Attribute(new ResName(TestUtil.SYSTEM_PACKAGE, "attr", "max"), "100", TestUtil.TEST_PACKAGE), 
						new Attribute(new ResName(TestUtil.SYSTEM_PACKAGE, "attr", "indeterminate"), "false", TestUtil.TEST_PACKAGE),
				        new Attribute(new ResName(TestUtil.SYSTEM_PACKAGE, "attr", "indeterminateOnly"), "false", TestUtil.TEST_PACKAGE)),
				Robolectric.application.getResources(), null));
	}

}
