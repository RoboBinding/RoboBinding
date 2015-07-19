package org.robobinding.codegen.apt;

import org.robobinding.codegen.apt.element.MethodElement;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public interface MethodElementFilter {
	boolean include(MethodElement element);
}
