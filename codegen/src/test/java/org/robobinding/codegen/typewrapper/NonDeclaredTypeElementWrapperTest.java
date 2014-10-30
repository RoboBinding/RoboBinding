package org.robobinding.codegen.typewrapper;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import org.junit.Rule;
import org.junit.Test;

import com.google.testing.compile.CompilationRule;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class NonDeclaredTypeElementWrapperTest {
	@Test
	public void whenTestIsBoolean_thenReturnTrue() {
		assertTrue(primitiveWrapperOf(TypeKind.BOOLEAN).isBoolean());
	}
	
	@Test
	public void whenGetPrimitiveTypeName_thenReturnExpectedName() {
		assertThat(primitiveWrapperOf(TypeKind.INT).typeName(), equalTo("int"));
		assertThat(primitiveWrapperOf(TypeKind.LONG).typeName(), equalTo("long"));
	}
	
	@Test
	public void whenGetArrayTypeName_thenReturnExpectedName() {
		assertThat(arrayWrapperOf(primitiveTypeOf(TypeKind.INT)).typeName(), equalTo("int[]"));
		assertThat(arrayWrapperOf(declaredTypeOf(Integer.class)).typeName(), equalTo("java.lang.Integer[]"));
	}
	@Test
	public void whenGetNonPrimitiveTypeName_thenReturnExpectedName() {
		assertThat(primitiveWrapperOf(TypeKind.INT).nonPrimitiveTypeName(), equalTo("java.lang.Integer"));
		assertThat(primitiveWrapperOf(TypeKind.LONG).nonPrimitiveTypeName(), equalTo("java.lang.Long"));
	}
	
	@Rule public CompilationRule compilation = new CompilationRule();
	
	private AbstractTypeElementWrapper primitiveWrapperOf(TypeKind kind) {
		return wrapperOf(primitiveTypeOf(kind));
	}
	
	private PrimitiveType primitiveTypeOf(TypeKind kind) {
		Types types = compilation.getTypes();
		return types.getPrimitiveType(kind);
	}
	
	private AbstractTypeElementWrapper wrapperOf(TypeMirror type) {
		ProcessingContext context = new ProcessingContext(compilation.getTypes(), compilation.getElements());
		return new NonDeclaredTypeElementWrapper(context, compilation.getTypes(), type);
	}
	
	private DeclaredType declaredTypeOf(Class<?> type) {
		Elements elements = compilation.getElements();
		TypeElement typeElement = elements.getTypeElement(type.getName());
		return (DeclaredType) typeElement.asType();
	}
	
	private AbstractTypeElementWrapper arrayWrapperOf(TypeMirror componentType) {
		return wrapperOf(arrayTypeOf(componentType));
	}
	
	private ArrayType arrayTypeOf(TypeMirror componentType) {
		Types types = compilation.getTypes();
		return types.getArrayType(componentType);
	}
}
