package org.robobinding;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robobinding.attribute.PropertyAttributeParser;
import org.robobinding.binder.BinderFactory;
import org.robobinding.viewattribute.impl.BindingAttributeMappingsProviderMap;
import org.robobinding.viewattribute.view.ViewListenersMap;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;

import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@RunWith(RobolectricTestRunner.class)
public class BinderFactoryTest {
    @Mock
    ViewListenersMap viewListenersMap;
    @Mock
    BindingAttributeMappingsProviderMap bindingAttributeMappingsProviderMap;
    @Mock
    PropertyAttributeParser propertyAttributeParser;
    @InjectMocks
    private BinderFactory binderFactory;
    
    @Before
    public void setUp() {
	MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void whenCreateActivityBinder_thenSuccessfullyWired() {
	Activity activity = new Activity();
	boolean withPreInitializingViews = false;
	
	ActivityBinder activityBinder = binderFactory.createActivityBinder(activity, withPreInitializingViews);
	
	assertNotNull(activityBinder);
    }
    
    @Test
    public void whenCreateDialogBinder_thenSuccessfullyWired() {
	Dialog dialog = new Dialog(new Activity());
	
	DialogBinder dialogBinder = binderFactory.createDialogBinder(dialog);
	
	assertNotNull(dialogBinder);
    }
    
    @Test
    public void whenCreateInternalViewBinder_thenSuccessfullyWired() {
	Context context = new Activity();
	
	InternalViewBinder internalViewBinder = binderFactory.createInternalViewBinder(context);
	
	assertNotNull(internalViewBinder);
    }
}
