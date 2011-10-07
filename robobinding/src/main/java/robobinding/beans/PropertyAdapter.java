package robobinding.beans;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;

import robobinding.utils.Validate;
import robobinding.value.AbstractValueModel0;

public final class PropertyAdapter<B, P> extends AbstractValueModel0<P>
{

	/**
	 * The property name used in the PropertyChangeEvent that is fired before
	 * the <em>bean</em> property fires its PropertyChangeEvent. Useful to
	 * perform an operation before listeners that handle the bean change are
	 * notified. See also the class comment.
	 */
	public static final String PROPERTY_BEFORE_BEAN = "beforeBean";

	/**
	 * The name of the read-write bound property that holds the target bean.
	 */
	public static final String PROPERTY_BEAN = "bean";

	/**
	 * The property name used in the PropertyChangeEvent that is fired after the
	 * <em>bean</em> property fires its PropertyChangeEvent. Useful to perform
	 * an operation after listeners that handle the bean change are notified.
	 * See also the class comment.
	 */
	public static final String PROPERTY_AFTER_BEAN = "afterBean";

	/**
	 * The name of the read-only bound bean property that indicates whether one
	 * of the observed properties has changed.
	 */
	public static final String PROPERTY_CHANGED = "changed";
	/**
	 * Specifies whether we observe property changes and in turn fire state changes.
	 */
	private final boolean observeChanges;

	private final String propertyName;
	private final String readMethodName;
	private final String writeMethodName;
	/**
	 * Refers to the old bean. Used as old value if the bean changes. Updated
	 * after a bean change in the BeanChangeHandler.
	 */
	private B bean;

	/**
	 * Indicates whether a property in the current target been has changed. Will
	 * be reset to {@code false} every time the target bean changes.
	 * 
	 * @see #isChanged()
	 * @see #setBean(Object)
	 */
	private boolean changed = false;

	private final PropertyChangeListener propertyChangeHandler;

	/**
	 * Describes the property access; basically a getter and setter.
	 */
	private transient PropertyAccessor<P> cachedPropertyAccessor;
	private PropertyAdapter(Builder<B, P> builder)
	{
		super(false);
		
		Validate.notBlank(builder.propertyName, "propertyName must not be empty");
		
		this.propertyName = builder.propertyName;
		this.readMethodName = builder.readMethodName;
		this.writeMethodName = builder.writeMethodName;
		this.observeChanges = builder.observeChanges;
		setBean(builder.bean);
		
		propertyChangeHandler = new PropertyChangeHandler();
	}
	public B getBean()
	{
		return bean;
	}
	public void setBean(B bean)
	{
		B oldBean = this.bean;
		this.bean = bean;
		
		firePropertyChange(PROPERTY_BEFORE_BEAN, oldBean, bean, true);
		removePropertyChangeHandlerFrom(oldBean);
		forwardAdaptedValueChanged(oldBean, bean);
		resetChanged();
		addChangeHandlerTo(bean);
		firePropertyChange(PROPERTY_BEAN, oldBean, bean, true);
		firePropertyChange(PROPERTY_AFTER_BEAN, oldBean, bean, true);
	}
	/**
	 * Returns the value of the bean's adapted property, {@code null} if the
	 * current bean is {@code null}.
	 * <p>
	 * 
	 * @throws UnsupportedOperationException if the property is write-only
	 * @throws PropertyNotFoundException if the property could not be found
	 * @throws PropertyAccessException if the value could not be read.
	 */
	@Override
	public P getValue()
	{
		return getValue0(bean);
	}
	public P getValue0(B bean)
	{
		return (bean==null)?null:getPropertyAccessor(bean).getValue(bean);
	}
	/**
	 * Sets the given object as new value of the adapted bean property. Does
	 * nothing if the bean is {@code null}. If the bean setter throws a
	 * PropertyVetoException, it is silently ignored. This write operation is
	 * supported only for writable bean properties.
	 * <p>
	 * 
	 * Notifies any registered value listeners if the bean reports a property
	 * change. Note that a bean may suppress PropertyChangeEvents if the old and
	 * new value are the same, or if the old and new value are equal.
	 * 
	 * @throws UnsupportedOperationException if the property is read-only.
	 * @throws PropertyNotFoundException if the property could not be found.
	 * @throws PropertyAccessException if the new value could not be set.
	 */
	@Override
	public void setValue(P newValue)
	{
		try
		{
			setValue0(newValue);
		} catch (PropertyVetoException e)
		{
			// Silently ignore this situation.
		}
	}

