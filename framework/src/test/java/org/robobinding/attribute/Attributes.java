package org.robobinding.attribute;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class Attributes {
	private Attributes() {
	}

	public static ValueModelAttribute aValueModelAttribute(String value) {
		return new ValueModelAttribute("name", value);
	}

	public static EventAttribute anEventAttribute(String value) {
		return new EventAttribute("name", value);
	}

	public static StaticResourceAttribute aStaticResourceAttribute(String value) {
		return new StaticResourceAttribute("name", value);
	}

	public static StaticResourcesAttribute aStaticResourcesAttribute(String value) {
		return new StaticResourcesAttribute("name", value);
	}
}
