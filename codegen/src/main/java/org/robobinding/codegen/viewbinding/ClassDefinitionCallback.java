package org.robobinding.codegen.viewbinding;

import com.helger.jcodemodel.JDefinedClass;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public interface ClassDefinitionCallback {
	JDefinedClass define(String typeName);
}
