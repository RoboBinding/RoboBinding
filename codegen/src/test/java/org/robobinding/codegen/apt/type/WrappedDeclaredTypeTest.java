package org.robobinding.codegen.apt.type;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import javax.lang.model.type.DeclaredType;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.google.testing.compile.CompilationRule;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class WrappedDeclaredTypeTest {
	@Rule public final CompilationRule compilation = new CompilationRule();
	
	private WrappedDeclaredType type;
	
	@Before
	public void setUp() {
		type = new WrappedDeclaredType(typeOf(GenericClass.class), 
				compilation.getTypes(), compilation.getElements());

	}
	
	@Test
	public void shouldReturnCorrectClassName() {
		assertThat(type.className(), equalTo(GenericClass.class.getName()));
	}
	
	private DeclaredType typeOf(Class<?> klass) {
		return (DeclaredType)compilation.getElements().getTypeElement(klass.getName()).asType();
	}
	
	@Test
	public void shouldIsOfTypeReturnTrue() {
		assertThat(type.isOfType(GenericClass.class), equalTo(true));
	}
	
	@Test
	public void shouldAssignableToSelfAndParent() {
		assertThat(type.isAssignableTo(GenericClass.class), equalTo(true));
		assertThat(type.isAssignableTo(ParentClass.class), equalTo(true));
	}
	
	@Test
	public void shouldHaveSingleParameterizedType() {
		assertThat(type.getTypeArguments().size(), equalTo(1));
	}
}
