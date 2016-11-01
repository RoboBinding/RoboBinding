package org.robobinding;

import org.robobinding.util.Strings;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class ViewNameResolver {
	public String getViewNameFromLayoutTag(String tagName) {
		StringBuilder nameBuilder = new StringBuilder();

		if (Strings.equalsAny(tagName, "View", "ViewGroup", "SurfaceView")) {
			nameBuilder.append("android.view.");
		} else if("WebView".equals(tagName)) {
			nameBuilder.append("android.webkit.");
		}else if (!viewNameIsFullyQualified(tagName)){
			nameBuilder.append("android.widget.");
		}

		nameBuilder.append(tagName);
		return nameBuilder.toString();
	}

	private boolean viewNameIsFullyQualified(String name) {
		return name.contains(".");
	}
}
