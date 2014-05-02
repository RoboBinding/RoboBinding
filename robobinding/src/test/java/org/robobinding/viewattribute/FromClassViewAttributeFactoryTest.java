package org.robobinding.viewattribute;

import static org.junit.Assert.assertNotNull;
import static org.robobinding.viewattribute.FromClassViewAttributeFactory.viewAttributeFactoryForClass;

import org.junit.Test;
import org.robobinding.BindingContext;
import org.robobinding.viewattribute.view.ViewVisibilityAttribute;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class FromClassViewAttributeFactoryTest {

    @Test
    public void shouldCreateNewInstanceFromClass() {
	ViewAttributeFactory<ViewVisibilityAttribute> viewAttributeFactory = viewAttributeFactoryForClass(ViewVisibilityAttribute.class);

	ViewVisibilityAttribute visibility = viewAttributeFactory.create();

	assertNotNull(visibility);
    }

    @Test(expected = RuntimeException.class)
    public void givenSuppliedClassIsAbstract_shouldThrowRuntimeException() {
	viewAttributeFactoryForClass(AbstractViewAttribute.class).create();
    }

    @Test(expected = RuntimeException.class)
    public void givenSuppliedClassIsNotVisible_shouldThrowRuntimeException() {
	viewAttributeFactoryForClass(NonVisibleViewAttribute.class).create();
    }

    public static class VisibilityAttributeFactory implements ViewAttributeFactory<ViewVisibilityAttribute> {
	@Override
	public ViewVisibilityAttribute create() {
	    return new ViewVisibilityAttribute();
	}
    }

   public abstract static class AbstractViewAttribute implements ViewAttribute {
    }

    private static class NonVisibleViewAttribute implements ViewAttribute {
	@Override
	public void bindTo(BindingContext bindingContext) {
	}

	@Override
	public void preInitializeView(BindingContext bindingContext) {
	}
    }
}
