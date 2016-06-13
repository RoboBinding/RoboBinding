package org.robobinding.attribute;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.robobinding.util.Strings;

import android.content.Context;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class StaticResource {
	public static final int RESOURCE_ID_NOT_EXIST = 0;
	
	static final String PATTERN = "@([\\w\\.]+:)?(\\w+)/(\\w+)";
	private static final Pattern RESOURCE_ATTRIBUTE_PATTERN = Pattern.compile("^"+PATTERN+"$");

	String resourceName;
	String resourceType;
	String resourcePackage;

	StaticResource(String value) {
		determineResourceNameAndType(value);
	}

	private void determineResourceNameAndType(String value) {
		Matcher matcher = RESOURCE_ATTRIBUTE_PATTERN.matcher(value);

		matcher.find();
		if (!matcher.matches())
			throw new IllegalArgumentException("Invalid resource syntax: " + value);
		
		resourcePackage = matcher.group(1);
		if (hasResourcePackage())
			resourcePackage = resourcePackage.substring(0, resourcePackage.length() - 1);
		
		resourceType = matcher.group(2);
		resourceName = matcher.group(3);
	}
	
	private boolean hasResourcePackage() {
		return !Strings.isNullOrEmpty(resourcePackage);
	}

	public int getResourceId(Context context) {
		int resourceId = context.getResources().getIdentifier(
				resourceName, resourceType, hasResourcePackage() ? resourcePackage : context.getPackageName());

		checkResource(resourceId);

		return resourceId;
	}

	private void checkResource(int resourceId) {
		if (resourceId == RESOURCE_ID_NOT_EXIST)
			throw new RuntimeException("No such resource was found for " + toString());
	}
	
	@Override
	public String toString() {
		if(hasResourcePackage()) {
			return "@" + resourcePackage + ":" + resourceType + "/" + resourceName;
		} else {
			return "@" + resourceType + "/" + resourceName;
		}
	}
	
	static boolean isStaticResource(String value) {
		Matcher matcher = RESOURCE_ATTRIBUTE_PATTERN.matcher(value);

		return matcher.matches();
	}
}
