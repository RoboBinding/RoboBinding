package org.robobinding.codegen.processor;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class ProcessingContext {
	private final Types types;
	private final Elements elements;
	
	public ProcessingContext(Types types, Elements elements) {
		this.types = types;
		this.elements = elements;
	}
	
	public MethodElementWrapper wrapper(ExecutableElement element) {
		return new MethodElementWrapper(this, types, element);
	}

	public TypeElementWrapper typeElementOf(TypeMirror type) {
		return wrapper((TypeElement) types.asElement(type));
	}

	public TypeElementWrapper typeElementOf(Class<?> type) {
		return typeElementOf(type.getName());
	}
	
	public TypeElementWrapper typeElementOf(String typeName) {
		return wrapper(elements.getTypeElement(typeName));
	}
	
	public TypeElementWrapper wrapper(TypeElement typeElement) {
		return new TypeElementWrapper(this, types, typeElement);
	}
	
	public String typeName(TypeMirror type) {
		if(type instanceof ArrayType) {
			return nonArrayTypeName(((ArrayType)type).getComponentType())+"[]";
		} else {
			return typeElementOf(type).typeName();
		}
	}
	
	private String nonArrayTypeName(TypeMirror type) {
		if(type.getKind().isPrimitive()) {
			return String.valueOf(type.getKind()).toLowerCase();
		} else {
			return typeElementOf(type).typeName();
		}
	}

	public TypeMirror typeMirrorOf(Class<?> type) {
		return typeMirrorOf(type.getName());
	}

	public TypeMirror typeMirrorOf(String type) {
		return elements.getTypeElement(type).asType();
	}
}
