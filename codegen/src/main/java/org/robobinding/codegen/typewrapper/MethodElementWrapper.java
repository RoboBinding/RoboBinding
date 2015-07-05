package org.robobinding.codegen.typewrapper;

import java.text.MessageFormat;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class MethodElementWrapper extends AbstractElementWrapper {
	private final ExecutableElement method;
	public MethodElementWrapper(ProcessingContext context, TypesWrapper types, ExecutableElement method) {
		super(context, types, method);
		
		this.method = method;
	}
	
	public String methodName() {
		return method.getSimpleName().toString();
	}

	public boolean isStaticOrNonPublic() {
		return (!method.getModifiers().contains(Modifier.PUBLIC)) || method.getModifiers().contains(Modifier.STATIC);
	}

	public boolean hasReturn() {
		return !hasNoReturn();
	}

	public boolean hasNoReturn() {
		AbstractTypeElementWrapper returnType = getReturnType();
		return returnType.isVoid();
	}
	
	public AbstractTypeElementWrapper getReturnType() {
		return context.typeElementOf(method.getReturnType());
	}

	public boolean hasParameter() {
		return !method.getParameters().isEmpty();
	}

	public boolean hasMoreThanOneParameters() {
		return method.getParameters().size() > 1;
	}

	public boolean hasSingleParameter() {
		return method.getParameters().size() == 1;
	}
	
	public AbstractTypeElementWrapper firstParameterType() {
		return context.typeElementOf(method.getParameters().get(0).asType());
	}
	
	@Override
	public String toString() {
		AbstractTypeElementWrapper ownerType = context.wrapper((TypeElement)method.getEnclosingElement());
		return MessageFormat.format("{0} {1}.{2}", getReturnType().typeName(), ownerType.typeName(), method.toString());
	}

}
