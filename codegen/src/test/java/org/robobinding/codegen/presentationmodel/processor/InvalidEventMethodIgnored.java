package org.robobinding.codegen.presentationmodel.processor;

import org.robobinding.annotation.PresentationModel;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
@PresentationModel
public class InvalidEventMethodIgnored {
	public void eventMethodWithTwoArg(Object event1, Object event2) {
		
	}
	
	public void eventMethodWithPrimitiveArg(int event1) {
	}
}
