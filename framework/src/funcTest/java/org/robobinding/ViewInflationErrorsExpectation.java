package org.robobinding;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.robobinding.attribute.MissingRequiredAttributesException;
import org.robobinding.binder.ViewBindingErrors;
import org.robobinding.binder.ViewInflationErrors;
import org.robobinding.viewattribute.AttributeBindingException;

import android.view.View;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ViewInflationErrorsExpectation {
    private Class<?> viewType;
    private List<AttributeResolutionErrorExpectation> attributeResolutionErrorExpectations;
    private List<MissingRequiredAttributesResolutionErrorExpectation> missingRequiredAttributesResolutionErrorExpectations;
    private List<AttributeBindingErrorExpectation> attributeBindingErrorExpectations;

    private ViewInflationErrorsExpectation(ViewInflationErrorsExpectation.Builder builder) {
		this.viewType = builder.viewType;
		this.attributeResolutionErrorExpectations = builder.attributeResolutionErrorExpectations;
		this.missingRequiredAttributesResolutionErrorExpectations = builder.missingRequiredAttributesResolutionErrorExpectations;
		this.attributeBindingErrorExpectations = builder.attributeBindingErrorExpectations;
    }

    public static Builder aBindingViewInflationErrorExpectationOf(Class<? extends View> viewType) {
    	return new Builder(viewType);
    }

    public void meet(ViewInflationErrors error) {
		assertThat(error.getView(), is(instanceOf(viewType)));
	
		assertResolutionErrors(error.getResolutionErrors());
		assertBindingErrors(error.getBindingErrors());
    }

    private void assertResolutionErrors(ViewResolutionErrors error) {
		assertThat(getViewName() + " has unmatched resolution errors.", error.numErrors(), is(expectedNumResolutionErrors()));
	
		assertAttributeResolutionErrors(error.getAttributeErrors());
		assertMissingRequiredAttributesResolutionErrors(error.getMissingRequiredAttributeErrors());
    }

    private int expectedNumResolutionErrors() {
    	return attributeResolutionErrorExpectations.size() + missingRequiredAttributesResolutionErrorExpectations.size();
    }

    private void assertAttributeResolutionErrors(Collection<AttributeResolutionException> attributeResolutionErrors) {
		assertThat(getViewName() + " has unmatched attribute resolution errors.", attributeResolutionErrors.size(),
			is(expectedNumAttributeResolutionErrors()));
	
		int index = 0;
		for (AttributeResolutionException error : attributeResolutionErrors) {
		    AttributeResolutionErrorExpectation errorExpectation = attributeResolutionErrorExpectations.get(index);
		    errorExpectation.meet(error);
		    index++;
		}
    }

    private int expectedNumAttributeResolutionErrors() {
    	return attributeResolutionErrorExpectations.size();
    }

    private void assertMissingRequiredAttributesResolutionErrors(Collection<MissingRequiredAttributesException> missingRequiredAttributeErrors) {
		assertThat(getViewName() + " has unmatched missing required attributes resolution errors.", missingRequiredAttributeErrors.size(),
			is(expectedNumMissingRequiredAttributeErrors()));
	
		int index = 0;
		for (MissingRequiredAttributesException error : missingRequiredAttributeErrors) {
		    MissingRequiredAttributesResolutionErrorExpectation errorExpectation = missingRequiredAttributesResolutionErrorExpectations.get(index);
		    errorExpectation.meet(error);
		    index++;
		}
    }

    private int expectedNumMissingRequiredAttributeErrors() {
    	return missingRequiredAttributesResolutionErrorExpectations.size();
    }

    private void assertBindingErrors(ViewBindingErrors error) {
		try {
		    assertThat(getViewName() + " has unmatched binding errors.", error.numErrors(), is(expectedNumAttributeBindingErrors()));
		} catch (AssertionError e) {
		    throw e;
		}
	
		int index = 0;
		for (AttributeBindingException bindingError : error.getAttributeErrors()) {
		    AttributeBindingErrorExpectation errorExpectation = attributeBindingErrorExpectations.get(index);
		    errorExpectation.meet(bindingError);
		    index++;
		}
    }

    private int expectedNumAttributeBindingErrors() {
    	return attributeBindingErrorExpectations.size();
    }

    private String getViewName() {
    	return viewType.getSimpleName();
    }

    public static class Builder {
		private Class<? extends View> viewType;
		private List<AttributeResolutionErrorExpectation> attributeResolutionErrorExpectations;
		private List<MissingRequiredAttributesResolutionErrorExpectation> missingRequiredAttributesResolutionErrorExpectations;
		private List<AttributeBindingErrorExpectation> attributeBindingErrorExpectations;
	
		private Builder(Class<? extends View> viewType) {
		    this.viewType = viewType;
		    attributeResolutionErrorExpectations = Lists.newArrayList();
		    missingRequiredAttributesResolutionErrorExpectations = Lists.newArrayList();
		    attributeBindingErrorExpectations = Lists.newArrayList();
		}
	
		public Builder withAttributeResolutionErrorOf(String attributeName) {
		    attributeResolutionErrorExpectations.add(new AttributeResolutionErrorExpectation(attributeName));
		    return this;
		}
	
		public Builder withMissingRequiredAttributesResolutionErrorOf(String... attributeNames) {
		    missingRequiredAttributesResolutionErrorExpectations.add(new MissingRequiredAttributesResolutionErrorExpectation(attributeNames));
		    return this;
		}
	
		public Builder withAttributeBindingErrorOf(String attributeName) {
		    attributeBindingErrorExpectations.add(new AttributeBindingErrorExpectation(attributeName));
		    return this;
		}
	
		public ViewInflationErrorsExpectation build() {
		    return new ViewInflationErrorsExpectation(this);
		}
    }

    public static class AttributeResolutionErrorExpectation {
		private String attributeName;
	
		public AttributeResolutionErrorExpectation(String attributeName) {
		    this.attributeName = attributeName;
		}
	
		public void meet(AttributeResolutionException e) {
		    assertThat(e.getAttributeName(), is(attributeName));
		}
    }

    public static class MissingRequiredAttributesResolutionErrorExpectation {
		private Set<String> missingAttributes;
	
		public MissingRequiredAttributesResolutionErrorExpectation(String[] missingAttributes) {
		    this.missingAttributes = Sets.newHashSet(missingAttributes);
		}
	
		public void meet(MissingRequiredAttributesException e) {
		    assertThat(Sets.newHashSet(e.getMissingAttributes()), is(missingAttributes));
		}
	    }
	
	    public static class AttributeBindingErrorExpectation {
		private String attributeName;
	
		public AttributeBindingErrorExpectation(String attributeName) {
		    this.attributeName = attributeName;
		}
	
		public void meet(AttributeBindingException e) {
		    assertThat(e.getAttributeName(), is(attributeName));
		}
    }
}