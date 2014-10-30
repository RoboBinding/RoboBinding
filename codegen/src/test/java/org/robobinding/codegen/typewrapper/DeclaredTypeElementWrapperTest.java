package org.robobinding.codegen.typewrapper;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.lang.model.element.TypeElement;

import org.junit.Rule;
import org.junit.Test;

import com.google.testing.compile.CompilationRule;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class DeclaredTypeElementWrapperTest {
	@Test
	public void givenObjectType_thenReturnTrue() {
		assertTrue(wrapperOf(Object.class).isObjectRoot());
	}
	
	@Test
	public void givenObjectType_thenReturnFalse() {
		assertFalse(wrapperOf(Boolean.class).isObjectRoot());
	}
	
	@Test
	public void givenBooleanType_whenTestIsBoolean_thenReturnTrue() {
		assertTrue(wrapperOf(Boolean.class).isBoolean());
	}
	
	@Rule public CompilationRule compilation = new CompilationRule();
	
	private AbstractTypeElementWrapper wrapperOf(Class<?> type) {
		ProcessingContext context = new ProcessingContext(compilation.getTypes(), compilation.getElements());
		TypeElement typeElement = compilation.getElements().getTypeElement(type.getCanonicalName());
		return new DeclaredTypeElementWrapper(context, compilation.getTypes(), typeElement);
	}
}
