package org.robobinding.codegen.presentationmodel;

import java.util.Set;

import org.robobinding.codegen.presentationmodel.differentpackage.CustomClass;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class DefineTryToCreateProperty implements VariousProperties {

	@Override
	public String getProp() {
		return null;
	}

	@Override
	public void setProp(String prop) {
	}

	@Override
	public int getReadOnlyProp() {
		return 0;
	}

	@Override
	public void setWriteOnlyProp(int prop) {
	}

	@Override
	public boolean isBooleanProp() {
		return false;
	}

	@Override
	public Set<Integer> getSetProp() {
		return null;
	}

	@Override
	public void setSetProp(Set<Integer> param) {
	}

	@Override
	public CustomClass getCustomClass1() {
		return null;
	}

	@Override
	public CustomClass getCustomClass2() {
		return null;
	}
	
}
