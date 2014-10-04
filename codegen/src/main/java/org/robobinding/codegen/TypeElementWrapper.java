package org.robobinding.codegen;

import java.util.List;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Types;

import com.google.common.collect.Lists;


/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class TypeElementWrapper {
	private final ProcessingContext context;
	private final Types types;
	private final TypeElement type;

	public TypeElementWrapper(ProcessingContext context, Types types, TypeElement type) {
		this.context = context;
		this.types = types;
		this.type = type;
	}

	public boolean isObjectType() {
		return types.isSameType(type.asType(), types.getNoType(TypeKind.NONE));
	}

	public List<MethodElementWrapper> getMethods() {
		List<ExecutableElement> methods = ElementFilter.methodsIn(type.getEnclosedElements());
		
		List<MethodElementWrapper> result = Lists.newArrayList();
		for(ExecutableElement method : methods) {
			result.add(context.wrapper(method));
		}
		return result;
	}

	public TypeElementWrapper getSuperclass() {
		return context.wrapper(type.getSuperclass());
	}

	public String typeName() {
		return type.getQualifiedName().toString();
	}
	
	public boolean isAssignableTo(TypeMirror anotherType) {
		return types.isAssignable(type.asType(), anotherType);
	}

}
