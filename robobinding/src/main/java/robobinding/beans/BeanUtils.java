package robobinding.beans;

import java.beans.PropertyChangeListener;

class BeanUtils
{

	private BeanUtils()
	{
	}

	/**
	 * Adds a property change listener to the given bean.
	 * @return returns true if success. Otherwise, returns false.
	 */
	public static boolean addPropertyChangeListener(Object bean, PropertyChangeListener listener)
	{
		if (bean instanceof ObservableBean)
		{
			((ObservableBean)bean).addPropertyChangeListener(listener);
			return true;
		}

		return false;
	}
}
