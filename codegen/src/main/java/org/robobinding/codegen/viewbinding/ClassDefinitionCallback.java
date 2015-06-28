package org.robobinding.codegen.viewbinding;

import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JDefinedClass;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public interface ClassDefinitionCallback {
	JDefinedClass define(String typeName) throws JClassAlreadyExistsException;
}
