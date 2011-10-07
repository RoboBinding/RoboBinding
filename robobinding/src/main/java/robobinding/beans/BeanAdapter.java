package robobinding.beans;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyVetoException;
import java.util.Map;

import robobinding.utils.Validate;

import com.google.common.collect.Maps;

public class BeanAdapter<B>
{

	/**
	 * @see PropertyAdapter#PROPERTY_BEFORE_BEAN
	 */
	public static final String PROPERTY_BEFORE_BEAN = PropertyAdapter.PROPERTY_BEFORE_BEAN;

	/**
	 * @see PropertyAdapter#PROPERTY_BEAN
	 */
	public static final String PROPERTY_BEAN = PropertyAdapter.PROPERTY_BEAN;

	/**
	 * @see PropertyAdapter#PROPERTY_AFTER_BEAN
	 */
	public static final String PROPERTY_AFTER_BEAN = PropertyAdapter.PROPERTY_AFTER_BEAN;

	/**
	 * The name of the read-only bound bean property that indicates whether one
	 * of the observed properties has changed.
	 * 
	 * @see PropertyAdapter#PROPERTY_CHANGED
	 */
	public static final String PROPERTY_CHANGED = PropertyAdapter.PROPERTY_CHANGED;

	// Fields *****************************************************************

	private B bean;

	/**
	 * Specifies whether we observe property changes and in turn fire state
	 * changes.
	 * 
	 * @see #getObserveChanges()
	 */
	private final boolean observeChanges;

	private final Map<String, PropertyAdapter<B,?>> propertyAdapters;

	private final ExtendedPropertyChangeSupport propertyChangeSupport;

	/**
	 * Indicates whether a property in the current target been has changed. Will
	 * be reset to {@code false} every time the target bean changes.
	 * 
	 * @see #isChanged()
	 * @see #setBean(Object)
	 */
	private boolean changed = false;

	/**
	 * The {@code PropertyChangeListener} used to handle changes in the bean
	 * properties. A new instance is created every time the target bean changes.
	 */
	private final PropertyChangeListener propertyChangeHandler;

	// Instance creation ****************************************************

	/**
	 * Constructs a BeanAdapter for the given bean; does not observe changes.
	 * <p>
	 * 
	 * Installs a default bean channel that checks the identity not equity to
	 * ensure that listeners are re-registered properly if the old and new bean
	 * are equal but not the same.
	 * 
	 * @param bean
	 *            the bean that owns the properties to adapt
	 */
	public BeanAdapter(B bean)
	{
		this(bean, false);
	}

	/**
	 * Constructs a BeanAdapter for the given bean; observes changes if
	 * specified.
	 * <p>
	 * 
	 * Installs a default bean channel that checks the identity not equity to
	 * ensure that listeners are reregistered properly if the old and new bean
	 * are equal but not the same.
	 * 
	 * @param bean
	 *            the bean that owns the properties to adapt
	 * @param observeChanges
	 *            {@code true} to observe changes of bound or constrained
	 *            properties, {@code false} to ignore changes
	 * @throws PropertyUnboundException
	 *             if {@code observeChanges} is true but the property is
	 *             unbound, i. e. the {@code bean} does not provide a pair of
	 *             methods to register a multicast PropertyChangeListener
	 */
	public BeanAdapter(B bean, boolean observeChanges)
	{
		this.observeChanges = observeChanges;
		setBean(bean);
		
		propertyAdapters = Maps.newHashMap();
		propertyChangeSupport = new ExtendedPropertyChangeSupport(this);
		propertyChangeHandler = new PropertyChangeHandler();
	}
	public B getBean()
	{
		return bean;
	}

