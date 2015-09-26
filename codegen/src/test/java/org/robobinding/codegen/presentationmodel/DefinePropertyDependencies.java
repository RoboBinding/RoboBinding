package org.robobinding.codegen.presentationmodel;

import org.robobinding.annotation.DependsOnStateOf;


/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class DefinePropertyDependencies {
	@DependsOnStateOf({"prop3", "prop2", "prop6"})
	public String getProp1() {
		return null;
	}
	
	public String getProp2() {
		return null;
	}

	public String getProp3() {
		return null;
	}
	
	@DependsOnStateOf({"prop1", "prop6", "prop5"})
	public String getProp4() {
		return null;
	}

	public String getProp5() {
		return null;
	}
	
	public String getProp6() {
		return null;
	}
}
