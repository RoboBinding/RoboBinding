package org.robobinding.viewattribute.impl;

import static org.mockito.Matchers.isA;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.robobinding.viewattribute.AbstractGroupedViewAttribute;
import org.robobinding.viewattribute.ChildViewAttributesBuilder;
import org.robobinding.viewattribute.ResolvedGroupAttributesFactory;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@RunWith(MockitoJUnitRunner.class)
public class ViewAttributeInitializerTest {
    @Mock
    private ResolvedGroupAttributesFactory resolvedGroupAttributesFactory;
    @Mock
    private View view;

    @SuppressWarnings("unchecked")
    @Test
    public void whenInitializeGroupedViewAttribute_thenTheAttributeIsCorrectlyInitialized() {
	AbstractGroupedViewAttribute<View> viewAttribute = mock(AbstractGroupedViewAttribute.class);

	ViewAttributeInitializer viewAttributeInitializer = new ViewAttributeInitializer(null, resolvedGroupAttributesFactory);

	viewAttribute = viewAttributeInitializer.initializeGroupedViewAttribute(view, viewAttribute, null);

	verify(viewAttribute).initialize(same(view), isA(ChildViewAttributesBuilder.class));
    }
}
