package robobinding.value;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.easymock.EasyMock;
import org.junit.Test;

/**
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 *
 */
public final class ValueHoldersTest
{
	@Test
	public void testEquityTestingHolderSendsProperEvents()
	{
		ValueModel<Object> holder = ValueHolders.create();

		Object obj1 = new Integer(1);
		Object obj2a = new Integer(2);
		Object obj2b = new Integer(2);
		
		testValueChangeSendsProperEvent(holder, null, obj1, true);
		testValueChangeSendsProperEvent(holder, obj1, null, true);
		testValueChangeSendsProperEvent(holder, obj1, obj1, false);
		testValueChangeSendsProperEvent(holder, obj1, obj2a, true);
		testValueChangeSendsProperEvent(holder, obj2a, obj2b, false); // equals
		testValueChangeSendsProperEvent(holder, null, null, false);
	}
	@Test
	public void testIdentityTestingHolderSendsProperEvents()
	{
		ValueModel<Object> holder = ValueHolders.create(null, true);

		Object obj1 = new Integer(1);
		Object obj2a = new Integer(2);
		Object obj2b = new Integer(2);
		testValueChangeSendsProperEvent(holder, null, obj1, true);
		testValueChangeSendsProperEvent(holder, obj1, null, true);
		testValueChangeSendsProperEvent(holder, obj1, obj1, false);
		testValueChangeSendsProperEvent(holder, obj1, obj2a, true);
		testValueChangeSendsProperEvent(holder, obj2a, obj2b, true); // !=
		testValueChangeSendsProperEvent(holder, null, null, false);
	}
	private <T> void testValueChangeSendsProperEvent(ValueModel<T> valueModel, T oldValue, T newValue, boolean eventExpected)
	{
		valueModel.setValue(oldValue);
		
		PropertyChangeListener mockListener = EasyMock.createMock(PropertyChangeListener.class);
		if(eventExpected)
		{
			mockListener.propertyChange(PropertyChangeEventEquals.eqPropertyChangeEvent(createPropertyChangeEvent(oldValue,newValue)));
		}
		EasyMock.replay(mockListener);

		valueModel.addValueChangeListener(mockListener);

		valueModel.setValue(newValue);

		EasyMock.verify(mockListener);
		EasyMock.reset(mockListener);
	}
	private PropertyChangeEvent createPropertyChangeEvent(Object oldValue, Object newValue)
	{
		return new PropertyChangeEvent(this, null, oldValue, newValue);
	}

}
