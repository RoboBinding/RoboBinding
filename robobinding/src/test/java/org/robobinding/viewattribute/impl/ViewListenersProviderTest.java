package org.robobinding.viewattribute.impl;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.robobinding.BindingContext;
import org.robobinding.viewattribute.ViewAttribute;
import org.robobinding.viewattribute.view.ViewListeners;
import org.robobinding.viewattribute.view.ViewListenersAware;
import org.robobinding.viewattribute.view.ViewListenersMapBuilder;

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
    private View view;
    @Mock
    private ViewSubclass viewSubclass;

    private ViewListenersProvider viewListenersProvider;

    @Before
    public void setUp() {
	ViewListenersMapBuilder viewListenersMapBuilder = new ViewListenersMapBuilder();
	viewListenersMapBuilder.put(ViewSubclass.class, ViewListenersSubclass.class);
	viewListenersProvider = new ViewListenersProvider(viewListenersMapBuilder.build());
    }

    @Test
    public void whenInjectNonViewListenersAwareAttribute_thenSafelyIgnored() {
	viewListenersProvider.injectIfRequired(new NonViewListenersAwareAttribute(), view);
    }
    
    @Test
    public void whenInjectViewListenersAwareAttribute_thenViewListenersIsInjected() {
	ViewListenersAwareAttribute viewAttribute = new ViewListenersAwareAttribute();
	
	viewListenersProvider.injectIfRequired(viewAttribute, view);
	
	assertThat(viewAttribute.viewListeners, instanceOf(ViewListeners.class));
    }
    
    @Test
    public void whenInjectViewListenersSubclassAwareAttribute_thenViewListenersSubclassIsInjected() {
	ViewListenersSubclassAwareAttribute viewAttribute = new ViewListenersSubclassAwareAttribute();
	
	viewListenersProvider.injectIfRequired(viewAttribute, viewSubclass);
	
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
    public void whenInjectTwoDifferentViewListenersAwareAttributesThroughTheSameViewSubclass_thenTheSameViewSubclassListenersInstanceIsInjectedForBoth() {
	ViewListenersAwareAttribute viewAttribute = new ViewListenersAwareAttribute();
	viewListenersProvider.injectIfRequired(viewAttribute, viewSubclass);

	ViewListenersSubclassAwareAttribute viewAttributeSubclass = new ViewListenersSubclassAwareAttribute();
	viewListenersProvider.injectIfRequired(viewAttributeSubclass, viewSubclass);

	assertThat(viewAttribute.viewListeners, sameInstance(viewAttributeSubclass.viewListeners));
    }
    
    @Test(expected = RuntimeException.class)
    public void whenInjectViewListenersSubclassAwareAttributeThroughIncorrectView_thenThrowExceptionWithDetailedMessage() {
	ViewListenersSubclassAwareAttribute viewAttributeSubclass = new ViewListenersSubclassAwareAttribute();
	viewListenersProvider.injectIfRequired(viewAttributeSubclass, view);
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
