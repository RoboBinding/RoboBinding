package org.robobinding.codegen.apt.element;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementVisitor;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.UnknownElementException;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import org.robobinding.codegen.apt.MessagerLoggerFactory;
import org.robobinding.codegen.apt.type.TypeMirrorWrapper;
import org.robobinding.codegen.apt.type.WrappedDeclaredType;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class ElementWrapper {
	private final TypeMirrorWrapper typeWrapper;
	private final MessagerLoggerFactory loggerFactory;
	private final Types types;
	private final WrapperVisitor visitor;

	public ElementWrapper(TypeMirrorWrapper typeWrapper, MessagerLoggerFactory loggerFactory, Types types) {
		this.typeWrapper = typeWrapper;
		this.loggerFactory = loggerFactory;
		this.types = types;
		
		this.visitor = new WrapperVisitor();
	}

	@SuppressWarnings({ "unchecked" })
	public <E extends AbstractWrappedElement> E wrap(Element element) {
		if (element == null) {
			return null;
		}

		return (E) element.accept(visitor, null);
	}
	
	public static ElementWrapper create(Types types, Elements elements, Messager messager) {
		TypeMirrorWrapper typeWrapper = new TypeMirrorWrapper(types, elements);
		ElementWrapper elementWrapper = new ElementWrapper(
				typeWrapper,
				new MessagerLoggerFactory(messager),
				types);
		
		return elementWrapper;
	}

	private class WrapperVisitor implements ElementVisitor<AbstractWrappedElement, Void> {
		@Override
		public WrappedTypeElement visitType(TypeElement e, Void p) {
			WrappedDeclaredType type = typeWrapper.wrap(e.asType());
			return new WrappedTypeElement(e, type, typeWrapper, loggerFactory, ElementWrapper.this, types);
		}

		@Override
		public MethodElement visitExecutable(ExecutableElement e, Void p) {
			WrappedTypeElement typeElement = wrap(e.getEnclosingElement());
			return new MethodElement(e, typeWrapper, loggerFactory, typeElement);
		}

		@Override
		public AbstractWrappedElement visit(Element e, Void p) {
			return throwsUnsupportedOperationException();
		}

		private AbstractWrappedElement throwsUnsupportedOperationException() {
			throw new UnsupportedOperationException();
		}

		@Override
		public AbstractWrappedElement visit(Element e) {
			return throwsUnsupportedOperationException();
		}

		@Override
		public AbstractWrappedElement visitPackage(PackageElement e, Void p) {
			return throwsUnsupportedOperationException();
		}

		@Override
		public AbstractWrappedElement visitVariable(VariableElement e, Void p) {
			return throwsUnsupportedOperationException();
		}

		@Override
		public AbstractWrappedElement visitTypeParameter(TypeParameterElement e, Void p) {
			return throwsUnsupportedOperationException();
		}

		@Override
		public AbstractWrappedElement visitUnknown(Element e, Void p) {
			throw new UnknownElementException(e, p);
		}
	}
}
