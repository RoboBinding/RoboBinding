package org.robobinding.viewattribute.view;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

import java.lang.reflect.ParameterizedType;

import org.junit.Before;
import org.robobinding.viewattribute.AbstractCommandViewAttribute;
import org.robobinding.viewattribute.AbstractCommandViewAttributeTest;
import org.robobinding.viewattribute.ParameterizedTypeUtils;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public abstract class AbstractCommandViewAttributeWithViewListenersAwareTest<ViewType extends View, 
	CommandViewAttributeType extends AbstractCommandViewAttribute<? super ViewType>, ViewListenersType extends ViewListeners>
	extends AbstractCommandViewAttributeTest<ViewType, CommandViewAttributeType> {
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
