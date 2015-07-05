package org.robobinding.codegen.typewrapper;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import com.google.testing.compile.CompilationRule;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class TypeTestHelper {
	private final ProcessingContext context;
	private final Types types;
	private final Elements elements;
	
	public TypeTestHelper(CompilationRule compilation) {
		this.types = compilation.getTypes();
		this.elements = compilation.getElements();
		this.context = new ProcessingContext(newTypesWraper(), compilation.getElements(), null);
	}
	
	private TypesWrapper newTypesWraper() {
		return new TypesWrapper(types, elements);
	}
	
	public NonDeclaredTypeElementWrapper primitiveTypeElementOf(TypeKind kind) {
		return new NonDeclaredTypeElementWrapper(context, newTypesWraper(), primitiveTypeOf(kind));
	}

	public PrimitiveType primitiveTypeOf(TypeKind kind) {
		return types.getPrimitiveType(kind);
	}

	public AbstractTypeElementWrapper arrayTypeElementOf(TypeMirror componentType) {
		return context.typeElementOf(arrayTypeOf(componentType));
	}
	
	private ArrayType arrayTypeOf(TypeMirror componentType) {
		return types.getArrayType(componentType);
	}

	public DeclaredType declaredTypeOf(Class<?> type) {
		TypeElement typeElement = elements.getTypeElement(type.getName());
		return (DeclaredType) typeElement.asType();
	}
	
	public DeclaredTypeElementWrapper declaredTypeElementOf(Class<?> type) {
		return context.declaredTypeElementOf(type);
	}

}
