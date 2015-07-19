package org.robobinding.codegen.apt;

import org.robobinding.codegen.apt.element.WrappedTypeElement;

public interface TypeElementFilter {
	boolean include(WrappedTypeElement element);
}
