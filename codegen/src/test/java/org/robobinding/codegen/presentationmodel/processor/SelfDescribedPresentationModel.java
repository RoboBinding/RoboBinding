package org.robobinding.codegen.presentationmodel.processor;

import java.util.Set;

import org.robobinding.annotation.PresentationModel;
import org.robobinding.codegen.presentationmodel.DefineTryToCreateDataSetProperty;
import org.robobinding.codegen.presentationmodel.VariousEventMethods;
import org.robobinding.codegen.presentationmodel.VariousProperties;
import org.robobinding.codegen.presentationmodel.differentpackage.CustomClass;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
@PresentationModel
public class SelfDescribedPresentationModel extends DefineTryToCreateDataSetProperty
	implements VariousProperties, VariousEventMethods  {

	@Override
	public void eventMethod() {
	}

	@Override
	public void eventMethodWithArg(Object event) {
	}

	@Override
	public int eventMethodWithReturn() {
		return 0;
	}

	@Override
	public int eventMethodWithReturnAndArg(Object event) {
		return 0;
	}

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
