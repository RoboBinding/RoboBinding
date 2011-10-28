package robobinding.value.experimental;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 *
 */
public class PropertyChangeReport implements PropertyChangeListener
{
	private int eventCount;
	public PropertyChangeReport()
	{
		eventCount = 0;
	}
	/* (non-Javadoc)
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt)
	{
		eventCount++;
	}
	public int getEventCount()
	{
		return eventCount;
	}
}
