package org.robobinding.viewattribute;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.robobinding.viewattribute.MockMultiTypePropertyViewAttributeConfigBuilder.aMultiTypePropertyViewAttributeConfig;

import org.robobinding.BindingContext;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class MultiTypePropertyViewAttributeTest extends ViewAttributeContractTest<AbstractMultiTypePropertyViewAttribute<View>> {
    @Override
    protected AbstractMultiTypePropertyViewAttribute<View> throwsExceptionDuringPreInitializingView() {
	return new ThrowsExceptionDuringPreInitializingView();
    }

    @Override
    protected AbstractMultiTypePropertyViewAttribute<View> throwsExceptionDuringBinding() {
	return new ThrowsExceptionDuringBinding();
    }

    private abstract static class AbstractMultiTypePropertyViewAttributeWithDefaultConfig extends AbstractMultiTypePropertyViewAttribute<View> {
	public AbstractMultiTypePropertyViewAttributeWithDefaultConfig() {
	    initialize(aMultiTypePropertyViewAttributeConfig(mock(View.class), "propertyName"));
	}
    }

    private static class ThrowsExceptionDuringPreInitializingView extends AbstractMultiTypePropertyViewAttributeWithDefaultConfig {
	@Override
	protected AbstractPropertyViewAttribute<View, ?> createPropertyViewAttribute(Class<?> propertyType) {
	    return null;
	}
    }

    private static class ThrowsExceptionDuringBinding extends AbstractMultiTypePropertyViewAttributeWithDefaultConfig {
	@Override
	protected AbstractPropertyViewAttribute<View, ?> createPropertyViewAttribute(Class<?> propertyType) {
	    @SuppressWarnings("unchecked")
	    AbstractPropertyViewAttribute<View, ?> propertyViewAttribute = mock(AbstractPropertyViewAttribute.class);
	    doThrow(new RuntimeException()).when(propertyViewAttribute).bindTo(any(BindingContext.class));
	    return propertyViewAttribute;
	}
    }
}
