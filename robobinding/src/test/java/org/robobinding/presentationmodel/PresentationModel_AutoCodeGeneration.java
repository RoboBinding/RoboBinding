package org.robobinding.presentationmodel;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@PresentationModel
public class PresentationModel_AutoCodeGeneration {
    public static final String PROPERTY = "property";
    public static final String CUSTOM_PROPERTY = "customProperty";
    public static final String PROPERTY_WITH_RETURN_VALUE = "propertyWithReturnValue";
    public static final String PROPERTY_WITH_MULTIPLE_PARAMETERS = "propertyWithMultipleParameters";

    public void setProperty(boolean b) {
    }

    @CustomSetter
    public void setCustomProperty(boolean b) {
    }

    public boolean setPropertyWithReturnValue(boolean b) {
	return true;
    }

    public void setPropertyWithMultipleParameters(boolean p1, String p2) {

    }
}
