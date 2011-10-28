package robobinding.value.experimental;

import java.beans.PropertyChangeListener;



/**
 * A Trigger is intended to be used as trigger channel for instances of BufferedValueModel. 
 * API users shall trigger commit and flush events using {@code #commit} and {@code #flush}.
 * <p>
 * 
 * BufferedValueHolder performs commit and flush events only if the trigger
 * channel value reports a change. The methods {@code #commit} and {@code #flush} 
 * check for the current state and guarantee that the appropriate {@code PropertyChangeEvent}
 * is fired. On the other hand, the implementation minimizes the number of
 * events necessary to commit or flush buffered values.
 * <p>
 * 
 * @see BufferedValueModel
 */
public final class Trigger
{
	private TriggerAdapter adapter;

	public Trigger()
	{
		adapter = new TriggerAdapter(TriggerState.NEUTRAL);
	}
	/**
	 * Triggers a commit event in BufferedValueModels that share this Trigger.
	 * All the listeners are notified about a value change to this new value.
	 */
	public void commit()
	{
		adapter.commit();
	}

	/**
	 * Triggers a flush event in BufferedValueModels that share this Trigger.
	 * All the listeners are notified about a value change to the new value.
	 */
	public void flush()
	{
		adapter.flush();
	}
    public void addValueChangeListener(PropertyChangeListener listener)
    {
    	adapter.addValueChangeListener(listener);
    }
    public void removeValueChangeListener(PropertyChangeListener listener)
    {
    	adapter.removeValueChangeListener(listener);
    }
	private static class TriggerAdapter extends AbstractValueModel<TriggerState>
	{
		public TriggerAdapter(TriggerState value)
		{
			super(value);
		}
		/**
		 *	@see Trigger#commit()
		 */
		public void commit()
		{
			if(TriggerState.COMMIT.equals(getValue()))
			{
				setValue(TriggerState.NEUTRAL);
			}
			setValue(TriggerState.COMMIT);
		}
		/**
		 *	@see Trigger#flush()
		 */
		public void flush()
		{
			if(TriggerState.FLUSH.equals(getValue()))
			{
				setValue(TriggerState.NEUTRAL);
			}
			setValue(TriggerState.FLUSH);
		}
	}
}
