package org.robobinding.codegen;

import org.robobinding.codegen.typemirror.Event;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class DefineTryToCreateFunction {
	@Event
	public void eventMethod1() {
	}
	
	@Event
	public void eventMethod2(Object event) {
	}
	
	@Event
	public boolean eventMethodWithReturn(Object event) {
		return true;
	}
}
