package robobinding.value;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import robobinding.utils.Validate;

/**
 * A ValueModel that wraps another ValueModel, the subject, and delays changes
 * of the subject's value. Returns the subject's value until a value has been
 * set. The buffered value is not written to the subject until the trigger
 * channel changes to {@code Boolean.TRUE}. The buffered value can be flushed by
 * changing the trigger channel value to {@code Boolean.FALSE}. Note that the
 * commit and flush events are performed only if the trigger channel fires a
 * change event. Since a plain ValueHolder fires no property change event if a
 * value is set that has been set before, it is recommended to use a
 * {@link Trigger} instead and invoke its {@code #triggerCommit} and
 * {@code triggerFlush} methods.
 * <p>
 * 
 * The BufferedValueModel has been designed to behave much like its subject when
 * accessing the value. Therefore it throws all exceptions that would arise when
 * accessing the subject directly. 
 * <p>
 * 
 * This class provides the bound read-write properties <em>subject</em> and
 * <em>triggerChannel</em> for the subject and trigger channel and a bound
 * read-only property <em>buffering</em> for the buffering state.
 * <p>
 * 
 * The BufferedValueModel registers listeners with the subject and trigger
 * channel. It is recommended to remove these listeners by invoking
 * {@code #release} if the subject and trigger channel live much longer than
 * this buffer. After {@code #release} has been called you must not use the
 * BufferedValueModel instance any longer. As an alternative you may use event
 * listener lists in subjects and trigger channels that are based on
 * {@code WeakReference}s.
 * <p>
 * 
 * If the subject value changes while this model is in buffering state this
 * change won't show through as this model's new value. If you want to update
 * the value whenever the subject value changes, register a listener with the
 * subject value and flush this model's trigger.
 * <p>
 */
public final class BufferedValueModel<T> extends AbstractValueModel0<T>
{
	public static final String PROPERTY_BUFFERING = "buffering";
	public static final String PROPERTY_SOURCE = "source";
	public static final String PROPERTY_TRIGGER_CHANNEL = "triggerChannel";

	/**
	 * Holds the source that provides the underlying value of type
	 * {@code Object}.
	 */
	private ValueModel<T> source;

	private Trigger triggerChannel;

	/**
	 * Holds the buffered value. This value is ignored if we are not buffering.
	 */
	private T bufferedValue;

	/**
	 * Indicates whether a value has been assigned since the last trigger
	 * change.
	 */
	private boolean valueAssigned;

	/**
	 * Holds a PropertyChangeListener that observes subject value changes.
	 */
	private final ValueChangeHandler valueChangeHandler;

	/**
	 * Holds a PropertyChangeListener that observes trigger changes.
	 */
	private final TriggerChangeHandler triggerChangeHandler;

	/**
	 * @param subject the value model to be buffered
	 * @param triggerChannel the value model that triggers the commit or flush event. Must not be {@code null}
	 */
	public BufferedValueModel(ValueModel<T> subject, Trigger triggerChannel)
	{
		super(false);
		valueChangeHandler = new ValueChangeHandler();
		triggerChangeHandler = new TriggerChangeHandler();

		setSource(subject);
		setTriggerChannel(triggerChannel);
		setBuffering(false);
	}

	/**
	 * Returns the subject, i.e. the underlying ValueModel that provides the
	 * unbuffered value.
	 * 
	 * @return the ValueModel that provides the unbuffered value
	 */
	public ValueModel<T> getSource()
	{
		return source;
	}

	/**
	 * Sets a new subject ValueModel, i.e. the model that provides the
	 * unbuffered value. Notifies all listeners that the <i>subject</i> property
	 * has changed.
	 * 
	 * @param newSubject the subject ValueModel to be set
	 */
	public void setSource(ValueModel<T> newSubject)
	{
		ValueModel<T> oldSubject = getSource();
		Object oldValue = null;
		if (oldSubject != null)
		{
			ReadAccessResult oldReadValue = readBufferedOrSubjectValue();
			oldValue = oldReadValue.value;
			oldSubject.removeValueChangeListener(valueChangeHandler);
		}
		source = newSubject;
		if (newSubject != null)
		{
			newSubject.addValueChangeListener(valueChangeHandler);
		}
		firePropertyChange(PROPERTY_SOURCE, oldSubject, newSubject);
		if (isBuffering())
		{
			return;
		}
		ReadAccessResult newReadValue = readBufferedOrSubjectValue();
		Object newValue = newReadValue.value;
		// Note that the old and/or new value may be null
		// just because the property is read-only.
		if (oldValue != null || newValue != null)
		{
			fireValueChange(oldValue, newValue, true);
		}
	}

