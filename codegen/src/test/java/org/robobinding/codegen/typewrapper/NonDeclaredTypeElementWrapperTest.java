package org.robobinding.codegen.typewrapper;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.google.testing.compile.CompilationRule;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class NonDeclaredTypeElementWrapperTest {
	@Rule public CompilationRule compilation = new CompilationRule();
	private TypeTestHelper typeTestHelper;
	
	@Before
	public void setUp() {
		typeTestHelper = new TypeTestHelper(compilation);
	}

	@Test
	public void whenTestIsBoolean_thenReturnTrue() {
		assertTrue(primitiveWrapperOf(TypeKind.BOOLEAN).isBoolean());
	}

	private AbstractTypeElementWrapper primitiveWrapperOf(TypeKind kind) {
		return typeTestHelper.primitiveTypeElementOf(kind);
	}
	
	@Test
	public void whenGetPrimitiveTypeName_thenReturnExpectedName() {
		assertThat(primitiveTypeNameOf(TypeKind.INT), equalTo("int"));
		assertThat(primitiveTypeNameOf(TypeKind.LONG), equalTo("long"));
	}

	private String primitiveTypeNameOf(TypeKind kind) {
		return primitiveWrapperOf(kind).typeName();
	}
	
	@Test
	public void whenGetArrayTypeName_thenReturnExpectedName() {
		assertThat(arrayTypeNameOf(typeTestHelper.primitiveTypeOf(TypeKind.INT)), equalTo("int[]"));
		assertThat(arrayTypeNameOf(typeTestHelper.declaredTypeOf(Integer.class)), equalTo("java.lang.Integer[]"));
	}
	
	private String arrayTypeNameOf(TypeMirror componentType) {
		return typeTestHelper.arrayTypeElementOf(componentType).typeName();
	}
	
	@Test
	public void whenGetNonPrimitiveTypeName_thenReturnExpectedName() {
		assertThat(primitiveWrapperOf(TypeKind.INT).nonPrimitiveTypeName(), equalTo("java.lang.Integer"));
		assertThat(primitiveWrapperOf(TypeKind.LONG).nonPrimitiveTypeName(), equalTo("java.lang.Long"));
	}
	
}
