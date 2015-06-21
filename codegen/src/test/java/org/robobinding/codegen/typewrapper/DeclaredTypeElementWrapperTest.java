package org.robobinding.codegen.typewrapper;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.google.testing.compile.CompilationRule;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class DeclaredTypeElementWrapperTest {
	@Rule public CompilationRule compilation = new CompilationRule();
	private TypeTestHelper typeTestHelper;
	
	@Before
	public void setUp() {
		typeTestHelper = new TypeTestHelper(compilation);
	}
	
	@Test
	public void givenObjectType_thenReturnTrue() {
		assertTrue(declaredTypeElementOf(Object.class).isObjectRoot());
	}
	
	@Test
	public void givenObjectType_thenReturnFalse() {
		assertFalse(declaredTypeElementOf(Boolean.class).isObjectRoot());
	}
	
	@Test
	public void givenBooleanType_whenTestIsBoolean_thenReturnTrue() {
		assertTrue(declaredTypeElementOf(Boolean.class).isBoolean());
	}

	private DeclaredTypeElementWrapper declaredTypeElementOf(Class<?> type) {
		return typeTestHelper.declaredTypeElementOf(type);
	}
}
