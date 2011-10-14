package robobinding.beans;


public class BufferedBeanAdapter<B>
{
/*	*//**
	 * @see PresentationModelAdapterImpl#PROPERTY_BEFORE_BEAN
	 *//*
	public static final String PROPERTY_BEFORE_BEAN = "PROPERTY_BEFORE_BEAN";

	*//**
	 * @see PresentationModelAdapterImpl#PROPERTY_BEAN
	 *//*
	public static final String PROPERTY_BEAN = "PROPERTY_BEAN";

	*//**
	 * @see PresentationModelAdapterImpl#PROPERTY_AFTER_BEAN
	 *//*
	public static final String PROPERTY_AFTER_BEAN = "PROPERTY_AFTER_BEAN";

	*//**
	 * The name of the read-write bound bean property for the trigger channel
	 * that is shared by all properties.
	 * 
	 * @see BufferedValueModel#PROPERTY_TRIGGER_CHANNEL
	 *//*
	public static final String PROPERTY_TRIGGER_CHANNEL = BufferedValueModel.PROPERTY_TRIGGER_CHANNEL;

	*//**
	 * The name of the read-only bound bean property that indicates whether one
	 * of the buffered properties is buffering.
	 * 
	 * @see BufferedValueModel#PROPERTY_BUFFERING
	 *//*
	public static final String PROPERTY_BUFFERING = BufferedValueModel.PROPERTY_BUFFERING;

	*//**
	 * @see PresentationModelAdapterImpl#PROPERTY_CHANGED
	 *//*
	public static final String PROPERTY_CHANGED = PresentationModelAdapterImpl.PROPERTY_CHANGED;

	*//**
	 * Refers to the BeanAdapter that provides all underlying behavior to vend
	 * adapting ValueModels, track bean changes, and to register with bound bean
	 * properties.
	 *//*
	private final PresentationModelAdapterImpl<B> beanAdapter;

	private Trigger triggerChannel;

	private final Map<String, BufferedValueModel<Object>> bufferedPropertyAdapters;

	*//**
	 * Listens to value changes and validates this model. The validation result
	 * is available in the validationResultHolder.
	 * <p>
	 * 
	 * Also listens to changes of the <em>buffering</em> property in
	 * {@code BufferedValueModel}s and updates the buffering state - if
	 * necessary.
	 *//*
	private final PropertyChangeListener bufferingStateChangeHandler;

	*//**
	 * Indicates whether a registered buffered model has a pending change, in
	 * other words whether any of the values has been edited or not.
	 *//*
	private boolean buffering = false;

	private final ExtendedPropertyChangeSupport propertyChangeSupport;
	*//**
	 * Constructs a PresentationModel on the given bean channel using the given
	 * trigger channel. The bean channel holds a bean that in turn holds the
	 * properties to adapt.
	 * <p>
	 * 
	 * It is strongly recommended that the bean channel checks the identity not
	 * equity. This ensures that listeners are unregistered properly if the old
	 * and new bean are equal but not the same.
	 * <p>
	 * 
	 * The trigger channel is shared by all buffered models that are created
	 * using {@code #buffer}. It can be replaced by any other Boolean ValueModel
	 * later. Note that PresentationModel observes trigger value changes, not
	 * value state. Therefore you must ensure that customer triggers report
	 * value changes when asked to commit or flush. See the Trigger
	 * implementation for an example.
	 * 
	 * @param beanChannel
	 *            the ValueModel that holds the bean
	 * @param triggerChannel
	 *            the ValueModel that triggers commit and flush events
	 *//*
	public BufferedBeanAdapter(B bean, Trigger triggerChannel)
	{
		this.beanAdapter = new PresentationModelAdapterImpl<B>(bean, true);
		this.triggerChannel = triggerChannel;
		
		propertyChangeSupport = new ExtendedPropertyChangeSupport(this);
		bufferingStateChangeHandler = new BufferingStateChangeHandler();
		bufferedPropertyAdapters = Maps.newHashMap();
	}

	public B getBean()
	{
		return beanAdapter.getPresentationModel();
	}

	public void setBean(B newBean)
	{
		beanAdapter.setBean(newBean);
	}

	*//**
	 * @see PresentationModelAdapterImpl#getValue(String)
	 *//*
	public Object getValue(String propertyName)
	{
		return getPropertyValueModel(propertyName).getValue();
	}

	*//**
	 * @see PresentationModelAdapterImpl#setValue(String, Object)
	 *//*
	public void setValue(String propertyName, Object newValue)
	{
		getPropertyValueModel(propertyName).setValue(newValue);
	}

	*//**
	 * @see PresentationModelAdapterImpl#getPropertyValueModel(String)
	 *//*
	public BufferedValueModel<Object> getPropertyValueModel(String propertyName)
	{
		return getPropertyValueModel(propertyName, null, null);
	}

	*//**
	 * @see PresentationModelAdapterImpl#getPropertyValueModel(String, String, String)
	 *//*
	public BufferedValueModel<Object> getPropertyValueModel(String propertyName, String readMethodName, String writeMethodName)
	{
		BufferedValueModel<Object> bufferedProperty = bufferedPropertyAdapters.get(propertyName);
		if (bufferedProperty == null)
		{
			bufferedProperty = createBufferedProperty(propertyName, readMethodName, writeMethodName);
			bufferedPropertyAdapters.put(propertyName, bufferedProperty);
		} else
		{
			@SuppressWarnings("unchecked")
			PropertyAdapter<B, Object> sourceProperty = (PropertyAdapter<B, Object>) bufferedProperty.getSource();
			sourceProperty.validateReadWriteMethodNames(readMethodName, writeMethodName);
		}
		return bufferedProperty;
	}

	private BufferedValueModel<Object> createBufferedProperty(String propertyName, String readMethodName, String writeMethodName)
	{
		PropertyAdapter<B, Object> property = beanAdapter.getPropertyValueModel(propertyName, readMethodName, writeMethodName);
		BufferedValueModel<Object> bufferedModel = new BufferedValueModel<Object>(property, triggerChannel);
		bufferedModel.addPropertyChangeListener(BufferedValueModel.PROPERTY_BUFFERING, bufferingStateChangeHandler);
		return bufferedModel;
	}

	*//**
	 * Sets the given ValueModel as this model's new trigger channel. Sets the
	 * new trigger channel in all existing BufferedValueModels that have been
	 * created using {@code #getBufferedModel}. Subsequent invocations of
	 * {@code #triggerCommit} and {@code #triggerFlush} will trigger commit and
	 * flush events using the new trigger channel.
	 *//*
	void setTriggerChannel(Trigger newTriggerChannel)
	{
		Validate.notNull(newTriggerChannel, "The trigger channel must not be null.");

		Trigger oldTriggerChannel = getTriggerChannel();
		triggerChannel = newTriggerChannel;
		for (BufferedValueModel<Object> bufferedProperty : bufferedPropertyAdapters.values())
		{
			bufferedProperty.setTriggerChannel(triggerChannel);
		}
		propertyChangeSupport.firePropertyChange(PROPERTY_TRIGGER_CHANNEL, oldTriggerChannel, newTriggerChannel);
	}

	private Trigger getTriggerChannel()
	{
		return triggerChannel;
	}

	*//**
	 * Sets the trigger channel to true which in turn triggers commit events in
	 * all BufferedValueModels that share this trigger.
	 *//*
	private boolean isBuffering()
	{
		return buffering;
	}

	*//**
	 * Sets the buffering state to the specified value.
	 * 
	 * @param newValue
	 *            the new buffering state
	 *//*
	private void setBuffering(boolean newValue)
	{
		boolean oldValue = isBuffering();
		buffering = newValue;
		propertyChangeSupport.firePropertyChange(PROPERTY_BUFFERING, oldValue, newValue);
	}

	private void updateBufferingState(boolean latestBufferingStateChange)
	{
		if (buffering == latestBufferingStateChange)
		{
			return;
		}
		boolean nowBuffering = false;
		for(BufferedValueModel<Object> bufferedProperty : bufferedPropertyAdapters.values())
		{
			nowBuffering = nowBuffering || bufferedProperty.isBuffering();
			if (!buffering && nowBuffering)
			{
				setBuffering(true);
				return;
			}
		}
		setBuffering(nowBuffering);
	}

	// Changed State *********************************************************

	

	// Observing Changes in ValueModel and Bean Properties *******************

	*//**
	 * @see PropertyChangeSupport#addPropertyChangeListener(PropertyChangeListener)
	 *//*
	public final void addPropertyChangeListener(PropertyChangeListener listener)
	{
		if(listener == null)
		{
			return;
		}
		if(listener instanceof PropertyChangeListenerProxy) 
		{
		    PropertyChangeListenerProxy proxy = (PropertyChangeListenerProxy)listener;
		    addPropertyChangeListener(proxy.getPropertyName(), (PropertyChangeListener)proxy.getListener());
		}else
		{
			beanAdapter.addPropertyChangeListener(listener);
		}
	}
	*//**
	 * @see PropertyChangeSupport#removePropertyChangeListener(PropertyChangeListener)
	 *//*
	public final void removePropertyChangeListener(PropertyChangeListener listener)
	{
		if(listener == null)
		{
			return;
		}
		if(listener instanceof PropertyChangeListenerProxy) 
		{
		    PropertyChangeListenerProxy proxy = (PropertyChangeListenerProxy)listener;
		    removePropertyChangeListener(proxy.getPropertyName(), (PropertyChangeListener)proxy.getListener());
		}else
		{
			beanAdapter.removePropertyChangeListener(listener);
		}
	}
	*//**
     * @see PropertyChangeSupport#addPropertyChangeListener(String, PropertyChangeListener)
     *//*
	public final void addPropertyChangeListener(String propertyName, PropertyChangeListener listener)
	{
		if(PROPERTY_TRIGGER_CHANNEL.equals(propertyName)
				|| PROPERTY_BUFFERING.equals(propertyName))
		{
			propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
		}else
		{
			beanAdapter.addPropertyChangeListener(propertyName, listener);
		}
	}

	*//**
	 * @see PropertyChangeSupport#removePropertyChangeListener(String, PropertyChangeListener)
	 *//*
	public final void removePropertyChangeListener(String propertyName, PropertyChangeListener listener)
	{
		if(PROPERTY_TRIGGER_CHANNEL.equals(propertyName)
				|| PROPERTY_BUFFERING.equals(propertyName))
		{
			propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
		}else
		{
			beanAdapter.removePropertyChangeListener(propertyName, listener);
		}
	}
	*//**
	 * @see PresentationModelAdapterImpl#addBeanPropertyChangeListener(PropertyChangeListener)
	 *//*
	public void addBeanPropertyChangeListener(PropertyChangeListener listener)
	{
		beanAdapter.addBeanPropertyChangeListener(listener);
	}

	*//**
	 * @see PresentationModelAdapterImpl#removeBeanPropertyChangeListener(PropertyChangeListener)
	 *//*
	public void removeBeanPropertyChangeListener(PropertyChangeListener listener)
	{
		beanAdapter.removeBeanPropertyChangeListener(listener);
	}

	*//**
	 * @see PresentationModelAdapterImpl#addBeanPropertyChangeListener(String,
	 *      PropertyChangeListener)
	 *//*
	public void addBeanPropertyChangeListener(String propertyName, PropertyChangeListener listener)
	{
		beanAdapter.addBeanPropertyChangeListener(propertyName, listener);
	}

	*//**
	 * @see PresentationModelAdapterImpl#removeBeanPropertyChangeListener(String,
	 *      PropertyChangeListener)
	 *//*
	public void removeBeanPropertyChangeListener(String propertyName, PropertyChangeListener listener)
	{
		beanAdapter.removeBeanPropertyChangeListener(propertyName, listener);
	}

	*//**
	 * @see PresentationModelAdapterImpl#getBeanPropertyChangeListeners()
	 *//*
	public PropertyChangeListener[] getBeanPropertyChangeListeners()
	{
		return beanAdapter.getBeanPropertyChangeListeners();
	}

	*//**
	 * @see PresentationModelAdapterImpl#getBeanPropertyChangeListeners(String)
	 *//*
	public PropertyChangeListener[] getBeanPropertyChangeListeners(String propertyName)
	{
		return beanAdapter.getBeanPropertyChangeListeners(propertyName);
	}

	*//**
	 * @see PresentationModelAdapterImpl#release()
	 *//*
	public void release()
	{
		beanAdapter.release();
	}

	*//**
	 * Updates the buffering state if a model buffering state changed.
	 *//*
	private final class BufferingStateChangeHandler implements PropertyChangeListener
	{
		*//**
		 * A registered BufferedValueModel has reported a change in its
		 * <em>buffering</em> state. Update this model's buffering state.
		 *//*
		public void propertyChange(PropertyChangeEvent event)
		{
			updateBufferingState(((Boolean)event.getNewValue()).booleanValue());
		}

	}
*/
}
