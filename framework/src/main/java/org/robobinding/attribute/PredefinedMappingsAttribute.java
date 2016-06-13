package org.robobinding.attribute;

import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.robobinding.PendingAttributesForView;
import org.robobinding.PendingAttributesForViewImpl;
import org.robobinding.PredefinedPendingAttributesForView;
import org.robobinding.util.Lists;
import org.robobinding.util.Maps;
import org.robobinding.util.Objects;

import android.content.Context;
import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class PredefinedMappingsAttribute extends AbstractAttribute {
	private static final String MAPPING_PATTERN = "(\\w+)\\.(\\w+):($?\\{\\w+\\})";
	private static final Pattern MAPPING_COMPILED_PATTERN = Pattern.compile(MAPPING_PATTERN);
	private static final Pattern MAPPING_ATTRIBUTE_COMPILED_PATTERN = Pattern.compile("^\\[" + MAPPING_PATTERN + "(?:(,\\s?)" + MAPPING_PATTERN + ")*\\]$");
	private final String attributeValue;

	public PredefinedMappingsAttribute(String name, String value) {
		super(name);
		this.attributeValue = value;

		if (!MAPPING_ATTRIBUTE_COMPILED_PATTERN.matcher(value).matches())
			throw new MalformedAttributeException(getName(), "Mapping attribute value: " + value + " contains invalid syntax.");
	}

	public Collection<PredefinedPendingAttributesForView> getViewMappings(Context context) {
		return new ItemMappingParser().parse(getName(), attributeValue, context).getPredefinedPendingAttributesForViewGroup();
	}

	private static class ItemMappingParser {
		public ViewMappings parse(String name, String value, Context context) {
			Matcher matcher = MAPPING_COMPILED_PATTERN.matcher(value);
			ViewMappings viewMappings = new ViewMappings();

			while (matcher.find()) {
				String viewIdString = matcher.group(1);
				String nestedAttributeName = matcher.group(2);
				String nestedAttributeValue = matcher.group(3);

				int viewId = context.getResources().getIdentifier(viewIdString, "id", "android");

				if (viewId == 0)
					throw new MalformedAttributeException(name, "View with id name: " + viewIdString + " in package: android could not be found");

				viewMappings.add(viewId, nestedAttributeName, nestedAttributeValue);
			}

			return viewMappings;
		}
	}

	private static class ViewMappings {
		private Map<Integer, ViewMapping> viewMappingsMap = Maps.newHashMap();

		void add(int viewId, String attributeName, String attributeValue) {
			ViewMapping existingViewMapping = viewMappingsMap.get(viewId);

			if (existingViewMapping != null) {
				existingViewMapping.add(attributeName, attributeValue);
			} else {
				viewMappingsMap.put(viewId, new ViewMapping(viewId, attributeName, attributeValue));
			}
		}

		public Collection<PredefinedPendingAttributesForView> getPredefinedPendingAttributesForViewGroup() {
			return Lists.<PredefinedPendingAttributesForView> newArrayList(viewMappingsMap.values());
		}
	}

	static class ViewMapping implements PredefinedPendingAttributesForView {
		int viewId;
		Map<String, String> bindingAttributes = Maps.newHashMap();

		public ViewMapping(int viewId, String attributeName, String attributeValue) {
			this.viewId = viewId;
			this.add(attributeName, attributeValue);
		}

		public void add(String attributeName, String attributeValue) {
			this.bindingAttributes.put(attributeName, attributeValue);
		}

		@Override
		public PendingAttributesForView createPendingAttributesForView(View rootView) {
			View childView = rootView.findViewById(viewId);
			if(childView == null) {
				String resName = rootView.getResources().getResourceName(viewId);
				throw new RuntimeException("predefined mapping viewId '"+resName+"' cannot be found");
			}
			
			return new PendingAttributesForViewImpl(childView, bindingAttributes);
		}

		@Override
		public boolean equals(Object other) {
			if (this == other)
				return true;
			if (!(other instanceof ViewMapping))
				return false;

			final ViewMapping that = (ViewMapping) other;
			return Objects.equal(bindingAttributes, that.bindingAttributes) && Objects.equal(viewId, that.viewId);
		}

		@Override
		public int hashCode() {
			return Objects.hashCode(bindingAttributes, viewId);
		}
	}

	public static PredefinedMappingsAttribute nullAttribute(String attributeName) {
		return new PredefinedMappingsAttribute(attributeName, "[text1.text:{property}]") {
			@Override
			public Collection<PredefinedPendingAttributesForView> getViewMappings(Context context) {
				return Lists.newArrayList();
			}
		};
	}
}
