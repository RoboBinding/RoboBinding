package org.robobinding.binder;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.robobinding.binder.MockBindingAttributeMappingsImplBuilder.aBindingAttributeMappingsImpl;
import static org.robobinding.binder.MockBindingAttributeMappingsImplBuilder.aCommandViewAttribute;
import static org.robobinding.binder.MockBindingAttributeMappingsImplBuilder.aGroupedViewAttribute;
import static org.robobinding.binder.MockBindingAttributeMappingsImplBuilder.aPropertyViewAttribute;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.robobinding.PendingAttributesForView;
import org.robobinding.ViewResolutionErrors;
import org.robobinding.viewattribute.ViewAttribute;
import org.robobinding.viewattribute.impl.BindingAttributeMappingsImpl;

import android.view.View;

import com.google.common.collect.Sets;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ByBindingAttributeMappingsResolverTest {
    private PendingAttributesForView pendingAttributesForView;

    @Before
    public void setUp() {
	pendingAttributesForView = new MockPendingAttributesForView();
    }

    @Test
    public void givenAPropertyAttribute_whenResolve_thenAResolvedPropertyViewAttributeShouldBeReturned() {
	String propertyAttribute = "propertyAttribute";
	ByBindingAttributeMappingsResolver byBindingAttributeMappingsResolver = newByBindingAttributeMappingsResolver(aBindingAttributeMappingsImpl()
		.withPropertyAttribute(propertyAttribute).build());

	Collection<ViewAttribute> viewAttributes = byBindingAttributeMappingsResolver.resolve(pendingAttributesForView);

	assertThat(Sets.newHashSet(viewAttributes), equalTo(Sets.<ViewAttribute> newHashSet(aPropertyViewAttribute(propertyAttribute))));
    }

    @Test
    public void givenACommandAttribute_whenResolve_thenAResolvedCommandViewAttributeShouldBeReturned() {
	String commandAttribute = "commandAttribute";
	ByBindingAttributeMappingsResolver byBindingAttributeMappingsResolver = newByBindingAttributeMappingsResolver(aBindingAttributeMappingsImpl()
		.withCommandAttribute(commandAttribute).build());

	Collection<ViewAttribute> viewAttributes = byBindingAttributeMappingsResolver.resolve(pendingAttributesForView);

	assertThat(Sets.newHashSet(viewAttributes), equalTo(Sets.<ViewAttribute> newHashSet(aCommandViewAttribute(commandAttribute))));
    }

    @Test
    public void givenAAttributeGroup_whenResolve_thenAResolvedGroupedViewAttributeShouldBeReturned() {
	String[] attributeGroup = {"group_attribute1", "group_attribute2"};
	ByBindingAttributeMappingsResolver byBindingAttributeMappingsResolver = newByBindingAttributeMappingsResolver(aBindingAttributeMappingsImpl()
		.withAttributeGroup(attributeGroup).build());

	Collection<ViewAttribute> viewAttributes = byBindingAttributeMappingsResolver.resolve(pendingAttributesForView);

	assertThat(Sets.newHashSet(viewAttributes), equalTo(Sets.<ViewAttribute> newHashSet(aGroupedViewAttribute(attributeGroup))));
    }

    private ByBindingAttributeMappingsResolver newByBindingAttributeMappingsResolver(BindingAttributeMappingsImpl<View> bindingAttributeMappings) {
	return new ByBindingAttributeMappingsResolver(bindingAttributeMappings);
    }

    private static class MockPendingAttributesForView implements PendingAttributesForView {
	@Override
	public void resolveAttributeGroupIfExists(String[] attributeGroup, AttributeGroupResolver attributeGroupResolver) {
	    attributeGroupResolver.resolve(null, attributeGroup, null);
	}

	@Override
	public void resolveAttributeIfExists(String attribute, AttributeResolver attributeResolver) {
	    attributeResolver.resolve(null, attribute, null);
	}

	@Override
	public View getView() {
	    throw new UnsupportedOperationException();
	}

	@Override
	public ViewResolutionErrors getResolutionErrors() {
	    throw new UnsupportedOperationException();
	}

	@Override
	public boolean isEmpty() {
	    throw new UnsupportedOperationException();
	}
    }
}
