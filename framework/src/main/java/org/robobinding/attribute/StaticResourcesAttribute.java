package org.robobinding.attribute;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.robobinding.util.Lists;

import android.content.Context;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class StaticResourcesAttribute extends AbstractPropertyAttribute {
	private static final Pattern RESOURCES_ATTRIBUTE_PATTERN = 
			Pattern.compile("^\\[" + StaticResource.PATTERN + "(,\\s?" + StaticResource.PATTERN + ")*\\]$");

	private final List<StaticResource> resources;
	
	public StaticResourcesAttribute(String name, String value) {
		super(name);
		
		if(!isStaticResourcesAttribute(value)) {
			throw new MalformedAttributeException(getName(), "Invalid resource syntax: " + value);
		}
		
		resources = Lists.newArrayList();
		
		String[] resourceValues = value.substring(1, value.length()-1).split(",");
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
		Matcher matcher = RESOURCES_ATTRIBUTE_PATTERN.matcher(value);

		return matcher.matches();
	}
}
