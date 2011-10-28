package robobinding.value.experimental;

import java.beans.PropertyChangeEvent;

import org.apache.commons.lang3.ObjectUtils;
import org.easymock.EasyMock;
import org.easymock.IArgumentMatcher;

import robobinding.utils.Validate;

/**
 * @since 1.0
 * @version $Revision: $
 * @author Cheng Wei
 * 
 */
public class PropertyChangeEventEquals implements IArgumentMatcher
{
	private PropertyChangeEvent expected;

	public PropertyChangeEventEquals(PropertyChangeEvent expected)
	{
		Validate.notNull(expected);
		this.expected = expected;
	}

	public boolean matches(Object actual)
	{
		if (!(actual instanceof PropertyChangeEvent))
		{
			return false;
		}
		PropertyChangeEvent actualEvent = (PropertyChangeEvent) actual;
		return ObjectUtils.equals(expected.getOldValue(), actualEvent.getOldValue()) 
				&& ObjectUtils.equals(expected.getNewValue(), actualEvent.getNewValue());
	}
	public void appendTo(StringBuffer buffer)
	{
		buffer.append("eqPropertyChangeEvent(");
		buffer.append(expected.getClass().getName());
		buffer.append(" with oldValue '");
		buffer.append(expected.getOldValue());
		buffer.append("', newValue '");
		buffer.append(expected.getNewValue());
		buffer.append("')");

	}

	public static PropertyChangeEvent eqPropertyChangeEvent(PropertyChangeEvent expected)
	{
		EasyMock.reportMatcher(new PropertyChangeEventEquals(expected));
		return null;
	}
}