package org.robobinding.codegen.processor;

import java.lang.annotation.Annotation;
import java.text.MessageFormat;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class MethodElementWrapper {
	private final ProcessingContext context;
	private final Types types;
	private final ExecutableElement method;
	public MethodElementWrapper(ProcessingContext context, Types types, ExecutableElement method) {
		this.context = context;
		this.types = types;
		this.method = method;
	}
	
	public String methodName() {
		return method.getSimpleName().toString();
	}
	
	public boolean hasAnnotation(Class<? extends Annotation> annotationType) {
		return findAnnotationMirror(annotationType) != null;
	}
	
	public <A extends Annotation> AnnotationMirrorWrapper getAnnotation(Class<A> annotationType) {
		AnnotationMirror annotationMirror = findAnnotationMirror(annotationType);
		if(annotationMirror != null) {
			return new AnnotationMirrorWrapper(context, annotationMirror);
		} else {
			return null;
		}
	}
	
	private  <A extends Annotation> AnnotationMirror findAnnotationMirror(Class<A> annotationType) {
		for(AnnotationMirror m : method.getAnnotationMirrors()) {
			if(types.isSameType(m.getAnnotationType(), context.typeMirrorOf(annotationType))) {
				return m;
			}
		}
		return null;
	}


	public boolean isStaticOrNonPublic() {
		return (!method.getModifiers().contains(Modifier.PUBLIC)) || method.getModifiers().contains(Modifier.STATIC);
	}

	public boolean hasReturn() {
		return !hasNoReturn();
	}
	
	public boolean isOfReturnType(TypeMirror returnType) {
		return method.getReturnType().equals(returnType);
	}
	
	public boolean isBooleanReturnType() {
		return types.isSameType(method.getReturnType(), context.typeMirrorOf(boolean.class)) 
				|| types.isSameType(method.getReturnType(), context.typeMirrorOf(Boolean.class));
	}

	private boolean isPrimitiveReturnType() {
		return method.getReturnType().getKind().isPrimitive();
	}

	public boolean hasNoReturn() {
		return method.getReturnType().getKind().equals(TypeKind.VOID);
	}

	public String returnTypeName() {
		return context.TypeElementOf(method.getReturnType()).typeName();
	}

	public String nonPrimitiveReturnTypeName() {
		if(isPrimitiveReturnType()) {
			return context.wrapper(types.boxedClass((PrimitiveType)method.getReturnType())).typeName();
		} else {
			return returnTypeName();
		}
	}

	public boolean isReturnTypeAssignableTo(Class<?> type) {
		return context.TypeElementOf(method.getReturnType()).isAssignableTo(context.typeMirrorOf(type));
	}
	
	public boolean isReturnTypeNotAssignableTo(String type) {
		return !isReturnTypeAssignableTo(type);
	}

	public boolean isReturnTypeAssignableTo(String type) {
		return context.TypeElementOf(method.getReturnType()).isAssignableTo(context.typeMirrorOf(type));
	}

	public boolean hasMoreThanOneParameters() {
		return method.getParameters().size() > 1;
	}
	

	public boolean hasSingleParameter() {
		return method.getParameters().size() == 1;
	}

	public String firstParameterTypeName() {
		TypeMirror parameterType = firstParameterType();
		return context.TypeElementOf(parameterType).typeName();
	}
	
	public TypeMirror firstParameterType() {
		return  method.getParameters().get(0).asType();
	}
	
	public boolean isFirstParameterNotPrimitiveType() {
		TypeMirror parameterType = method.getParameters().get(0).asType();
		return !parameterType.getKind().isPrimitive();
	}

	public boolean hasParameter() {
		return !method.getParameters().isEmpty();
	}

	@Override
	public String toString() {
		TypeElementWrapper ownerType = context.wrapper((TypeElement)method.getEnclosingElement());
		return MessageFormat.format("{0} {1}.{2}", returnTypeName(), ownerType.typeName(), method.toString());
	}

}
