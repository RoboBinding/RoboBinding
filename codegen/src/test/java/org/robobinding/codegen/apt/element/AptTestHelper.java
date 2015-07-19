package org.robobinding.codegen.apt.element;

import java.util.List;

import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

import org.robobinding.codegen.apt.SetterElementFilter;

import com.google.testing.compile.CompilationRule;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class AptTestHelper {
	private final Elements elements;
	private final ElementWrapper elementWrapper;
	
	public AptTestHelper(CompilationRule compilation) {
		this.elements = compilation.getElements();
		this.elementWrapper = ElementWrapper.create(compilation.getTypes(), compilation.getElements(), null);
	}
	
	public WrappedTypeElement typeElementOf(Class<?> type) {
		TypeElement typeElement = elements.getTypeElement(type.getName());
		return elementWrapper.wrap(typeElement);
	}
	
	public SetterElement looseSetterOf(Class<?> type, final String propertyName) {
		WrappedTypeElement typeElement = typeElementOf(type);
		List<SetterElement> setters = typeElement.looseSetters(new SetterElementFilter() {
			@Override
			public boolean include(SetterElement element) {
				return element.propertyName().equals(propertyName);
			}
		});
		
		return setters.isEmpty() ? null : setters.get(0);
	}

}
