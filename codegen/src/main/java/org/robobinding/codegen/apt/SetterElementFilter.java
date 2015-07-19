package org.robobinding.codegen.apt;

import org.robobinding.codegen.apt.element.SetterElement;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public interface SetterElementFilter {
	boolean include(SetterElement element);
}
