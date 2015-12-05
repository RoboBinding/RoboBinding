package org.robobinding.codegen.viewbinding;

import static org.hamcrest.Matchers.equalTo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.robobinding.codegen.apt.MethodElementFilter;
import org.robobinding.codegen.apt.element.AptTestHelper;
import org.robobinding.codegen.apt.element.MethodElement;
import org.robobinding.codegen.apt.element.SetterElement;
import org.robobinding.codegen.apt.element.WrappedTypeElement;

import com.google.common.collect.Lists;
import com.google.testing.compile.CompilationRule;

import java.util.Collections;
import java.util.List;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
@RunWith(Theories.class)
public class ViewBindingInfoBuilderTest {
	@Rule public final CompilationRule compilation = new CompilationRule();
	@Rule public final ExpectedException thrownException = ExpectedException.none();
	
	private final String viewBindingObjectTypeName = "ViewBinding$$VB";

	private AptTestHelper aptTestHelper;
	
	@Before
	public void setUp() {
		aptTestHelper = new AptTestHelper(compilation);
	}
	
	@Test
	public void givenValidImageViewBinding_whenBuildViewBindingInfo_thenReturnExpectedResult() {
		WrappedTypeElement typeElement = typeElementOf(ViewBindingWithVariousProperties.class);
		ViewBindingInfoBuilder builder = new ViewBindingInfoBuilder(typeElement, viewBindingObjectTypeName);
		
		ViewBindingInfo viewBindingInfo = builder.build();

		ViewBindingInfo expectedViewBindingInfo = new ViewBindingInfo(
				ViewBindingWithVariousProperties.class.getName(), 
				viewBindingObjectTypeName, 
				typeElementOf(ViewWithProperties.class), 
				Lists.newArrayList(new SimpleOneWayPropertyInfo(looseSetterOf(ViewWithProperties.PRIMITIVE_PROP)), 
						new SimpleOneWayPropertyInfo(looseSetterOf(ViewWithProperties.OBJECT_PROP))),
				Lists.newArrayList(new TwoWayPropertyInfo(ViewWithProperties.TWO_WAY_PROP, ViewWithProperties.CustomTwoWayProp.class.getCanonicalName())));
		Assert.assertThat(viewBindingInfo, equalTo(expectedViewBindingInfo));
	}
	
    
    private WrappedTypeElement typeElementOf(Class<?> type) {
    	return aptTestHelper.typeElementOf(type);
    }
    
    private SetterElement looseSetterOf(String propertyName) {
    	return aptTestHelper.looseSetterOf(ViewWithProperties.class, propertyName);
    }

    @DataPoints("invalidViewBindings")
    public static Class<?>[] invalidViewBindings = {
    		ViewBindingWithNonExistingProperty.class, 
    		ViewBindingWithNonExistingProperty.class,
    		ViewBindingWithAmbiguousSetter.class};
    
    @Theory
    public void givenInvalidViewBinding_whenBuildViewBindingInfo_thenThrowBindingPropertyCreationError(
    		@FromDataPoints("invalidViewBindings") Class<?> invalidViewBinding) {
    	WrappedTypeElement typeElement = typeElementOf(invalidViewBinding);
		ViewBindingInfoBuilder builder = new ViewBindingInfoBuilder(typeElement, viewBindingObjectTypeName);
		
		thrownException.expect(OneWayBindingPropertyGenerationException.class);
		builder.build();
    }
    
    @Test(expected=RuntimeException.class)
    public void givenNotCustomViewBindingSubclass_whenBuildViewBindingInfo_thenThrowUnsupportedViewBindingError() {
    	WrappedTypeElement typeElement = typeElementOf(NotCustomViewBindingSubclass.class);
		ViewBindingInfoBuilder builder = new ViewBindingInfoBuilder(typeElement, viewBindingObjectTypeName);
		
		builder.build();
    }
}
