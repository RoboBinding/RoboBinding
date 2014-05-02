package org.robobinding.viewattribute;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class BindingAttributeValues {
    private BindingAttributeValues() {
    }
    
    public static final String DEFAULT_PROPERTY_NAME = "defaultPropertyName";
    public static final String ONE_WAY_BINDING_DEFAULT_PROPERTY_NAME = "{" + DEFAULT_PROPERTY_NAME + "}";
    public static final String TWO_WAY_BINDING_DEFAULT_PROPERTY_NAME = "$" + ONE_WAY_BINDING_DEFAULT_PROPERTY_NAME;

    public static final String PROPERTY_NAME1 = "propertyName1";
    public static final String ONE_WAY_BINDING_PROPERTY_NAME1 = "{" + PROPERTY_NAME1 + "}";
    public static final String TWO_WAY_BINDING_PROPERTY_NAME1 = "$" + ONE_WAY_BINDING_PROPERTY_NAME1;

    public static final String PROPERTY_NAME2 = "propertyName2";
    public static final String ONE_WAY_BINDING_PROPERTY_NAME2 = "{" + PROPERTY_NAME2 + "}";
    public static final String TWO_WAY_BINDING_PROPERTY_NAME2 = "$" + ONE_WAY_BINDING_PROPERTY_NAME2;

    public static final String DEFAULT_RESOURCE_PACKAGE = "android";
    public static final String LAYOUT_RESOURCE_TYPE = "layout";

    public static final String DEFAULT_LAYOUT_RESOURCE = "@layout/defaultLayout";
    public static final String DEFAULT_LAYOUT_RESOURCE_NAME = "defaultLayout";

}
