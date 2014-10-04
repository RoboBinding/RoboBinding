package org.robobinding.codegen;

import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class MethodElementWrapper {
	private final ProcessingContext context;
	private final ExecutableElement method;
	public MethodElementWrapper(ProcessingContext context, ExecutableElement method) {
		this.context = context;
		this.method = method;
	}
	
	public String methodName() {
		return method.getSimpleName().toString();
	}
	
	public boolean hasAnnotation(Class<? extends Annotation> annotationType) {
		return method.getAnnotation(annotationType) != null;
	}
	
	public boolean isStaticOrNonPublic() {
		return (!method.getModifiers().contains(Modifier.PUBLIC)) || method.getModifiers().contains(Modifier.STATIC);
	}

	public boolean hasReturn() {
		return isOfReturnType(TypeKind.VOID);
	}
	
	public String returnTypeName() {
		return context.wrapper(method.getReturnType()).typeName();
	}

	public boolean isOfReturnType(TypeKind returnType) {
		return method.getReturnType().getKind().equals(returnType);
	}

	public boolean isReturnTypeSubTypeOf(Class<?> type) {
		return context.wrapper(method.getReturnType()).isAssignableTo(context.typeMirrorOf(type));
	}
	
	public boolean isReturnTypeSubTypeOf(String type) {
		return context.wrapper(method.getReturnType()).isAssignableTo(context.typeMirrorOf(type));
	}

	public boolean hasMoreThanOneParameters() {
		return method.getParameters().size() > 1;
	}
	

	public boolean hasSingleParameter() {
		return method.getParameters().size() == 1;
	}

	public String firstParameterTypeName() {
		TypeMirror parameterType = method.getParameters().get(0).asType();
		return context.wrapper(parameterType).typeName();
	}

	public boolean hasParameter() {
		return !method.getParameters().isEmpty();
	}


}
