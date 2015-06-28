package org.robobinding.codegen.viewbinding;

import static org.hamcrest.Matchers.equalTo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.robobinding.codegen.typewrapper.DeclaredTypeElementWrapper;
import org.robobinding.codegen.typewrapper.TypeTestHelper;

import com.google.common.collect.Lists;
import com.google.testing.compile.CompilationRule;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class ViewBindingInfoBuilderTest {
	@Rule final public CompilationRule compilation = new CompilationRule();
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
    
    @Test(expected=OneWayBindingPropertyGenerationException.class)
    public void givenViewBindingWithNonExistingProperty_whenBuildViewBindingInfo_thenThrowBindingPropertyCreationError() {
		DeclaredTypeElementWrapper typeElement = declaredTypeElementOf(ViewBindingWithNonExistingProperty.class);
		ViewBindingInfoBuilder builder = new ViewBindingInfoBuilder(typeElement, viewBindingObjectTypeName);
		
		builder.build();
    }
    
    @Test(expected=OneWayBindingPropertyGenerationException.class)
    public void givenViewBindingWithNonExistingSetter_whenBuildViewBindingInfo_thenThrowBindingPropertyCreationError() {
		DeclaredTypeElementWrapper typeElement = declaredTypeElementOf(ViewBindingWithNonExistingSetter.class);
		ViewBindingInfoBuilder builder = new ViewBindingInfoBuilder(typeElement, viewBindingObjectTypeName);
		
		builder.build();
    }
    
    @Test(expected=RuntimeException.class)
    public void givenNotCustomViewBindingSubclass_whenBuildViewBindingInfo_thenThrowUnsupportedViewBindingError() {
		DeclaredTypeElementWrapper typeElement = declaredTypeElementOf(NotCustomViewBindingSubclass.class);
		ViewBindingInfoBuilder builder = new ViewBindingInfoBuilder(typeElement, viewBindingObjectTypeName);
		
		builder.build();
    }
}