	/**
	 * Sets a new Java Bean as holder of the adapted properties. Notifies any
	 * registered value listeners that are registered with the adapting
	 * ValueModels created in {@code #getValueModel}. Also notifies listeners
	 * that have been registered with this adapter to observe the bound property
	 * <em>bean</em>.
	 * <p>
	 * 
	 * Resets the changed state to {@code false}.
	 * <p>
	 * 
	 * If this adapter observes bean changes, the bean change handler will be
	 * removed from the former bean and will be added to the new bean. Hence, if
	 * the new bean is {@code null}, this adapter has no listener registered
	 * with a bean. And so, {@code setBean(null)} can be used as a clean release
	 * method that allows to use this adapter later again.
	 * 
	 * @param newBean
	 *            the new holder of the adapted properties
	 * 
	 * @see #getBean()
	 * @see #isChanged()
	 * @see #resetChanged()
	 * @see #release()
	 */
	public void setBean(B bean)
	{
		B oldBean = this.bean;
		this.bean = bean;
		propertyChangeSupport.firePropertyChange(PROPERTY_BEFORE_BEAN, oldBean, bean, true);
		removeChangeHandlerFrom(oldBean);
		forwardAllAdaptedValuesChanged();
		resetChanged();
		addChangeHandlerTo(bean);
		propertyChangeSupport.firePropertyChange(PROPERTY_BEAN, oldBean, bean, true);
		propertyChangeSupport.firePropertyChange(PROPERTY_AFTER_BEAN, oldBean, bean, true);
	}

	// Accessing Property Values **********************************************

	/**
	 * Returns the value of specified bean property, {@code null} if the current
	 * bean is {@code null}.
	 * <p>
	 * 
	 * This operation is supported only for readable bean properties.
	 * 
	 * @param propertyName
	 *            the name of the property to be read
	 * @return the value of the adapted bean property, null if the bean is null
	 * 
	 * @throws NullPointerException
	 *             if propertyName is null
	 * @throws UnsupportedOperationException
	 *             if the property is write-only
	 * @throws PropertyNotFoundException
	 *             if the property could not be found
	 * @throws PropertyAccessException
	 *             if the value could not be read
	 */
	public Object getValue(String propertyName)
	{
		if(bean == null)
		{
			return null;
		}
		return getPropertyValueModel(propertyName).getValue();
	}

	/**
	 * Sets the given new value for the specified bean property. Does nothing if
	 * this adapter's bean is {@code null}. If the setter associated with the
	 * propertyName throws a PropertyVetoException, it is silently ignored.
	 * <p>
	 * 
	 * Notifies the associated value change listeners if the bean reports a
	 * property change. Note that a bean may suppress PropertyChangeEvents if
	 * the old and new value are the same, or if the old and new value are
	 * equal.
	 * <p>
	 * 
	 * This operation is supported only for writable bean properties.
	 * 
	 * @param propertyName
	 *            the name of the property to set
	 * @param newValue
	 *            the value to set
	 * 
	 * @throws NullPointerException
	 *             if propertyName is null
	 * @throws UnsupportedOperationException
	 *             if the property is read-only
	 * @throws PropertyNotFoundException
	 *             if the property could not be found
	 * @throws PropertyAccessException
	 *             if the new value could not be set
	 */
	public void setValue(String propertyName, Object newValue)
	{
		if(bean == null)
		{
			return;
		}
		PropertyAdapter<B, Object> property = getPropertyValueModel(propertyName);
		property.setValue(newValue);
	}

	/**
	 * Sets a new value for the specified bean property. Does nothing if the
	 * bean is {@code null}. If the setter associated with the propertyName
	 * throws a PropertyVetoException, this methods throws the same exception.
	 * <p>
	 * 
	 * Notifies the associated value change listeners if the bean reports a
	 * property change. Note that a bean may suppress PropertyChangeEvents if
	 * the old and new value are the same, or if the old and new value are
	 * equal.
	 * <p>
	 * 
	 * This operation is supported only for writable bean properties.
	 * 
	 * @param propertyName
	 *            the name of the property to set
	 * @param newValue
	 *            the value to set
	 * 
	 * @throws UnsupportedOperationException
	 *             if the property is read-only
	 * @throws PropertyNotFoundException
	 *             if the property could not be found
	 * @throws PropertyAccessException
	 *             if the new value could not be set
	 * @throws PropertyVetoException
	 *             if the bean setter throws a PropertyVetoException
	 * 
	 * @since 1.1
	 */
	public void setVetoableValue(String propertyName, Object newValue) throws PropertyVetoException
	{
		if(bean == null)
		{
			return;
		}
		PropertyAdapter<B, Object> property = getPropertyValueModel(propertyName);
		property.setVetoableValue(newValue);
	}

	// Creating and Accessing Adapting ValueModels ****************************

