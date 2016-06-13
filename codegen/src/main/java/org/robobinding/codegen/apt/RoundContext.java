package org.robobinding.codegen.apt;

import java.lang.annotation.Annotation;
import java.util.Set;

import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import org.robobinding.codegen.apt.element.ElementWrapper;
import org.robobinding.codegen.apt.element.WrappedTypeElement;
import org.robobinding.util.Sets;

public class RoundContext {
	private final RoundEnvironment env;
	private final ElementWrapper elementWrapper;
	
	public RoundContext(RoundEnvironment env, Types types, Elements elements, Messager messager) {
		this.env = env;
		this.elementWrapper = ElementWrapper.create(types, elements, messager);
	}

	public Set<WrappedTypeElement> typeElementsAnnotatedWith(Class<? extends Annotation> a, TypeElementFilter filter) {
		Set<WrappedTypeElement> typeElements = Sets.newHashSet();
	    for (Element element : env.getElementsAnnotatedWith(a)) {
	    	WrappedTypeElement typeElement = elementWrapper.wrap(element);
	    	if(filter.include(typeElement)) {
	    		typeElements.add(typeElement);
	    	}
	    }
	    return typeElements;
	}
}
