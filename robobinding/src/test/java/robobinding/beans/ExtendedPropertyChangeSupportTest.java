package robobinding.beans;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import robobinding.value.PropertyChangeEventEquals;
import robobinding.value.PropertyChangeReport;

import com.google.common.collect.Lists;

/**
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 * 
 */
public class ExtendedPropertyChangeSupportTest
{
	private String one;
	private String two;
	private List<String> emptyList1;
	private List<String> emptyList2;
	private List<String> list1a;
	private List<String> list1b;
	@Before
	public void setUp() throws Exception
	{
		one = "one";
		two = "two";
		emptyList1 = Lists.newArrayList();
		emptyList2 = Lists.newArrayList();
		list1a = Lists.newArrayList();
		list1a.add(one);
		list1b = Lists.newArrayList();
		list1b.add(one);
	}
	@Test
	public void testByEquals()
	{
		checkByEquals("name1", null, null, null);
		checkByEquals("name1", null, null, one);
		checkByEquals("name1", null, one, null);
		checkByEquals("name1", null, one, one);
		checkByEquals("name1", null, one, two);
		checkByEquals("name1", null, emptyList1, emptyList2);
		checkByEquals("name1", null, list1a, list1b);

		checkByEquals("name1", "name1", null, null);
		checkByEquals("name1", "name1", null, one);
		checkByEquals("name1", "name1", one, null);
		checkByEquals("name1", "name1", one, one);
		checkByEquals("name1", "name1", one, two);
		checkByEquals("name1", "name1", emptyList1, emptyList2);
		checkByEquals("name1", "name1", list1a, list1b);

		checkByEquals("name1", "name2", null, null);
		checkByEquals("name1", "name2", null, one);
		checkByEquals("name1", "name2", one, null);
		checkByEquals("name1", "name2", one, one);
		checkByEquals("name1", "name2", one, two);
		checkByEquals("name1", "name2", emptyList1, emptyList2);
		checkByEquals("name1", "name2", list1a, list1b);
	}
	@Test
	public void testByIdentity()
	{
		checkByIdentity("name1", "name1", emptyList1, emptyList2);
		checkByIdentity("name1", "name1", list1a, list1b);
	}
	private void checkByEquals(String observedPropertyName, String changedPropertyName, Object oldValue, Object newValue)
	{
		fireAndCount(true, observedPropertyName, changedPropertyName, oldValue, newValue, false, false);
		checkValue(oldValue, newValue, (oldValue == null) || (newValue == null) || !oldValue.equals(newValue), false);
	}
	
	private void checkByIdentity(String observedPropertyName, String changedPropertyName, Object oldValue, Object newValue)
	{
		fireAndCount(false, observedPropertyName, changedPropertyName, oldValue, newValue, true, false);
		fireAndCount(false, observedPropertyName, changedPropertyName, oldValue, newValue, false, true);
		checkValue(oldValue, newValue, oldValue!=newValue, true);
	}
	private <T> void checkValue(Object oldValue, Object newValue, boolean eventExpected, boolean checkIdentity)
	{
		ExtendedPropertyChangeSupport extended = new ExtendedPropertyChangeSupport(this);
		
		PropertyChangeListener mockListener = EasyMock.createMock(PropertyChangeListener.class);
		if(eventExpected)
		{
			mockListener.propertyChange(PropertyChangeEventEquals.eqPropertyChangeEvent(createPropertyChangeEvent(oldValue,newValue)));
		}
		
		EasyMock.replay(mockListener);
		
		String propertyName = "propertyName";
		extended.addPropertyChangeListener(propertyName, mockListener);
		if(checkIdentity)
		{
			extended.firePropertyChange(propertyName, oldValue, newValue, true);
		}else
		{
			extended.firePropertyChange(propertyName, oldValue, newValue);
		}

		EasyMock.verify(mockListener);
		EasyMock.reset(mockListener);
	}
	private PropertyChangeEvent createPropertyChangeEvent(Object oldValue, Object newValue)
	{
		return new PropertyChangeEvent(this, null, oldValue, newValue);
	}
	private void fireAndCount(boolean countsShallBeEqual, String observedPropertyName, String changedPropertyName, Object oldValue, Object newValue,
			boolean checkIdentityDefault, boolean checkIdentity)
	{
		PropertyChangeReport extendedNamedCounter = new PropertyChangeReport();
		PropertyChangeReport extendedMulticastCounter = new PropertyChangeReport();
		PropertyChangeReport normalNamedCounter = new PropertyChangeReport();
		PropertyChangeReport normalMulticastCounter = new PropertyChangeReport();

		ExtendedPropertyChangeSupport extended = new ExtendedPropertyChangeSupport(this, checkIdentityDefault);
		PropertyChangeSupport normal = new PropertyChangeSupport(this);

		extended.addPropertyChangeListener(observedPropertyName, extendedNamedCounter);
		extended.addPropertyChangeListener(extendedMulticastCounter);
		normal.addPropertyChangeListener(observedPropertyName, normalNamedCounter);
		normal.addPropertyChangeListener(normalMulticastCounter);

		if(checkIdentity)
		{
			extended.firePropertyChange(changedPropertyName, oldValue, newValue, true);
		} else
		{
			extended.firePropertyChange(changedPropertyName, oldValue, newValue);
		}
		normal.firePropertyChange(changedPropertyName, oldValue, newValue);

		//count.
		boolean namedCountersAreEqual = normalNamedCounter.getEventCount() == extendedNamedCounter.getEventCount();
		boolean multicastCountersAreEqual = normalMulticastCounter.getEventCount() == extendedMulticastCounter.getEventCount();

		if (countsShallBeEqual)
		{
			Assert.assertTrue("Named counters shall be equal", namedCountersAreEqual);
			Assert.assertTrue("Multicast counters shall be equal", multicastCountersAreEqual);
		} else
		{
			Assert.assertFalse("Named counters shall differ", namedCountersAreEqual);
			Assert.assertFalse("Multicast counters shall differ", multicastCountersAreEqual);
		}
	}

}