	/**
	 * Looks up and lazily creates a ValueModel that adapts the bound property
	 * with the specified name. Uses the Bean introspection to look up the
	 * getter and setter names.
	 * <p>
	 * 
	 * Subsequent calls to this method with the same property name return the
	 * same ValueModel.
	 * <p>
	 * 
	 * To prevent potential runtime errors this method eagerly looks up the
	 * associated PropertyDescriptor if the target bean is not null.
	 * <p>
	 * 
	 * For each property name all calls to this method and to
	 * {@code #getValueModel(String, String, String)} must use the same getter
	 * and setter names. Attempts to violate this constraint will be rejected
	 * with an IllegalArgumentException. Especially once you've called this
	 * method you must not call {@code #getValueModel(String, String, String)}
	 * with a non-null getter or setter name. And vice versa, once you've called
	 * the latter method with a non-null getter or setter name, you must not
	 * call this method.
	 * <p>
	 * 
	 * This method uses a return type of AbstractValueModel, not a ValueModel.
	 * This makes {@link #setVetoableValue(String, Object)} visible. It also
	 * makes the AbstractValueModel convenience type converters available, which
	 * can significantly shrink the source code necessary to read and write
	 * values from/to these models.
	 * 
	 * @param propertyName
	 *            the name of the property to adapt
	 * @return a ValueModel that adapts the property with the specified name
	 * 
	 * @throws NullPointerException
	 *             if propertyName is null
	 * @throws PropertyNotFoundException
	 *             if the property could not be found
	 * @throws IllegalArgumentException
	 *             if {@code #getValueModel(String, String, String)} has been
	 *             called before with the same property name and a non-null
	 *             getter or setter name
	 */
	public <P> PropertyAdapter<B, P> getPropertyValueModel(String propertyName)
	{
		return getPropertyValueModel(propertyName, null, null);
	}

	/**
	 * Looks up and lazily creates a ValueModel that adapts the bound property
	 * with the specified name. Unlike {@code #getValueModel(String)} this
	 * method bypasses the Bean Introspection and uses the given getter and
	 * setter names to setup the access to the adapted Bean property.
	 * <p>
	 * 
	 * Subsequent calls to this method with the same parameters will return the
	 * same ValueModel.
	 * <p>
	 * 
	 * To prevent potential runtime errors this method eagerly looks up the
	 * associated PropertyDescriptor if the target bean is not null.
	 * <p>
	 * 
	 * For each property name all calls to this method and to
	 * {@code #getValueModel(String)} must use the same getter and setter names.
	 * Attempts to violate this constraint will be rejected with an
	 * IllegalArgumentException. Especially once you've called this method with
	 * a non-null getter or setter name, you must not call
	 * {@code #getValueModel(String)}. And vice versa, once you've called the
	 * latter method you must not call this method with a non-null getter or
	 * setter name.
	 * <p>
	 * 
	 * This method uses a return type of AbstractValueModel, not a ValueModel.
	 * This makes {@link #setVetoableValue(String, Object)} visible. It also
	 * makes the AbstractValueModel convenience type converters available, which
	 * can significantly shrink the source code necessary to read and write
	 * values from/to these models.
	 * 
	 * @throws PropertyNotFoundException if the property could not be found.
	 * @throws IllegalArgumentException if this method has been called before with the same property 
	 * 		name and different getter or setter names.
	 */
	public <P> PropertyAdapter<B, P> getPropertyValueModel(String propertyName, String readMethodName, String writeMethodName)
	{
		Validate.notBlank(propertyName, "The property name must not be empty");
		
		@SuppressWarnings("unchecked")
		PropertyAdapter<B, P> property = (PropertyAdapter<B, P>)propertyAdapters.get(propertyName);
		if (property == null)
		{
			property = createPropertyAdapter(propertyName, readMethodName, writeMethodName);
			propertyAdapters.put(propertyName, property);
		}else
		{
			property.validateReadWriteMethodNames(readMethodName, writeMethodName);
		}
		return property;
	}

	private <P> PropertyAdapter<B, P> createPropertyAdapter(String propertyName, String readMethodName, String writeMethodName)
	{
		PropertyAdapter.Builder<B, P> builder = new PropertyAdapter.Builder<B, P>();
		builder.setPropertyName(propertyName);
		builder.setReadMethodName(readMethodName);
		builder.setWriteMethodName(writeMethodName);
		builder.setObserveChanges(false);
		builder.setBean(bean);
		return builder.create();
	}