	/**
	 * Sets the given object as new value of the adapted bean property. Does
	 * nothing if the bean is {@code null}. If the bean setter throws a
	 * PropertyVetoExeption, this method throws the same exception. This write
	 * operation is supported only for writable bean properties.
	 * <p>
	 * 
	 * Notifies any registered value listeners if the bean reports a property
	 * change. Note that a bean may suppress PropertyChangeEvents if the old and
	 * new value are the same, or if the old and new value are equal.
	 * 
	 * @throws UnsupportedOperationException if the property is read-only
	 * @throws PropertyNotFoundException if the property could not be found
	 * @throws PropertyAccessException if the new value could not be set
	 * @throws PropertyVetoException if the invoked bean setter throws a PropertyVetoException
	 */
	public void setVetoableValue(P newValue) throws PropertyVetoException
	{
		setValue0(newValue);
	}

	// Accessing the Changed State ********************************************

	private void setValue0(P newValue) throws PropertyVetoException
	{
		if (bean == null)
		{
			return;
		}
		getPropertyAccessor(bean).setValue(bean, newValue);
	}
	private void resetChanged()
	{
		setChanged(false);
	}

	private void setChanged(boolean newValue)
	{
		boolean oldValue = changed;
		changed = newValue;
		firePropertyChange(PROPERTY_CHANGED, oldValue, newValue);
	}

	// Releasing PropertyChangeListeners **************************************

	/**
	 * Removes the PropertyChangeHandler from the observed bean, if the bean is
	 * not {@code null} and if property changes are observed.
	 * <p>
	 * 
	 * PropertyAdapters that observe changes have a PropertyChangeListener
	 * registered with the target bean. Hence, a bean has a reference to all
	 * PropertyAdapters that observe it. To avoid memory leaks it is recommended
	 * to remove this listener if the bean lives much longer than the
	 * PropertyAdapter, enabling the garbage collector to remove the adapter. To
	 * do so, you can call {@code setBean(null)} or set the bean channel's value
	 * to null. As an alternative you can use event listener lists in your beans
	 * that implement references with {@code WeakReference}.
	 * <p>
	 * 
	 * Setting the bean to null has side-effects, for example this adapter fires
	 * a change event for the bound property <em>bean</em> and other properties.
	 * And this adpter's value may change. However, typically this is fine and
	 * setting the bean to null is the first choice for removing the reference
	 * from the bean to the adapter. Another way to clear the reference from the
	 * target bean is to call {@code #release}. It has no side-effects, but the
	 * adapter must not be used anymore once #release has been called.
	 * 
	 * @see #setBean(Object)
	 * @see java.lang.ref.WeakReference
	 */
	public void release()
	{
		removePropertyChangeHandlerFrom(getBean());
	}
	@Override
	protected String paramString()
	{
		if(bean == null)
		{
			return "Null bean";
		}else
		{
			return cachedPropertyAccessor.describeProperty(bean);
		}
	}

	// Changing the Bean & Adding and Removing the PropertyChangeHandler ******

