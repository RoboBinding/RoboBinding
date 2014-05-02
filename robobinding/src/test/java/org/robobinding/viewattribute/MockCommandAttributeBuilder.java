package org.robobinding.viewattribute;

import org.robobinding.attribute.CommandAttribute;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class MockCommandAttributeBuilder extends AbstractAttributeBuilder<CommandAttribute> {
    private MockCommandAttributeBuilder() {
	super(CommandAttribute.class);
    }

    public static CommandAttribute aCommandAttribute() {
	MockCommandAttributeBuilder builder = new MockCommandAttributeBuilder();
	return builder.attribute;
    }
}
