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

    public static CommandAttribute aCommandAttribute(String value) {
	return new CommandAttribute("name", value);
    }

    public static StaticResourceAttribute aStaticResourceAttribute(String value) {
	return new StaticResourceAttribute("name", value);
    }
}
