package org.robobinding.viewattribute;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.robobinding.attribute.MockCommandAttributeBuilder.aCommandAttribute;
import static org.robobinding.attribute.MockValueModelAttributeBuilder.aValueModelAttribute;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.robobinding.attribute.CommandAttribute;
import org.robobinding.attribute.ValueModelAttribute;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
@RunWith(MockitoJUnitRunner.class)
public class StandaloneViewAttributeInitializerTest {
    @Mock
    private View view;
    @Mock
    private ViewListenersInjector viewListenersInjector;
    @InjectMocks
    private StandaloneViewAttributeInitializer viewAttributeInitializer;

    @Test
    public void whenInitializePropertyViewAttribute_thenTheAttributeIsCorrectlyInitialized() {
	ValueModelAttribute attribute = aValueModelAttribute();
	@SuppressWarnings("unchecked")
	AbstractPropertyViewAttribute<View, Object> viewAttribute = mock(AbstractPropertyViewAttribute.class);

	viewAttributeInitializer.initializePropertyViewAttribute(viewAttribute, attribute);

	verify(viewAttribute).initialize(eq(new PropertyViewAttributeConfig<View>(view, attribute)));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void whenInitializePropertyViewAttribute_thenViewListenersInjectorIsCalled() {
	@SuppressWarnings("rawtypes")
	AbstractPropertyViewAttribute viewAttribute = mock(AbstractPropertyViewAttribute.class);

	viewAttributeInitializer.initializePropertyViewAttribute(viewAttribute, null);

	verify(viewListenersInjector).injectIfRequired(viewAttribute, view);
    }

    @Test
    public void whenInitializeMultiTypePropertyViewAttribute_thenTheAttributeIsCorrectlyInitialized() {
	ValueModelAttribute attribute = aValueModelAttribute();
	@SuppressWarnings("unchecked")
	AbstractMultiTypePropertyViewAttribute<View> viewAttribute = mock(AbstractMultiTypePropertyViewAttribute.class);

	viewAttributeInitializer.initializePropertyViewAttribute(viewAttribute, attribute);

	verify(viewAttribute).initialize(eq(new MultiTypePropertyViewAttributeConfig<View>(view, attribute, viewListenersInjector)));
    }

    @Test
    public void whenInitializeCommandViewAttribute_thenTheAttributeIsCorrectlyInitialized() {
	CommandAttribute attribute = aCommandAttribute();
	@SuppressWarnings({ "unchecked" })
	AbstractCommandViewAttribute<View> viewAttribute = mock(AbstractCommandViewAttribute.class);

	viewAttributeInitializer.initializeCommandViewAttribute(viewAttribute, attribute);

	verify(viewAttribute).initialize(new CommandViewAttributeConfig<View>(view, attribute));
    }

    @Test
    public void whenInitializeCommandViewAttribute_thenViewListenersInjectorIsCalled() {
	@SuppressWarnings("unchecked")
	AbstractCommandViewAttribute<View> viewAttribute = mock(AbstractCommandViewAttribute.class);

	viewAttributeInitializer.initializeCommandViewAttribute(viewAttribute, null);

	verify(viewListenersInjector).injectIfRequired(viewAttribute, view);
    }
}
