/**
 * Copyright 2012 Cheng Wei, Robert Taylor
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 */
package org.robobinding.viewattribute.impl;

import static com.google.common.collect.Maps.newHashMap;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.robobinding.BindingContext;
import org.robobinding.viewattribute.ViewAttribute;
import org.robobinding.viewattribute.view.ViewListeners;
import org.robobinding.viewattribute.view.ViewListenersAware;

import android.content.Context;
import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@RunWith(MockitoJUnitRunner.class)
public class ViewListenersProviderTest {
    @Mock
    private ViewSubclass view;

    private ViewListenersProvider viewListenersProvider;

    @Before
    public void setUp() {
	Map<Class<? extends View>, Class<? extends ViewListeners>> viewToViewListenersMap = newHashMap();
	viewToViewListenersMap.put(ViewSubclass.class, ViewListenersSubclass.class);
	viewListenersProvider = new ViewListenersProvider(viewToViewListenersMap);
    }

    @Test
    public void whenInjectNonViewListenersAwareAttribute_thenSafelyIgnored() {
	viewListenersProvider.injectIfRequired(new NonViewListenersAwareAttribute(), view);
    }
    
    @Test
    public void whenInjectViewListenersAwareAttribute_thenViewListenersSubclassIsInjected() {
	ViewListenersAwareAttribute viewAttribute = new ViewListenersAwareAttribute();
	
	viewListenersProvider.injectIfRequired(viewAttribute, view);
	
	assertThat(viewAttribute.viewListeners, instanceOf(ViewListenersSubclass.class));
    }
    
    @Test
    public void whenInjectViewListenersSubclassAwareAttribute_thenViewListenersSubclassInjected() {
	ViewListenersSubclassAwareAttribute viewAttribute = new ViewListenersSubclassAwareAttribute();
	
	viewListenersProvider.injectIfRequired(viewAttribute, view);
	
	assertThat(viewAttribute.viewListeners, instanceOf(ViewListenersSubclass.class));
    }

    @Test
    public void whenInjectViewListenersAwareAttributeAgain_thenTheSameViewListenersInstanceIsInjected() {
	ViewListenersAwareAttribute viewAttribute = new ViewListenersAwareAttribute();
	
	viewListenersProvider.injectIfRequired(viewAttribute, view);
	ViewListeners viewListeners1 = viewAttribute.viewListeners;

	viewListenersProvider.injectIfRequired(viewAttribute, view);
	ViewListeners viewListeners2 = viewAttribute.viewListeners;

	assertThat(viewListeners1, sameInstance(viewListeners2));
    }
    
    @Test
    public void whenInjectTwoDifferentViewListenersAwareAttributesWithTheSameView_thenTheSameViewListenersInstanceIsInjectedForBoth() {
	ViewListenersAwareAttribute viewAttribute = new ViewListenersAwareAttribute();
	viewListenersProvider.injectIfRequired(viewAttribute, view);

	ViewListenersSubclassAwareAttribute viewAttributeSubclass = new ViewListenersSubclassAwareAttribute();
	viewListenersProvider.injectIfRequired(viewAttributeSubclass, view);

	assertThat(viewAttribute.viewListeners, sameInstance(viewAttributeSubclass.viewListeners));
    }

    private static class NonViewListenersAwareAttribute implements ViewAttribute  {
	@Override
	public void bindTo(BindingContext bindingContext) {
	}
	
	@Override
	public void preInitializeView(BindingContext bindingContext) {
	}
    }

    private abstract static class ViewAttributeWithViewListenersType implements ViewAttribute {
        public ViewListeners viewListeners;
        
        @Override
        public void bindTo(BindingContext bindingContext) {
        }
        
        @Override
        public void preInitializeView(BindingContext bindingContext) {
        }
        
        public abstract Class<? extends ViewListeners> getViewListenersType();
    }

    private static class ViewListenersAwareAttribute extends ViewAttributeWithViewListenersType implements ViewListenersAware<ViewListeners> {
	@Override
	public void setViewListeners(ViewListeners viewListeners) {
	    this.viewListeners = viewListeners;
	}
	
	@Override
	public Class<? extends ViewListeners> getViewListenersType() {
	    return ViewListeners.class;
	}

    }

    private static class ViewListenersSubclassAwareAttribute extends ViewAttributeWithViewListenersType implements
	    ViewListenersAware<ViewListenersSubclass> {
	@Override
	public void setViewListeners(ViewListenersSubclass viewListeners) {
	    this.viewListeners = viewListeners;
	}
	
	@Override
	public Class<? extends ViewListeners> getViewListenersType() {
	    return ViewListenersSubclass.class;
	}

    }

    public static class ViewListenersSubclass extends ViewListeners {
	public ViewListenersSubclass(ViewSubclass view) {
	    super(view);
	}
    }
    
    public static class ViewSubclass extends View {
	public ViewSubclass(Context context) {
	    super(context);
	}
    }
}
