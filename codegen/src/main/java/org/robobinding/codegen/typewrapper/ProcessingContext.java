package org.robobinding.codegen.typewrapper;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
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

	public AbstractTypeElementWrapper typeElementOf(TypeMirror type) {
		if(type.getKind().equals(TypeKind.DECLARED)) {
			return wrapper((TypeElement) types.asElement(type));
		} else {
			return new NonDeclaredTypeElementWrapper(this, types, type);
		}
	}

	public AbstractTypeElementWrapper typeElementOf(Class<?> type) {
		return typeElementOf(type.getName());
	}
	
	public AbstractTypeElementWrapper typeElementOf(String typeName) {
		return wrapper(elements.getTypeElement(typeName));
	}
	
	public AbstractTypeElementWrapper wrapper(TypeElement typeElement) {
		return new DeclaredTypeElementWrapper(this, types, typeElement);
	}

	public TypeMirror typeMirrorOf(Class<?> type) {
		return typeMirrorOf(type.getName());
	}

	public TypeMirror typeMirrorOf(String type) {
		return elements.getTypeElement(type).asType();
	}
}
