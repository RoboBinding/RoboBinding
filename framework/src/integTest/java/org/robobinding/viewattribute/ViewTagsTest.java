package org.robobinding.viewattribute;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import android.app.Activity;
import android.view.View;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class ViewTagsTest {
	private View view;
	private ViewTags<TagType> viewTags;
	
	@Before
	public void setUp() {
		view = new View(new Activity());
		
		viewTags = new ViewTags<TagType>(ViewTags.USED_KEY1);
	}
	
	@Test
	public void givenViewWithoutTagSet_whenAskForTag_thenNewTagIsSetOnView() {
		ViewTag<TagType>  tag = viewTags.tagFor(view);
		
		assertThat(tag, notNullValue());
		assertThat(view.getTag(), notNullValue());
	}
	
	@Test
	public void givenViewWithTagSet_whenAskForTagAgain_thenReuseTheOldTag() {
		viewTags.tagFor(view);
		Object oldTag = view.getTag();

		viewTags.tagFor(view);
		Object newTag = view.getTag();
		
		assertThat(newTag, sameInstance(oldTag));
	}
	
	@Test(expected=RuntimeException.class)
	public void givenViewWithUserSetTag_whenAskForTag_thenThrowError() {
		Object userDefinedTag = new Object();
		view.setTag(userDefinedTag);
		
		viewTags.tagFor(view);
	}
	
	private static class TagType {}
}
