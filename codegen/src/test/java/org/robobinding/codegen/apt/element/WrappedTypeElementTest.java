package org.robobinding.codegen.apt.element;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.robobinding.codegen.apt.MethodElementFilter;
import org.robobinding.codegen.apt.SetterElementFilter;
import org.robobinding.codegen.apt.type.TypeMirrorWrapper;
import org.robobinding.codegen.apt.type.WrappedDeclaredType;

import com.google.testing.compile.CompilationRule;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class WrappedTypeElementTest {
	@Rule public final CompilationRule compilation = new CompilationRule();
	
	private WrappedTypeElement klassTypeElement;
	
	@Before
	public void setUp() {
		klassTypeElement = typeElement(Klass.class);
	}
	
	private WrappedTypeElement typeElement(Class<?> klass) {
		TypeElement element = compilation.getElements().getTypeElement(klass.getName());
		DeclaredType type = (DeclaredType)element.asType();
		TypeMirrorWrapper typeWrapper = new TypeMirrorWrapper(compilation.getTypes(), compilation.getElements());
		ElementWrapper elementWrapper = new ElementWrapper(typeWrapper, null, compilation.getTypes());
		
		return new WrappedTypeElement(
				element, 
				new WrappedDeclaredType(type, compilation.getTypes(), compilation.getElements()), 
				typeWrapper, null, 
				elementWrapper, 
				compilation.getTypes());
	}
	
	@Test
	public void whenGetMethodsRecursively_thenReturnAllMethodsIncludeParent() {
		List<MethodElement> methods = klassTypeElement.methodsRecursively(ALL_METHODS);
		
		assertThat(methods.size(), equalTo(Klass.NUM_METHODS+ParentClass.NUM_METHODS));
	}

	@Test
	public void whenGetterLooseSetters_thenReturnSettersFromKlassOnly() {
		List<SetterElement> setters = klassTypeElement.looseSetters(ALL_SETTERS);
		
		assertThat(setters.size(), equalTo(Klass.NUM_SETTERS));
	}
	
	@Test
	public void shouldFoundDirectSuperclassOfParentClass() {
		assertThat(klassTypeElement.findDirectSuperclassOf(ParentClass.class), notNullValue());
	}
	
	@Test
	public void shouldNotFoundDirectSuperclassOfObject() {
		assertThat(klassTypeElement.findDirectSuperclassOf(Object.class), nullValue());
	}
	
	@Test
	public void shouldBeConcreteClass() {
		assertThat(klassTypeElement.isConcreteClass(), equalTo(true));
	}
	
	@Test
	public void shouldNotBeConcreteClass() {
		WrappedTypeElement abstractClassTypeElement = typeElement(AbstractClass.class);
		
		assertThat(abstractClassTypeElement.isNotConcreteClass(), equalTo(true));
	}
	
	@Test
	public void shouldParentClassFirstTypeArgumentOfInteger() {
		WrappedTypeElement parentClassTypeElement = klassTypeElement.findDirectSuperclassOf(ParentClass.class);
		WrappedTypeElement typeArgument = parentClassTypeElement.firstTypeArgument();
		
		assertThat(typeArgument.qName(), equalTo(Integer.class.getName()));
	}
	
	private static MethodElementFilter ALL_METHODS = new MethodElementFilter() {
		@Override
		public boolean include(MethodElement element) {
			return true;
		}
	};
	
	private static SetterElementFilter ALL_SETTERS = new SetterElementFilter() {
		@Override
		public boolean include(SetterElement element) {
			return true;
		}
	};
}
