package org.robobinding.dynamicbinding;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.robobinding.property.PropertyDescriptor.Setter;

import android.content.Context;
import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ViewPropertySetterLocatorTest {
    private ViewPropertySetterLocator setterLocator;
    
    @Before
    public void setUp() {
	setterLocator = new ViewPropertySetterLocator(CustomView.class);
    }
    
    @Test(expected = RuntimeException.class)
    public void whenGetNonExistingAttribute_thenThrowException() {
	setterLocator.get("nonExistingAttribute");
    }
    
    @Test(expected = RuntimeException.class)
    public void whenGetReadOnlyAttribute_thenThrowException() {
	setterLocator.get(CustomView.READ_ONLY_ATTRIBUTE);
    }
    
    @Test
    public void whenGetWritableAttribute_thenReturnSetter() {
	Setter setter = setterLocator.get(CustomView.WRITABLE_ATTRIBUTE);
	
	assertNotNull(setter);
    }
    
    public static class CustomView extends View {
	private static final String READ_ONLY_ATTRIBUTE = "readOnlyAttribute";
	private static final String WRITABLE_ATTRIBUTE = "writableAttribute";
	
	public Object getReadOnlyAttribute() {
	    return null;
	}
	
	public void setWritableAttribute(Object o) {
	    
	}

	public CustomView() {
	    super(mock(Context.class));
	}
	
    }
}
