package org.robobinding.widget;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

import java.lang.reflect.ParameterizedType;

import org.junit.Before;
import org.robobinding.viewattribute.ParameterizedTypeUtils;
import org.robobinding.viewattribute.ViewListeners;
import org.robobinding.viewattribute.ViewListenersAware;
import org.robobinding.viewattribute.event.EventViewAttribute;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public abstract class AbstractEventViewAttributeWithViewListenersAwareTest<ViewType extends View,
	EventViewAttributeType extends EventViewAttribute<? super ViewType>, ViewListenersType extends ViewListeners>
	extends AbstractEventViewAttributeTest<ViewType, EventViewAttributeType> {
    protected ViewListenersType viewListeners;

    @SuppressWarnings("unchecked")
    @Before
    public void initializeViewListeners() {
	assertThat(attribute, instanceOf(ViewListenersAware.class));
	ParameterizedType superclass = (ParameterizedType) getClass().getGenericSuperclass();
	viewListeners = ParameterizedTypeUtils.createTypeArgument(superclass, 2, view.getClass(), view);
	((ViewListenersAware<ViewListeners>) attribute).setViewListeners(viewListeners);
    }
}
