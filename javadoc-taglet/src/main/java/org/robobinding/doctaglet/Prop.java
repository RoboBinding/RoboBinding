package org.robobinding.doctaglet;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class Prop {
    private String name;
    private String supportedTypes;
    private String supportedBindingTypes;

    public Prop(String name, String supportedTypes, String supportedBindingTypes) {
	super();
	this.name = name;
	this.supportedTypes = supportedTypes;
	this.supportedBindingTypes = supportedBindingTypes;
    }

    public String getName() {
	return name;
    }

    public String getSupportedTypes() {
	return supportedTypes;
    }

    public String getSupportedBindingTypes() {
	return supportedBindingTypes;
    }
}
