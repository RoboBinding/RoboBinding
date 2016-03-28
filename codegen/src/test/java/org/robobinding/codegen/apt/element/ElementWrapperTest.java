package org.robobinding.codegen.apt.element;

import static org.hamcrest.Matchers.isA;
import static org.junit.Assert.assertThat;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.robobinding.codegen.ClassMockery;
import org.robobinding.codegen.apt.type.TypeMirrorWrapper;

import com.google.testing.compile.CompilationRule;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
@RunWith(Theories.class)
public class ElementWrapperTest {
	@ClassRule public static final CompilationRule compilation = new CompilationRule();
	
	@Rule
	public ExpectedException thrownException = ExpectedException.none();
	
	@Theory
	public void whenWrapSupportedElements_thenReturnCoorespondingWrappedInstance(
			@FromDataPoints("supportedElements") ElementToWrapped elementToWrapped) {
		Mockery context = new ClassMockery();
		final TypeMirrorWrapper typeWrapper = context.mock(TypeMirrorWrapper.class);
		context.checking(new Expectations() {{
			allowing(typeWrapper).wrap(with(any(TypeMirror.class))); will(returnValue(null));
		}});
		
		ElementWrapper wrapper = new ElementWrapper(typeWrapper, null, null, null);
		
		AbstractWrappedElement wrapped = wrapper.wrap(elementToWrapped.element);
		
		assertThat(wrapped, isA(elementToWrapped.wrappedType));
	}
	
	@DataPoints("supportedElements")
	public static ElementToWrapped[] supportedElements() {
		Elements elements = compilation.getElements();
		TypeElement typeElement = elements.getTypeElement(MethodsAndFields.class.getName());
		ExecutableElement methodElement = ElementFilter.methodsIn(typeElement.getEnclosedElements()).get(0);
		
		return new ElementToWrapped[] {
				a(typeElement).itsWrapped(WrappedTypeElement.class), 
				a(methodElement).itsWrapped(MethodElement.class)};
	}
	
	private static ElementToWrapped a(Element element) {
		return new ElementToWrapped(element);
	}

	@Theory
	public void whenWrapUnsupportedElements_thenThrowsError(
			@FromDataPoints("unsupportedElements") Element element) {
		thrownException.expect(UnsupportedOperationException.class);
		
		ElementWrapper wrapper = new ElementWrapper(null, null, null, null);
		
		wrapper.wrap(element);
	}
	
	
	@DataPoints("unsupportedElements")
	public static Element[] unsupportedElements() {
		Elements elements = compilation.getElements();
		PackageElement packageElement = elements.getPackageElement("java.lang");
		TypeElement typeElement = elements.getTypeElement(MethodsAndFields.class.getName());
		VariableElement variableElement = ElementFilter.fieldsIn(typeElement.getEnclosedElements()).get(0);
		
		return new Element[] {
				packageElement,
				variableElement};
	}
	
	private static class ElementToWrapped {
		public final Element element;
		public Class<AbstractWrappedElement> wrappedType;
		
		public ElementToWrapped(Element element) {
			this.element = element;
		}
		
		@SuppressWarnings("unchecked")
		public ElementToWrapped itsWrapped(Class<? extends AbstractWrappedElement> wrappedType) {
			this.wrappedType = (Class<AbstractWrappedElement>)wrappedType;
			return this;
		}
	}
}
