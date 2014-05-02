package org.robobinding.attribute;


/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class MockCommandAttributeBuilder extends AbstractMockAttributeBuilder<CommandAttribute> {
    private MockCommandAttributeBuilder() {
	super(CommandAttribute.class);
    }

    public static CommandAttribute aCommandAttribute() {
	MockCommandAttributeBuilder builder = new MockCommandAttributeBuilder();
	return builder.attribute;
    }
}
