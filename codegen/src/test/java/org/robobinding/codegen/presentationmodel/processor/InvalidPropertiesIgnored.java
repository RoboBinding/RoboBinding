package org.robobinding.codegen.presentationmodel.processor;

import org.robobinding.annotation.PresentationModel;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
@PresentationModel
public class InvalidPropertiesIgnored {
	public void getGetterWithNoReturn() {
	}
	
	public int getGetterWithParam(int param) {
		return 0;
	}

	public int getGetterWithParam(int param1, int param2) {
		return 0;
	}
	
	public int setSetterWithReturn(int param) {
		return 0;
	}
	
	public void setSetterWithNoParam() {
	}
	
	public void setSetterWithTwoParams(int param1, int param2) {
	}
}
