package org.robobinding.codegen;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class ProcessingContext {
	private Types types;
	private Elements elements;
	
	public MethodElementWrapper wrapper(ExecutableElement element) {
		return new MethodElementWrapper(this, element);
	}

	public TypeElementWrapper wrapper(TypeMirror type) {
		return new TypeElementWrapper(this, types, (TypeElement) types.asElement(type));
	}

	public TypeMirror typeMirrorOf(Class<?> type) {
		return typeMirrorOf(type.getName());
	}

	public TypeMirror typeMirrorOf(String type) {
		return elements.getTypeElement(type).asType();
	}
}
