package org.robobinding.codegen.typewrapper;

import javax.annotation.processing.Messager;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class ProcessingContext {
	private final TypesWrapper types;
	private final Elements elements;
	private final Messager messager;
	
	public ProcessingContext(TypesWrapper types, Elements elements, Messager messager) {
		this.types = types;
		this.elements = elements;
		this.messager = messager;
	}
	
	public MethodElementWrapper wrapper(ExecutableElement element) {
		return new MethodElementWrapper(this, types, element);
	}

	public AbstractTypeElementWrapper typeElementOf(TypeMirror type) {
		if(type.getKind().equals(TypeKind.DECLARED)) {
			return declaredTypeElementOf((DeclaredType)type);
		} else {
			return new NonDeclaredTypeElementWrapper(this, types, type);
		}
	}
	
	public DeclaredTypeElementWrapper boxedClassTypeElementOf(PrimitiveType type) {
		return wrapper(types.boxedClass(type));
	}

	public DeclaredTypeElementWrapper declaredTypeElementOf(DeclaredType type) {
		return new DeclaredTypeElementWrapper(this, types, (TypeElement)types.asElement(type), type);
	}

	public DeclaredTypeElementWrapper declaredTypeElementOf(Class<?> type) {
		return declaredTypeElementOf(type.getName());
	}
	
	public DeclaredTypeElementWrapper declaredTypeElementOf(String typeName) {
		return wrapper(elements.getTypeElement(typeName));
	}
	
	public DeclaredTypeElementWrapper wrapper(TypeElement typeElement) {
		return new DeclaredTypeElementWrapper(this, types, typeElement, (DeclaredType)typeElement.asType());
	}
	
	/*
	public TypeMirror typeMirrorOf(Class<?> type) {
		return typeMirrorOf(type.getName());
	}

	public TypeMirror typeMirrorOf(String type) {
		return elements.getTypeElement(type).asType();
	}*/
	
	public Logger loggerFor(AbstractElementWrapper element) {
		return new MessagerLogger(messager, element.element);
	}
}
