package org.robobinding.codegen.presentationmodel;

import java.util.Set;

import org.robobinding.codegen.presentationmodel.differentpackage.CustomClass;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public interface VariousProperties {
	int numProperties = 7;
	
	String getProp();

	void setProp(String prop);
	
	int getReadOnlyProp();

	void setWriteOnlyProp(int prop);
	
	boolean isBooleanProp();
	
	Set<Integer> getSetProp();
	
	void setSetProp(Set<Integer> param);

	CustomClass getCustomClass1();

	CustomClass getCustomClass2();
}