	/**
	 * Resets this tracker's changed state to {@code false}.
	 */
	void resetChanged()
	{
		setChanged(false);
	}

	/**
	 * Sets the changed state to the given value. Invoked by the global
	 * PropertyChangeHandler that observes all bean changes. Also invoked by
	 * {@code #resetChanged}.
	 * 
	 * @param newValue
	 *            the new changed state
	 */
	private void setChanged(boolean newValue)
	{
		boolean oldValue = changed;
		changed = newValue;
		propertyChangeSupport.firePropertyChange(PROPERTY_CHANGED, oldValue, newValue);
	}
	/**
	 * @see PropertyChangeSupport#addPropertyChangeListener(PropertyChangeListener)
	 */
	public final void addPropertyChangeListener(PropertyChangeListener listener)
	{
		propertyChangeSupport.addPropertyChangeListener(listener);
	}
	/**
	 * @see PropertyChangeSupport#removePropertyChangeListener(PropertyChangeListener)
	 */
	public final void removePropertyChangeListener(PropertyChangeListener listener)
	{
		propertyChangeSupport.removePropertyChangeListener(listener);
	}
	/**
     * @see PropertyChangeSupport#addPropertyChangeListener(String, PropertyChangeListener)
     */
	public final void addPropertyChangeListener(String propertyName, PropertyChangeListener listener)
	{
		propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
	}

	/**
	 * @see PropertyChangeSupport#removePropertyChangeListener(String, PropertyChangeListener)
	 */
	public final void removePropertyChangeListener(String propertyName, PropertyChangeListener listener)
	{
		propertyChangeSupport.removePropertyChangeListener(propertyName, listener);
	}
	/**
	 * Adds a PropertyChangeListener to the list of bean listeners. The listener
	 * is registered for all bound properties of the target bean.
	 * <p>
	 * 
	 * The listener will be notified if and only if this BeanAdapter's current
	 * bean changes a property. It'll not be notified if the bean changes.
	 * <p>
	 */
	public void addBeanPropertyChangeListener(PropertyChangeListener listener)
	{
		propertyChangeSupport.addPropertyChangeListener(listener);
	}

	/**
	 * Removes a PropertyChangeListener from the list of bean listeners. This
	 * method should be used to remove PropertyChangeListeners that were
	 * registered for all bound properties of the target bean.
	 * <p>
	 */
	public void removeBeanPropertyChangeListener(PropertyChangeListener listener)
	{
		propertyChangeSupport.removePropertyChangeListener(listener);
	}

	/**
	 * Adds a PropertyChangeListener to the list of bean listeners for a
	 * specific property. The specified property may be user-defined.
	 * <p>
	 * 
	 * The listener will be notified if and only if this BeanAdapter's current
	 * bean changes the specified property. It'll not be notified if the bean
	 * changes. If you want to observe property changes and bean changes, you
	 * may observe the ValueModel that adapts this property - as returned by
	 * {@code #getValueModel(String)}.
	 * <p>
	 * 
	 * Note that if the bean is inheriting a bound property, then no event will
	 * be fired in response to a change in the inherited property.
	 * <p>
	 */
	public void addBeanPropertyChangeListener(String propertyName, PropertyChangeListener listener)
	{
		propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
	}

	/**
	 * Removes a PropertyChangeListener from the listener list for a specific
	 * property. This method should be used to remove PropertyChangeListeners
	 * that were registered for a specific bound property.
	 * <p>
	 * 
	 */
	public void removeBeanPropertyChangeListener(String propertyName, PropertyChangeListener listener)
	{
		propertyChangeSupport.removePropertyChangeListener(propertyName, listener);
	}
	
	/**
	 * Returns an array of all the property change listeners registered on this
	 * component.
	 */
	public PropertyChangeListener[] getBeanPropertyChangeListeners()
	{
		return propertyChangeSupport.getPropertyChangeListeners();
	}

	/**
	 * Returns an array of all the listeners which have been associated with the
	 * named property.
	 */
	public PropertyChangeListener[] getBeanPropertyChangeListeners(String propertyName)
	{
		return propertyChangeSupport.getPropertyChangeListeners(propertyName);
	}

	// Releasing PropertyChangeListeners **************************************

