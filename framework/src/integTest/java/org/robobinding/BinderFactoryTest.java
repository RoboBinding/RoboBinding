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
import org.robobinding.viewattribute.RandomValues;
import org.robobinding.viewattribute.ViewListenersMap;
import org.robobinding.viewattribute.impl.BindingAttributeMappingsProviderMap;

import android.app.Activity;
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
    public void whenCreateViewBinder_thenSuccessfullyWired() {
	Context context = new Activity();
	
	ViewBinder viewBinder = binderFactory.createViewBinder(context, RandomValues.trueOrFalse());
	
	assertNotNull(viewBinder);
    }
}
