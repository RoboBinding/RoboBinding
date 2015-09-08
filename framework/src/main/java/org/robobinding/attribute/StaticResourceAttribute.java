package org.robobinding.attribute;

import java.util.regex.Matcher;

import android.content.Context;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public class StaticResourceAttribute extends AbstractPropertyAttribute {
	private final StaticResource resource;
	
	StaticResourceAttribute(String name, String value) {
		super(name);
		
		resource = new StaticResource(value);
	}


	public int getResourceId(Context context) {
		return resource.getResourceId(context);
	}

	@Override
	public boolean isTwoWayBinding() {
		return false;
	}
	
	@Override
	public <T> T accept(PropertyAttributeVisitor<T> visitor) {
		return visitor.visitStaticResource(this);
	}

	static boolean isStaticResourceAttribute(String value) {
		Matcher matcher = StaticResource.RESOURCE_ATTRIBUTE_PATTERN.matcher(value);

		return matcher.matches();
	}
}
