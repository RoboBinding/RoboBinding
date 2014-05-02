package org.robobinding.viewattribute.impl;

import org.robobinding.viewattribute.ResolvedGroupAttributesFactory;
import org.robobinding.viewattribute.StandaloneViewAttributeInitializer;
import org.robobinding.viewattribute.ViewListenersInjector;
import org.robobinding.viewattribute.view.ViewListenersMap;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ViewAttributeInitializerFactory {
    private final ViewListenersMap viewListenersMap;

    public ViewAttributeInitializerFactory(ViewListenersMap viewListenersMap) {
	this.viewListenersMap = viewListenersMap;
    }

    public ViewAttributeInitializer create(View view) {
	ViewListenersInjector viewListenersInjector = new ViewListenersProvider(viewListenersMap);
	return new ViewAttributeInitializer(
		new StandaloneViewAttributeInitializer(viewListenersInjector, view),
		new ResolvedGroupAttributesFactory());
    }
}
