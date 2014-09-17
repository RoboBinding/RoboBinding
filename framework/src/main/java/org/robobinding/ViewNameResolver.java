package org.robobinding;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class ViewNameResolver {
	public String getViewNameFromLayoutTag(String tagName) {
		StringBuilder nameBuilder = new StringBuilder();

		if ("View".equals(tagName) || "ViewGroup".equals(tagName))
			nameBuilder.append("android.view.");
		else if (!viewNameIsFullyQualified(tagName))
			nameBuilder.append("android.widget.");

		nameBuilder.append(tagName);
		return nameBuilder.toString();
	}

	private boolean viewNameIsFullyQualified(String name) {
		return name.contains(".");
	}
}
