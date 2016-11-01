package org.robobinding;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@RunWith(Theories.class)
public class ViewNameResolverTest {
	@DataPoints
	public static ViewNameMapping[] viewNameMappings = { 
		layoutTagName("View").shouldResolveTo("android.view.View"),
			layoutTagName("ViewGroup").shouldResolveTo("android.view.ViewGroup"), 
			layoutTagName("SurfaceView").shouldResolveTo("android.view.SurfaceView"), 
			layoutTagName("WebView").shouldResolveTo("android.webkit.WebView"), 
			layoutTagName("EditText").shouldResolveTo("android.widget.EditText"),
			layoutTagName("ListView").shouldResolveTo("android.widget.ListView"),
			layoutTagName("robobinding.widget.CustomWidget").shouldResolveTo("robobinding.widget.CustomWidget") };

	@Theory
	public void shouldResolveViewNamesCorrectly(ViewNameMapping viewNameMapping) {
		ViewNameResolver viewNameResolver = new ViewNameResolver();
		String viewName = viewNameResolver.getViewNameFromLayoutTag(viewNameMapping.tagName);
		assertThat(viewName, equalTo(viewNameMapping.expectedMapping));
	}

	private static class ViewNameMapping {
		private final String tagName;
		private final String expectedMapping;

		public ViewNameMapping(String tagName, String expectedMapping) {
			this.tagName = tagName;
			this.expectedMapping = expectedMapping;
		}
	}

	private static LayoutTagName layoutTagName(String tagName) {
		return new LayoutTagName(tagName);
	}

	private static class LayoutTagName {
		private final String tagName;

		public LayoutTagName(String tagName) {
			this.tagName = tagName;
		}

		public ViewNameMapping shouldResolveTo(String mapping) {
			return new ViewNameMapping(tagName, mapping);
		}

	}

}