	/**
	 * Returns the ValueModel that is used to trigger commit and flush events.
	 * 
	 * @return the ValueModel that is used to trigger commit and flush events
	 */
	public Trigger getTriggerChannel()
	{
		return triggerChannel;
	}

	/**
	 * Sets the ValueModel that triggers the commit and flush events.
	 * 
	 * @param newTriggerChannel the ValueModel to be set as trigger channel
	 */
	public void setTriggerChannel(Trigger newTriggerChannel)
	{
		Validate.notNull(newTriggerChannel, "The trigger channel must not be null.");
		Trigger oldTriggerChannel = getTriggerChannel();
		if (oldTriggerChannel != null)
		{
			oldTriggerChannel.removeValueChangeListener(triggerChangeHandler);
		}
		
		triggerChannel = newTriggerChannel;
		newTriggerChannel.addValueChangeListener(triggerChangeHandler);
		firePropertyChange(PROPERTY_TRIGGER_CHANNEL, oldTriggerChannel, newTriggerChannel);
	}

	/**
	 * Returns the subject's value if no value has been set since the last
	 * commit or flush, and returns the buffered value otherwise.
	 * 
	 * @return the buffered value
	 */
	@Override
	public T getValue()
	{
		Validate.notNull(source, "The subject must not be null " + "when reading a value from a BufferedValueModel.");
		return isBuffering() ? bufferedValue : source.getValue();
	}

	/**
	 * Sets a new buffered value and turns this BufferedValueModel into the
	 * buffering state. The buffered value is not provided to the underlying
	 * model until the trigger channel indicates a commit. 
	 * <p>
	 * 
	 * The above semantics is easy to understand, however it is tempting to
	 * check the new value against the current subject value to avoid that the
	 * buffer unnecessary turns into the buffering state. But here's a problem.
	 * Let's say the subject value is "first" at buffer creation time, and let's
	 * say the subject value has changed in the meantime to "second". Now
	 * someone sets the value "second" to this buffer. The subject value and the
	 * value to be set are equal. Shall we buffer? Also, this decision would
	 * depend on the ability to read the subject. The semantics would depend on
	 * the subject' state and capabilities.
	 * <p>
	 * 
	 * It is often sufficient to observe the buffering state when enabling or
	 * disabling a commit command button like "OK" or "Apply". And later check
	 * the <em>changed</em> state in a PresentationModel. You may want to do
	 * better and may want to observe a property like "defersTrueChange" that
	 * indicates whether flushing a buffer will actually change the subject. But
	 * note that such a state may change with subject value changes, which may
	 * be hard to understand for a user.
	 */
	@Override
	public void setValue(T newBufferedValue)
	{
		Validate.notNull(source, "The subject must not be null " + "when setting a value to a BufferedValueModel.");
		ReadAccessResult oldReadValue = readBufferedOrSubjectValue();
		Object oldValue = oldReadValue.value;
		bufferedValue = newBufferedValue;
		setBuffering(true);
		if (oldReadValue.readable && oldValue == newBufferedValue)
		{
			return;
		}
		fireValueChange(oldValue, newBufferedValue, true);
	}

	/**
	 * Tries to lookup the current buffered or subject value and returns this
	 * value plus a marker that indicates whether the read-access succeeded or
	 * failed. The latter situation arises in an attempt to read a value from a
	 * write-only subject if this BufferedValueModel is not buffering and if
	 * this model changes its subject.
	 * 
	 * @return the current value plus a boolean that indicates the success or failure
	 */
	private ReadAccessResult readBufferedOrSubjectValue()
	{
		try
		{
			Object value = getValue(); // May fail with write-only models
			return new ReadAccessResult(value, true);
		} catch (Exception e)
		{
			return new ReadAccessResult(null, false);
		}
	}

