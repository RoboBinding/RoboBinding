package org.robobinding.attribute;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;

import com.google.common.collect.Lists;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class StaticResourcesAttribute extends AbstractPropertyAttribute {
	private static final Pattern RESOURCE_ATTRIBUTE_PATTERN = Pattern.compile("^@([\\w\\.]+:)?(\\w+)/(\\w+)(,@([\\w\\.]+:)?(\\w+)/(\\w+))?$");
	
	private final List<StaticResource> resources;
	
	public StaticResourcesAttribute(String name, String value) {
		super(name);
		
		if(!isStaticResourcesAttribute(value)) {
			throw new MalformedAttributeException(getName(), "Invalid resource syntax: " + value);
		}
		
		resources = Lists.newArrayList();
		String[] resourceValues = value.split(",");
		for (String resourceValue : resourceValues) {
			resources.add(new StaticResource(resourceValue.trim()));
		}
	}
	
	public List<Integer> getResourceIds(Context context) {
		List<Integer> resourceIds = Lists.newArrayList();
		for (StaticResource resource : resources) {
			resourceIds.add(resource.getResourceId(context));
		}
		return resourceIds;
	}

	@Override
	public boolean isTwoWayBinding() {
		return false;
	}
	
	@Override
	public <T> T accept(PropertyAttributeVisitor<T> visitor) {
		return visitor.visitStaticResources(this);
	}

	static boolean isStaticResourcesAttribute(String value) {
		Matcher matcher = RESOURCE_ATTRIBUTE_PATTERN.matcher(value);

		return matcher.matches();
	}
}
