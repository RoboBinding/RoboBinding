package org.robobinding.attribute;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class StaticResource {
	public static final int RESOURCE_ID_NOT_EXIST = 0;
	
	static final Pattern RESOURCE_ATTRIBUTE_PATTERN = Pattern.compile("^@([\\w\\.]+:)?(\\w+)/(\\w+)$");

	String resourceName;
	String resourceType;
	String resourcePackage;

	StaticResource(String value) {
		determineResourceNameAndType(value);
	}

	private void determineResourceNameAndType(String value) {
		Matcher matcher = RESOURCE_ATTRIBUTE_PATTERN.matcher(value);

		if (!matcher.matches())
			throw new IllegalArgumentException("Invalid resource syntax: " + value);

		matcher.find();
		
		resourcePackage = matcher.group(1);
		resourceType = matcher.group(2);
		resourceName = matcher.group(3);
	}

	public int getResourceId(Context context) {
		int resourceId = context.getResources().getIdentifier(
				resourceName, resourceType, (resourcePackage == null) ? context.getPackageName() : resourcePackage);

		checkResource(resourceId);

		return resourceId;
	}

	private void checkResource(int resourceId) {
		if (resourceId == RESOURCE_ID_NOT_EXIST)
			throw new RuntimeException("No such resource was found");
	}
	
	static boolean isStaticResourceAttribute(String value) {
		Matcher matcher = RESOURCE_ATTRIBUTE_PATTERN.matcher(value);

		return matcher.matches();
	}
}