	/**
	 * Removes the PropertyChangeHandler from the observed bean, if the bean is
	 * not {@code null} and if bean property changes are observed. Also removes
	 * all listeners from the bean that have been registered with
	 * {@code #addBeanPropertyChangeListener} before.
	 * <p>
	 * 
	 * BeanAdapters that observe changes have a PropertyChangeListener
	 * registered with the target bean. Hence, a bean has a reference to all
	 * BeanAdapters that observe it. To avoid memory leaks it is recommended to
	 * remove this listener if the bean lives much longer than the BeanAdapter,
	 * enabling the garbage collector to remove the adapter. To do so, you can
	 * call {@code setBean(null)} or set the bean channel's value to null. As an
	 * alternative you can use event listener lists in your beans that implement
	 * references with {@code WeakReference}.
	 * <p>
	 * 
	 * Setting the bean to null has side-effects, for example the adapter fires
	 * a change event for the bound property <em>bean</em> and other properties.
	 * And the value of ValueModel's vended by this adapter may change. However,
	 * typically this is fine and setting the bean to null is the first choice
	 * for removing the reference from the bean to the adapter. Another way to
	 * clear the reference from the target bean is to call {@code #release}; it
	 * has no side-effects.
	 * <p>
	 * 
	 * Since version 2.0.4 it is safe to call this method multiple times,
	 * however, the adapter must not be used anymore once #release has been
	 * called.
	 * 
	 * @see #setBean(Object)
	 * @see java.lang.ref.WeakReference
	 */
	public void release()
	{
		removeChangeHandlerFrom(bean);
		propertyChangeSupport.removeAllListeners();
	}

	private void forwardAllAdaptedValuesChanged()
	{
		for(PropertyAdapter<B, ?> property:propertyAdapters.values())
		{
			property.setBean(bean);
		}
	}

	/**
	 * Adds a property change listener to the given bean if we observe changes
	 * and the bean is not null. First checks whether the bean class supports
	 * <em>bound properties</em>, i.e. it provides a pair of methods to register
	 * multicast property change event listeners; see section 7.4.1 of the Java
	 * Beans specification for details.
	 * 
	 * @param bean
	 *            the bean to add a property change handler.
	 * @throws PropertyUnboundException
	 *             if the bean does not support bound properties
	 * @throws PropertyNotBindableException
	 *             if the property change handler cannot be added successfully
	 * 
	 * @see #removeChangeHandlerFrom(Object)
	 */
	private void addChangeHandlerTo(B bean)
	{
		if (!observeChanges || bean == null)
		{
			return;
		}
		if(!BeanUtils.addPropertyChangeListener(bean, propertyChangeHandler))
		{
			throw new PropertyNotBindableException("The given bean '"+bean+"' is not bindable. You need to extends ObservableBean interface");
		}
	}

	/**
	 * Removes the formerly added property change handler from the given bean if
	 * we observe changes and the bean is not null and we haven't called this
	 * method before.
	 * 
	 * @param bean
	 *            the bean to remove the property change handler from.
	 * @throws PropertyUnboundException
	 *             if the bean does not support bound properties
	 * @throws PropertyNotBindableException
	 *             if the property change handler cannot be removed successfully
	 * 
	 * @see #addChangeHandlerTo(Object)
	 */
	private void removeChangeHandlerFrom(B bean)
	{
		if (!observeChanges || bean == null || propertyChangeHandler == null)
		{
			return;
		}
		if(ObservableBean.class.isInstance(bean))
		{
			((ObservableBean)bean).removePropertyChangeListener(propertyChangeHandler);
		}
	}

	/**
	 * Listens to changes of all bean properties. Fires property changes if the
	 * associated property or an arbitrary set of properties has changed.
	 */
	private final class PropertyChangeHandler implements PropertyChangeListener
	{

		/**
		 * A bean property has been changed. Sets the changed state to true.
		 * Checks whether the observed or multiple properties have changed. If
		 * so, notifies all registered listeners about the change.
		 * 
		 * @param event
		 *            the property change event to be handled
		 */
		public void propertyChange(PropertyChangeEvent event)
		{
			setChanged(true);
			String propertyName = event.getPropertyName();
			if (propertyName == null)
			{
				forwardAllAdaptedValuesChanged();
			}
		}
	}

}
