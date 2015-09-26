package org.robobinding.codegen.apt.element;

import java.text.MessageFormat;
import java.util.Set;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;

import org.apache.commons.lang3.StringUtils;
import org.robobinding.codegen.apt.MessagerLoggerFactory;
import org.robobinding.codegen.apt.type.TypeMirrorWrapper;
import org.robobinding.codegen.apt.type.WrappedTypeMirror;
import org.robobinding.internal.java_beans.Introspector;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class MethodElement extends AbstractWrappedElement {
	private final ExecutableElement method;
	private final WrappedTypeElement typeElement;
	private final TypeMirrorWrapper typeWrapper;
	
	public MethodElement(ExecutableElement method, TypeMirrorWrapper typeWrapper, 
			MessagerLoggerFactory loggerFactory, WrappedTypeElement typeElement) {
		super(method, typeWrapper, loggerFactory);
		
		this.method = method;
		this.typeElement = typeElement;
		this.typeWrapper = typeWrapper;
	}

	public String methodName() {
		return method.getSimpleName().toString();
	}

	public boolean isStaticOrNonPublic() {
		Set<Modifier> modifiers = method.getModifiers();
		return (!modifiers.contains(Modifier.PUBLIC)) || modifiers.contains(Modifier.STATIC);
	}

	public boolean hasReturn() {
		return !hasNoReturn();
	}

	public boolean hasNoReturn() {
		WrappedTypeMirror returnType = returnType();
		return returnType.isVoid();
	}
	
	public <T extends WrappedTypeMirror> T returnType() {
		return typeWrapper.wrap(method.getReturnType());
	}

	public boolean hasParameter() {
		return !hasNoParameter();
	}

	public boolean hasNoParameter() {
		return method.getParameters().isEmpty();
	}

	public boolean hasMoreThanOneParameters() {
		return method.getParameters().size() > 1;
	}

	public boolean hasSingleParameter() {
		return method.getParameters().size() == 1;
	}
	
	public boolean hasSingleParameterTyped(Class<?> type) {
		return hasSingleParameter() && firstParameterType().isOfType(type);
	}

	public WrappedTypeMirror firstParameterType() {
		return typeWrapper.wrap(method.getParameters().get(0).asType());
	}
	
	public boolean isGetter() {
		if(hasParameter() || hasNoReturn()) {
			return false;
		}
		
		String propertyName = findRawPropertyNameAsGetter();
		
		return isFirstLetterUpperCase(propertyName);
	}
	
	private String findRawPropertyNameAsGetter() {
		String methodName = methodName();
		if (methodName.startsWith("is") && returnType().isBoolean()) {
			return StringUtils.removeStart(methodName, "is");
		} else if (methodName.startsWith("get")) {
			return StringUtils.removeStart(methodName, "get");
		} else {
			return "";
		}
	}
	
	private boolean isFirstLetterUpperCase(String text) {
		return StringUtils.isNotBlank(text) && Character.isUpperCase(text.charAt(0));
	}
	
	public GetterElement asGetter() {
		if (!isGetter()) {
			throw new UnsupportedOperationException(toString() + " is not a getter");
		}
		
		String methodName = methodName();
		String rawPropertyName = "";
		if (methodName.startsWith("is")) {
			rawPropertyName = StringUtils.removeStart(methodName, "is");
		} else {
			rawPropertyName = StringUtils.removeStart(methodName, "get");
		}
		
		return new GetterElement(this, Introspector.decapitalize(rawPropertyName));
	}

	/**
	 * Disregard the return type.
	 */
	public boolean isLooseSetter() {
		String methodName = methodName();
		
		if (methodName.startsWith("set") && hasSingleParameter()) {
			String propertyName = findRawPropertyNameAsSetter();
			if(isFirstLetterUpperCase(propertyName)) {
				return true;
			}
		}
		
		return false;
	}
	
	private String findRawPropertyNameAsSetter() {
		return StringUtils.removeStart(methodName(), "set");
	}
	
	public SetterElement asLooseSetter() {
		if (!isLooseSetter()) {
			throw new UnsupportedOperationException(toString() + " is not a loose setter");
		}
		
		return createSetter();
	}

	private SetterElement createSetter() {
		String rawPropertyName = findRawPropertyNameAsSetter();
		return new SetterElement(this, Introspector.decapitalize(rawPropertyName));
	}
	
	public boolean isSetter() {
		return isLooseSetter() && hasNoReturn();
	}
	
	public SetterElement asSetter() {
		if (!isSetter()) {
			throw new UnsupportedOperationException(toString() + " is not a setter");
		}
		
		return createSetter();
	}
	
	@Override
	public String toString() {
		return MessageFormat.format("{0} {1}.{2}", returnType().className(), typeElement.qName(), method.toString());
	}

}
