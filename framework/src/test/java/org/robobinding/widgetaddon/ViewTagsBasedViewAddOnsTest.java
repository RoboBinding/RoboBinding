package org.robobinding.widgetaddon;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.robobinding.viewattribute.ViewTag;
import org.robobinding.viewattribute.ViewTags;

import android.view.View;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ViewTagsBasedViewAddOnsTest {
	@Mock
	private ViewAddOnFactories factories;
	
	@Test
	public void givenViewAddOnNotSetOnTag_whenGetMostSuitable_thenReturnNewViewAddOn() {
		View view = mock(View.class);
		ViewAddOn newViewAddOn = mock(ViewAddOn.class);
		when(factories.createViewAddOn(view)).thenReturn(newViewAddOn);
		ViewTag<ViewAddOn> viewTag = viewTagHasValue(false);
		ViewTags<ViewAddOn> viewTags = viewTagsFor(view, viewTag);
		ViewTagsBasedViewAddOns viewAddOns = new ViewTagsBasedViewAddOns(factories, viewTags);
		
		ViewAddOn actual = viewAddOns.getMostSuitable(view);
		
		assertThat(actual, is(newViewAddOn));
	}

	private ViewTag<ViewAddOn> viewTagHasValue(boolean hasValue) {
		@SuppressWarnings("unchecked")
		ViewTag<ViewAddOn> viewTag = mock(ViewTag.class);
		when(viewTag.has()).thenReturn(hasValue);
		return viewTag;
	}
	
	private ViewTags<ViewAddOn> viewTagsFor(View view, ViewTag<ViewAddOn> viewTag) {
		@SuppressWarnings("unchecked")
		ViewTags<ViewAddOn> viewTags = mock(ViewTags.class);
		when(viewTags.tagFor(view)).thenReturn(viewTag);
		return viewTags;
	}

	@Test
	public void givenViewAddOnSetOnTag_whenGetMostSuitable_thenReturnOldViewAddOn() {
		View view = mock(View.class);
		ViewAddOn oldViewAddOn = mock(ViewAddOn.class);
		ViewTag<ViewAddOn> viewTag = viewTagHasValue(true);
		when(viewTag.get()).thenReturn(oldViewAddOn);
		ViewTags<ViewAddOn> viewTags = viewTagsFor(view, viewTag);
		ViewTagsBasedViewAddOns viewAddOns = new ViewTagsBasedViewAddOns(factories, viewTags);
		
		ViewAddOn actual = viewAddOns.getMostSuitable(view);
		
		assertThat(actual, is(oldViewAddOn));
	}
	
	@Test
	public void givenViewNotSupportTag_whenGetMostSuitable_thenReturnNewViewAddOn() {
		Object viewNotSupportTag = mock(Object.class);
		ViewAddOn newViewAddOn = mock(ViewAddOn.class);
		when(factories.createViewAddOn(viewNotSupportTag)).thenReturn(newViewAddOn);
		ViewTagsBasedViewAddOns viewAddOns = new ViewTagsBasedViewAddOns(factories, null);
		
		ViewAddOn actual = viewAddOns.getMostSuitable(viewNotSupportTag);
		
		assertThat(actual, is(newViewAddOn));
	}
}
