package robobinding.beans;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeListenerProxy;
import java.beans.PropertyChangeSupport;

/**
 * Differs from its superclass {@link PropertyChangeSupport} in that it can
 * check for changed values using {@code #equals} or {@code ==}. Useful if you
 * want to ensure that a {@code PropertyChangeEvent} is fired if the old and new
 * value are not the same but if they are equal.
 * <p>
 */
public final class ExtendedPropertyChangeSupport extends PropertyChangeSupport
{
	private static final long serialVersionUID = 4908378981887614204L;

	private final Object source;

	/**
	 * The default setting for the identity check.
	 */
	private final boolean checkIdentityDefault;

	/**
	 * @param sourceBean The bean to be given as the source for any events.
	 */
	public ExtendedPropertyChangeSupport(Object sourceBean)
	{
		this(sourceBean, false);
	}

	/**
	 * @param sourceBean The object provided as the source for any generated events.
	 * @param checkIdentityDefault true enables the identity check by default.
	 */
	public ExtendedPropertyChangeSupport(Object sourceBean, boolean checkIdentityDefault)
	{
		super(sourceBean);
		this.source = sourceBean;
		this.checkIdentityDefault = checkIdentityDefault;
	}
	/**
	 * Reports a bound property update to any registered listeners. Uses the
	 * default test ({@code #equals} vs. {@code ==}) to determine whether the
	 * event's old and new values are different. No event is fired if old and
	 * new value are the same.
	 * 
	 * @param propertyName The programmatic name of the property that was changed.
	 * 
	 * @see PropertyChangeSupport#firePropertyChange(String, Object, Object)
	 */
	@Override
	public void firePropertyChange(String propertyName, Object oldValue, Object newValue)
	{
		firePropertyChange(propertyName, oldValue, newValue, checkIdentityDefault);
	}
	/**
	 * Reports a bound property update to any registered listeners. No event is
	 * fired if the old and new value are the same. If checkIdentity is
	 * {@code true} an event is fired in all other cases. If this parameter is
	 * {@code false}, an event is fired if old and new values are not equal.
	 * 
	 * @param propertyName The programmatic name of the property that was changed.
	 * @param checkIdentity true to check differences using {@code ==} false to use {@code #equals}.
	 */
	public void firePropertyChange(final String propertyName, final Object oldValue, final Object newValue, final boolean checkIdentity)
	{
		if(oldValue != null && oldValue == newValue)
		{
			return;
		}
		firePropertyChange0(propertyName, oldValue, newValue, checkIdentity);
	}


	private void firePropertyChange0(String propertyName, Object oldValue, Object newValue, boolean checkIdentity)
	{
		if(checkIdentity)
		{
			fireUnchecked(new PropertyChangeEvent(source, propertyName, oldValue, newValue));
		} else
		{
			super.firePropertyChange(propertyName, oldValue, newValue);
		}
	}

	/**
	 * Fires a PropertyChangeEvent to all its listeners without checking via
	 * equals method if the old value is equal to new value. The instance
	 * equality check is done by the calling firePropertyChange method (to avoid
	 * instance creation of the PropertyChangeEvent).
	 * <p>
	 * 
	 * If some listeners have been added with a named property, then
	 * {@code PropertyChangeSupport#getPropertyChangeListeners()} returns an
	 * array with a mixture of PropertyChangeListeners and
	 * {@code PropertyChangeListenerProxy}s. We notify all non-proxies and those
	 * proxies that have a property name that is equals to the event's property
	 * name.
	 * 
	 * @param event event to fire to the listeners
	 * 
	 * @see PropertyChangeListenerProxy
	 * @see PropertyChangeSupport#getPropertyChangeListeners()
	 */
	private void fireUnchecked(PropertyChangeEvent event)
	{
		PropertyChangeListener[] listeners;
		synchronized (this)
		{
			listeners = getPropertyChangeListeners();
		}
		String propertyName = event.getPropertyName();
		for(PropertyChangeListener listener : listeners)
		{
			if (listener instanceof PropertyChangeListenerProxy)
			{
				PropertyChangeListenerProxy proxy = (PropertyChangeListenerProxy) listener;
				if (proxy.getPropertyName().equals(propertyName))
				{
					proxy.propertyChange(event);
				}
			} else
			{
				listener.propertyChange(event);
			}
		}
	}
	public void removeAllListeners()
	{
		PropertyChangeListener[] propertyChangeListeners = getPropertyChangeListeners();
		for(PropertyChangeListener propertyChangeListener : propertyChangeListeners)
		{
			removePropertyChangeListener(propertyChangeListener);
		}
	}
}