	private void forwardAdaptedValueChanged(B oldBean, B newBean)
	{
		Object oldValue = isBeanNullOrIsWriteOnlyProperty(oldBean) ? null : getValue0(oldBean);
		Object newValue = isBeanNullOrIsWriteOnlyProperty(newBean) ? null : getValue0(newBean);
		if (oldValue != null || newValue != null)
		{
			fireValueChange(oldValue, newValue, true);
		}
	}
	private boolean isBeanNullOrIsWriteOnlyProperty(B bean)
	{
		return (bean == null) || isWriteOnlyProperty(bean);
	}
	private void forwardAdaptedValueChanged(B newBean)
	{
		Object newValue = isBeanNullOrIsWriteOnlyProperty(newBean) ? null : getValue0(newBean);
		fireValueChange(null, newValue);
	}

	/**
	 * Adds a property change listener to the given bean if we observe changes
	 * and the bean is not null. First checks whether the bean class supports
	 * <em>bound properties</em>, i.e. it provides a pair of methods to register
	 * multicast property change event listeners; see section 7.4.1 of the Java
	 * Beans specification for details.
	 * 
	 * @param bean the bean to add a property change handler.
	 * @throws PropertyNotBindableException
	 *             if the property change handler cannot be added successfully
	 * 
	 * @see #removePropertyChangeHandlerFrom(Object)
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
	
	private void removePropertyChangeHandlerFrom(B bean)
	{
		if (!observeChanges || bean == null)
		{
			return;
		}

		if(ObservableBean.class.isInstance(bean))
		{
			((ObservableBean)bean).removePropertyChangeListener(propertyChangeHandler);
		}
	}

	/**
	 * The cached accessor is considered invalid, if the bean's class has
	 * changed. In this case we recompute the accessor.
	 */
	private PropertyAccessor<P> getPropertyAccessor(B bean)
	{
		if((cachedPropertyAccessor==null) || !cachedPropertyAccessor.beanClassEquals(bean.getClass()))
		{
			cachedPropertyAccessor = new PropertyAccessor<P>(propertyName, bean.getClass(), readMethodName, writeMethodName);
		}
		return cachedPropertyAccessor;
	}

	private boolean isWriteOnlyProperty(B bean)
	{
		return getPropertyAccessor(bean).isWriteOnly();
	}

	public void validateReadWriteMethodNames(String getterName, String setterName)
	{
		// TODO Auto-generated method stub
		
	}

	/**
	 * Listens to changes of all bean properties. Fires property changes if the
	 * associated property or an arbitrary set of properties has changed.
	 */
	private final class PropertyChangeHandler implements PropertyChangeListener
	{

		/**
		 * A bean property has been changed. Sets the changed state to true.
		 * Checks whether the observed property or multiple properties have
		 * changed. If so, notifies all registered listeners about the change.
		 * 
		 * @param event the property change event to be handled
		 */
		public void propertyChange(PropertyChangeEvent event)
		{
			setChanged(true);
			if (event.getPropertyName().equals(propertyName))
			{
				fireValueChange(event.getOldValue(), event.getNewValue(), true);
			}else
			{
				forwardAdaptedValueChanged(getBean());
			}
		}
	}

	public static class Builder<B, P>
	{
		private B bean;
		private String propertyName;
		private String readMethodName;
		private String writeMethodName;
		private boolean observeChanges;

		public Builder<B, P> setBean(B bean)
		{
			this.bean = bean;
			return this;
		}

		public Builder<B, P> setPropertyName(String propertyName)
		{
			this.propertyName = propertyName;
			return this;
		}

		public Builder<B, P> setObserveChanges(boolean observeChanges)
		{
			this.observeChanges = observeChanges;
			return this;
		}

		public Builder<B, P> setReadMethodName(String readMethodName)
		{
			this.readMethodName = readMethodName;
			return this;
		}

		public Builder<B, P> setWriteMethodName(String writeMethodName)
		{
			this.writeMethodName = writeMethodName;
			return this;
		}

		public PropertyAdapter<B, P> create()
		{
			return new PropertyAdapter<B, P>(this);
		}
	}
}
