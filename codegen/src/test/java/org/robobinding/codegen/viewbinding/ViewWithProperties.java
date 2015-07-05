package org.robobinding.codegen.viewbinding;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class ViewWithProperties {
	public static final String PRIMITIVE_PROP = "primitiveProp";
	public static final String OBJECT_PROP = "objectProp";
	public static final String AMBIGUOUS_SETTER = "ambiguousSetter";
	
	public void setPrimitiveProp(int value) {
	}
	
	public void setObjectProp(Object value) {
	}
	
	public void setAmbiguousSetter(int value) {
	}
	
	public void setAmbiguousSetter(boolean value) {	
	}
}
