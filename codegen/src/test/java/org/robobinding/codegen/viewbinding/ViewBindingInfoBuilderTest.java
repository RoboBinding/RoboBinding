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
import org.robobinding.codegen.typewrapper.DeclaredTypeElementWrapper;
import org.robobinding.codegen.typewrapper.TypeTestHelper;

import com.google.common.collect.Lists;
import com.google.testing.compile.CompilationRule;

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

	private TypeTestHelper typeTestHelper;
	
	@Before
	public void setUp() {
		typeTestHelper = new TypeTestHelper(compilation);
	}
	
	@Test
	public void givenValidImageViewBinding_whenBuildViewBindingInfo_thenReturnExpectedResult() {
		DeclaredTypeElementWrapper typeElement = declaredTypeElementOf(ViewBindingWithVariousProperties.class);
		ViewBindingInfoBuilder builder = new ViewBindingInfoBuilder(typeElement, viewBindingObjectTypeName);
		
		ViewBindingInfo viewBindingInfo = builder.build();
		
		ViewBindingInfo expectedViewBindingInfo = new ViewBindingInfo(
				ViewBindingWithVariousProperties.class.getName(), 
				viewBindingObjectTypeName, 
				ViewWithProperties.class, 
				Lists.newArrayList(new SimpleOneWayPropertyInfo(int.class, ViewWithProperties.PRIMITIVE_PROP), 
						new SimpleOneWayPropertyInfo(Object.class, ViewWithProperties.OBJECT_PROP)));
		Assert.assertThat(viewBindingInfo, equalTo(expectedViewBindingInfo));
	}
	
    
    private DeclaredTypeElementWrapper declaredTypeElementOf(Class<?> type) {
    	return typeTestHelper.declaredTypeElementOf(type);
    }
    
    @DataPoints("invalidViewBindings")
    public static Class<?>[] invalidViewBindings = {
    		ViewBindingWithNonExistingProperty.class, 
    		ViewBindingWithNonExistingProperty.class,
    		/*ViewBindingWithAmbiguousSetter.class*/};
    
    @Theory
    public void givenInvalidViewBinding_whenBuildViewBindingInfo_thenThrowBindingPropertyCreationError(
    		@FromDataPoints("invalidViewBindings") Class<?> invalidViewBinding) {
		DeclaredTypeElementWrapper typeElement = declaredTypeElementOf(invalidViewBinding);
		ViewBindingInfoBuilder builder = new ViewBindingInfoBuilder(typeElement, viewBindingObjectTypeName);
		
		thrownException.expect(OneWayBindingPropertyGenerationException.class);
		builder.build();
    }
    
    @Test(expected=RuntimeException.class)
    public void givenNotCustomViewBindingSubclass_whenBuildViewBindingInfo_thenThrowUnsupportedViewBindingError() {
		DeclaredTypeElementWrapper typeElement = declaredTypeElementOf(NotCustomViewBindingSubclass.class);
		ViewBindingInfoBuilder builder = new ViewBindingInfoBuilder(typeElement, viewBindingObjectTypeName);
		
		builder.build();
    }
}