	/**
	 * Removes the PropertyChangeListeners from the subject and trigger channel.
	 * <p>
	 * 
	 * To avoid memory leaks it is recommended to invoke this method if the
	 * subject and trigger channel live much longer than this buffer. Once
	 * #release has been invoked the BufferedValueModel instance must not be
	 * used any longer.
	 * <p>
	 * 
	 * As an alternative you may use event listener lists in subjects and
	 * trigger channels that are based on {@code WeakReference}s.
	 * 
	 * @see java.lang.ref.WeakReference
	 */
	public void release()
	{
		ValueModel<T> aSubject = getSource();
		if (aSubject != null)
		{
			aSubject.removeValueChangeListener(valueChangeHandler);
		}
		
		Trigger aTriggerChannel = getTriggerChannel();
		aTriggerChannel.removeValueChangeListener(triggerChangeHandler);
	}

	/**
	 * Returns whether this model buffers a value or not, that is, whether a
	 * value has been assigned since the last commit or flush.
	 * 
	 * @return true if a value has been assigned since the last commit or flush
	 */
	boolean isBuffering()
	{
		return valueAssigned;
	}

	private void setBuffering(boolean newValue)
	{
		boolean oldValue = isBuffering();
		valueAssigned = newValue;
		firePropertyChange(PROPERTY_BUFFERING, oldValue, newValue);
	}

	/**
	 * Sets the buffered value as new subject value - if any value has been set.
	 * After this commit this BufferedValueModel behaves as if no value has been
	 * set before. This method is invoked if the trigger has changed to
	 * {@code Boolean.TRUE}.
	 * <p>
	 * 
	 * Since the subject's value is assigned <em>after</em> the buffer marker is
	 * reset, subject change notifications will be handled. In this case the
	 * subject's old value is not this BufferedValueModel's old value; instead
	 * the old value reported to listeners of this model is the formerly
	 * buffered value.
	 */
	private void commit()
	{
		Validate.notNull(source, "The subject must not be null " + "while committing a value in a BufferedValueModel.");
		if(isBuffering())
		{
			setBuffering(false);
			valueChangeHandler.oldValue = bufferedValue;
			source.setValue(bufferedValue);
			valueChangeHandler.oldValue = null;
		}
	}

	/**
	 * Flushes the buffered value. This method is invoked if the trigger has
	 * changed to {@code Boolean.FALSE}. After this flush this
	 * BufferedValueModel behaves as if no value has been set before.
	 */
	private void flush()
	{
		Object oldValue = getValue();
		setBuffering(false);
		Object newValue = getValue();
		fireValueChange(oldValue, newValue, true);
	}

	@Override
	protected String paramString()
	{
		return "value=" + valueString() + "; buffering" + isBuffering();
	}

	/**
	 * Describes the result of a subject value read-access plus a marker that
	 * indicates if the value could be read or not. The latter is used in
	 * {@code #setValue} to suppress some unnecessary change notifications in
	 * case the value could be read successfully.
	 * 
	 * @see BufferedValueModel#setValue(Object)
	 */
	private static final class ReadAccessResult
	{

		private final Object value;
		private final boolean readable;

		private ReadAccessResult(Object value, boolean readable)
		{
			this.value = value;
			this.readable = readable;
		}

	}

	/**
	 * Listens to changes of the subject.
	 */
	private final class ValueChangeHandler implements PropertyChangeListener
	{

		private Object oldValue;

		/**
		 * The subject's value has changed. Notifies this BufferedValueModel's
		 * listeners if we are not buffering, does nothing otherwise.
		 * <p>
		 */
		public void propertyChange(PropertyChangeEvent event)
		{
			if (!isBuffering())
			{
				fireValueChange(oldValue != null ? oldValue : event.getOldValue(), event.getNewValue(), true);
			}
		}
	}

	/**
	 * Listens to changes of the trigger channel.
	 */
	private final class TriggerChangeHandler implements PropertyChangeListener
	{

		/**
		 * The trigger has been changed. Commits or flushes the buffered value.
		 */
		public void propertyChange(PropertyChangeEvent event)
		{
			if (TriggerState.COMMIT.equals(event.getNewValue()))
			{
				commit();
			} else if (TriggerState.FLUSH.equals(event.getNewValue()))
			{
				flush();
			}
		}
	}

}
