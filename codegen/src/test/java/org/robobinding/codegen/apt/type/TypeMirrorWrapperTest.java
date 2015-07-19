package org.robobinding.codegen.apt.type;

import static org.hamcrest.Matchers.isA;
import static org.junit.Assert.assertThat;

import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.NoType;
import javax.lang.model.type.NullType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import com.google.testing.compile.CompilationRule;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
@RunWith(Theories.class)
public class TypeMirrorWrapperTest {
	@ClassRule public static final CompilationRule compilation = new CompilationRule();
	
	@Rule
	public ExpectedException thrownException = ExpectedException.none();
	
	private TypeMirrorWrapper wrapper;
	
	@Before
	public void setUp() {
		wrapper = new TypeMirrorWrapper(compilation.getTypes(), compilation.getElements());
	}
	
	@Theory
	public void whenWrapSupportedTypeMirrors_thenReturnCoorespondingWrappedInstance(
			@FromDataPoints("supportedTypeMirrors") TypeMirrorToWrapped typeMirrorToWrapped) {
		WrappedTypeMirror wrapped = wrapper.wrap(typeMirrorToWrapped.typeMirror);
		
		assertThat(wrapped, isA(typeMirrorToWrapped.wrappedType));
	}
	
	@DataPoints("supportedTypeMirrors")
	public static TypeMirrorToWrapped[] supportedTypeMirrors() {
		Types types = compilation.getTypes();
		NoType voidType = types.getNoType(TypeKind.VOID);
		PrimitiveType primitiveType = types.getPrimitiveType(TypeKind.INT);
		
		Elements elements = compilation.getElements();
		DeclaredType declaredType = (DeclaredType)elements.getTypeElement(Object.class.getName()).asType();
		ArrayType arrayType = types.getArrayType(declaredType);
		
		return new TypeMirrorToWrapped[] {
				a(voidType).itsWrapped(WrappedVoidType.class), 
				a(primitiveType).itsWrapped(WrappedPrimitiveType.class), 
				a(declaredType).itsWrapped(WrappedDeclaredType.class), 
				a(arrayType).itsWrapped(WrappedArrayType.class)};
	}
	
	@Theory()
	public void whenWrapUnsupportedTypeMirrors_thenThrowsError(
			@FromDataPoints("unsupportedTypeMirrors") TypeMirror typeMirror) {
		thrownException.expect(UnsupportedOperationException.class);

		wrapper.wrap(typeMirror);
		
	}
	
	
	@DataPoints("unsupportedTypeMirrors")
	public static TypeMirror[] unsupportedTypeMirrors() {
		Types types = compilation.getTypes();
		NullType nullType = types.getNullType();
		NoType noneType = types.getNoType(TypeKind.NONE);
		
		return new TypeMirror[] {nullType, noneType};
	}
	
	private static TypeMirrorToWrapped a(TypeMirror typeMirror) {
		return new TypeMirrorToWrapped(typeMirror);
	}
	
	private static class TypeMirrorToWrapped {
		public final TypeMirror typeMirror;
		public Class<WrappedTypeMirror> wrappedType;
		
		public TypeMirrorToWrapped(TypeMirror typeMirror) {
			this.typeMirror = typeMirror;
		}
		
		@SuppressWarnings("unchecked")
		public TypeMirrorToWrapped itsWrapped(Class<? extends WrappedTypeMirror> wrappedType) {
			this.wrappedType = (Class<WrappedTypeMirror>)wrappedType;
			return this;
		}
	}
}
