package org.robobinding.codegen.apt;

import javax.annotation.processing.Messager;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import org.robobinding.codegen.apt.element.ElementWrapper;
import org.robobinding.codegen.apt.element.WrappedTypeElement;


/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class ProcessingContext {
	private final Elements elements;
	private final ElementWrapper elementWrapper;
	
	public ProcessingContext(Types types, Elements elements, Messager messager) {
		this.elements = elements;
		this.elementWrapper = ElementWrapper.create(types, elements, messager);
	}

	public WrappedTypeElement typeElementOf(String qName) {
		return elementWrapper.wrap(elements.getTypeElement(qName));
	}
}
