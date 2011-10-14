package robobinding.value;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public final class BufferedValueModelTest
{
/*
	private static final Object INITIAL_VALUE = "initial value";
	private static final Object RESET_VALUE = "reset value";

	private ValueModel<Object> source;
	private Trigger triggerChannel;
	
	@Before
	public void setUp()
	{
		source = ValueHolders.create(INITIAL_VALUE);
		triggerChannel = new Trigger();
	}

	@Test
	public void testReturnsSourceValueIfNoValueAssigned()
	{
		BufferedValueModel<Object> buffer = createDefaultBufferedValueModel();
		Assert.assertEquals("Buffer value equals the source value before any changes.", buffer.getValue(), source.getValue());

		source.setValue("change1");
		Assert.assertEquals("Buffer value equals the source value changes as long as no value has been assigned.", buffer.getValue(), source.getValue());

		source.setValue(null);
		Assert.assertEquals("Buffer value equals the source value changes as long as no value has been assigned.", buffer.getValue(), source.getValue());
	}
	@Test
	public void testReturnsBufferedValueIfValueAssigned()
	{
		BufferedValueModel<Object> buffer = createDefaultBufferedValueModel();

		Object newValue1 = source.getValue();
		buffer.setValue(newValue1);
		source.setValue("source1");
		Assert.assertSame("Buffer value == new value once a value has been assigned.", buffer.getValue(), newValue1);

		Object newValue2 = "change2";
		buffer.setValue(newValue2);
		Assert.assertSame("Buffer value == new value once a value has been assigned.", buffer.getValue(), newValue2);
		
		Object newValue3 = "change3";
		buffer.setValue(newValue3);
		source.setValue("source3");
		Assert.assertSame("Buffer value == new value once a value has been assigned.", buffer.getValue(), newValue3);
	}
	@Test
	public void testIgnoreSsourceValuesIfValueAssigned()
	{
		BufferedValueModel<Object> buffer = createDefaultBufferedValueModel();

		Object newValue1 = "change1";
		buffer.setValue(newValue1);
		source.setValue("source1");
		Assert.assertSame("Buffer value == new value once a value has been assigned.", buffer.getValue(), newValue1);
		source.setValue(null);
		Assert.assertSame("Buffer value == new value once a value has been assigned.", buffer.getValue(), newValue1);
	}
	@Test
	public void testReturnsSourceValueAfterCommit()
	{
		BufferedValueModel<Object> buffer = createDefaultBufferedValueModel();
		buffer.setValue("change1");
		
		triggerChannel.commit();
		Assert.assertEquals("Buffer value equals the source value after a commit.", buffer.getValue(), source.getValue());

		source.setValue("source1");
		Assert.assertEquals("Buffer value equals the source value after a commit.", buffer.getValue(), source.getValue());
	}
	@Test
	public void testReturnsSourceValueAfterFlush()
	{
		BufferedValueModel<Object> buffer = createDefaultBufferedValueModel();
		buffer.setValue("change1");
		
		triggerChannel.flush();
		Assert.assertEquals("Buffer value equals the source value after a flush.", source.getValue(), buffer.getValue());

		source.setValue("source2");
		Assert.assertEquals("Buffer value equals the source value after a flush.", source.getValue(), buffer.getValue());
	}
	@Test
	public void testSourceValuesUnchangedBeforeCommit()
	{
		BufferedValueModel<Object> buffer = createDefaultBufferedValueModel();
		Object oldsourceValue = source.getValue();
		
		buffer.setValue("changedBuffer1");
		Assert.assertEquals("Buffer changes do not change the source value before a commit.", source.getValue(), oldsourceValue);
		
		buffer.setValue(null);
		Assert.assertEquals("Buffer changes do not change the source value before a commit.", source.getValue(), oldsourceValue);
	}
	@Test
	public void testCommitChangessourceValue()
	{
		BufferedValueModel<Object> buffer = createDefaultBufferedValueModel();
		Object oldsourceValue = source.getValue();
		Object newValue1 = "change1";
		
		buffer.setValue(newValue1);
		Assert.assertEquals("source value is unchanged before the first commit.", source.getValue(), oldsourceValue);
		
		triggerChannel.commit();
		Assert.assertEquals("source value is the new value after the first commit.", source.getValue(), newValue1);
		//one more time.
		Object newValue2 = "change2";
		buffer.setValue(newValue2);
		Assert.assertEquals("source value is unchanged before the second commit.", source.getValue(), newValue1);
		
		triggerChannel.commit();
		Assert.assertEquals("source value is the new value after the second commit.", source.getValue(), newValue2);
	}
	@Test
	public void testFlushResetsTheBufferedValue()
	{
		BufferedValueModel<Object> buffer = createDefaultBufferedValueModel();
		Object newValue1 = "new value1";
		
		buffer.setValue(newValue1);
		Assert.assertSame("Buffer value reflects changes before the first flush.", buffer.getValue(), newValue1);
		
		triggerChannel.flush();
		Assert.assertEquals("Buffer value is the source value after the first flush.", buffer.getValue(), source.getValue());

		//one more time.
		Object newValue2 = "new value2";
		
		buffer.setValue(newValue2);
		Assert.assertSame("Buffer value reflects changes before the second flush.", buffer.getValue(), newValue2);
		
		triggerChannel.flush();
		Assert.assertEquals("Buffer value is the source value after the second flush.", buffer.getValue(), source.getValue());
	}
	@Test
	public void testIsNotBufferingIfNoValueAssigned()
	{
		BufferedValueModel<Object> buffer = createDefaultBufferedValueModel();
		Assert.assertFalse("Initially the buffer does not buffer.", buffer.isBuffering());

		Object newValue = "change1";
		source.setValue(newValue);
		Assert.assertFalse("source changes do not affect the buffering state.", buffer.isBuffering());
	}
	@Test
	public void testIsBufferingIfValueAssigned()
	{
		BufferedValueModel<Object> buffer = createDefaultBufferedValueModel();
		buffer.setValue("change1");
		Assert.assertTrue("Setting a value (even the source's value) turns on buffering.", buffer.isBuffering());

		buffer.setValue(source.getValue());
		Assert.assertTrue("Resetting the value to the source's value doesn't affect buffering.", buffer.isBuffering());
	}
	@Test
	public void testIsNotBufferingAfterCommit()
	{
		BufferedValueModel<Object> buffer = createDefaultBufferedValueModel();
		
		buffer.setValue("change1");
		triggerChannel.commit();
		Assert.assertFalse("The buffer does not buffer after a commit.", buffer.isBuffering());
	}
	@Test
	public void testIsNotBufferingAfterFlush()
	{
		BufferedValueModel<Object> buffer = createDefaultBufferedValueModel();
		
		buffer.setValue("change1");
		triggerChannel.flush();
		Assert.assertFalse("The buffer does not buffer after a flush.", buffer.isBuffering());
	}
	@Test
	public void testFiresBufferingChanges()
	{
		Trigger trigger2 = new Trigger();
		BufferedValueModel<Object> buffer = new BufferedValueModel<Object>(source, trigger2);
		PropertyChangeReport changeReport = new PropertyChangeReport();
		buffer.addPropertyChangeListener("buffering", changeReport);

		Assert.assertEquals("Initial state.", 0, changeReport.getEventCount());
		
		buffer.getValue();
		Assert.assertEquals("Reading initial value.", 0, changeReport.getEventCount());
		
		buffer.setSource(ValueHolders.create());
		Assert.assertEquals("After source change.", 0, changeReport.getEventCount());
		
		buffer.setTriggerChannel(triggerChannel);
		Assert.assertEquals("After trigger channel change.", 0, changeReport.getEventCount());

		buffer.setValue("now buffering");
		Assert.assertEquals("After setting the first value.", 1, changeReport.getEventCount());
		
		buffer.setValue("still buffering");
		Assert.assertEquals("After setting the second value.", 1, changeReport.getEventCount());
		
		buffer.getValue();
		Assert.assertEquals("Reading buffered value.", 1, changeReport.getEventCount());

		triggerChannel.commit();
		Assert.assertEquals("After committing.", 2, changeReport.getEventCount());
		
		buffer.getValue();
		Assert.assertEquals("Reading unbuffered value.", 2, changeReport.getEventCount());

		buffer.setValue("buffering again");
		Assert.assertEquals("After second buffering switch.", 3, changeReport.getEventCount());
		triggerChannel.flush();
		Assert.assertEquals("After flushing.", 4, changeReport.getEventCount());
		
		buffer.getValue();
		Assert.assertEquals("Reading unbuffered value.", 4, changeReport.getEventCount());
	}
	@Test
	public void testSetValueFiresIfNewValueAndSourceChange()
	{
		Object initialValue = new Integer(0);
		Object oldValue = new Integer(1);
		Object newValue = new Integer(2);
		source.setValue(initialValue);
		BufferedValueModel<Object> buffer = new BufferedValueModel<Object>(source, new Trigger());
		PropertyChangeReport changeReport = new PropertyChangeReport();
		buffer.addValueChangeListener(changeReport);
		
		buffer.setValue(oldValue);
		Assert.assertEquals("Buffer fired a value change.", 1, changeReport.getEventCount());
		source.setValue(newValue);
		Assert.assertEquals("Setting the source in buffered state fires no value change.", 1, changeReport.getEventCount());

		//One more time.
		buffer.setValue(newValue);
		Assert.assertEquals("Setting the buffer from 1 to 2 fires a value change.", 2, changeReport.getEventCount());
	}
	@Test
	public void testReturnsCurrentSourceValue()
	{
		Object value1_1 = "value1.1";
		Object value1_2 = "value1.2";
		Object value1_3 = "value1.3";
		Object value2_1 = "value2.1";
		Object value2_2 = "value2.2";

		ValueModel<Object> source1 = ValueHolders.create(value1_1);
		ValueModel<Object> source2 = ValueHolders.create(value2_1);

		BufferedValueModel<Object> buffer = new BufferedValueModel<Object>(source1, triggerChannel);

		Assert.assertSame("Buffer returns the source value of the current source1.", buffer.getValue(), source1.getValue());

		source1.setValue(value1_2);
		Assert.assertSame("Buffer returns the new source value of the current source1.", buffer.getValue(), source1.getValue());

		buffer.setSource(source2);
		Assert.assertSame("Buffer returns the source value of the current source2.", buffer.getValue(), source2.getValue());

		source1.setValue(value1_3);
		source2.setValue(value2_2);
		Assert.assertSame("Buffer returns the new source value of the current source2.", buffer.getValue(), source2.getValue());
	}
	@Test
	public void testListensToCurrentSource()
	{
		Object value1_1 = "value1.1";
		Object value1_2 = "value1.2";
		Object value2_1 = "value2.1";
		Object value2_2 = "value2.2";

		ValueModel<Object> source1 = ValueHolders.create();
		ValueModel<Object> source2 = ValueHolders.create();

		BufferedValueModel<Object> buffer = new BufferedValueModel<Object>(source1, triggerChannel);
		PropertyChangeReport changeReport = new PropertyChangeReport();
		buffer.addValueChangeListener(changeReport);

		source1.setValue(value1_1);
		Assert.assertEquals("Value change.", 1, changeReport.getEventCount());

		source2.setValue(value2_1);
		Assert.assertEquals("No value change.", 1, changeReport.getEventCount());

		buffer.setSource(source2);
		Assert.assertEquals("Value changed because of source change.", 2, changeReport.getEventCount());

		source1.setValue(value1_2);
		Assert.assertEquals("No value change.", 2, changeReport.getEventCount());

		source2.setValue(value2_2);
		Assert.assertEquals("Value change.", 3, changeReport.getEventCount());
	}
	@Test
	public void testListensToCurrentTriggerChannel()
	{
		Trigger trigger1 = new Trigger();
		Trigger trigger2 = new Trigger();

		BufferedValueModel<Object> buffer = new BufferedValueModel<Object>(source, trigger1);
		buffer.setValue("change1");
		Object sourceValue = source.getValue();
		Object bufferedValue = buffer.getValue();
		
		trigger2.commit();
		Assert.assertEquals("Changing the unrelated trigger2 to true has no effect on the source.", source.getValue(), sourceValue);
		Assert.assertSame("Changing the unrelated trigger2 to true has no effect on the buffer.", buffer.getValue(), bufferedValue);

		trigger2.flush();
		Assert.assertEquals("Changing the unrelated trigger2 to false has no effect on the source.", source.getValue(), sourceValue);
		Assert.assertSame("Changing the unrelated trigger2 to false has no effect on the buffer.", buffer.getValue(), bufferedValue);

		// Change the trigger channel to trigger2.
		buffer.setTriggerChannel(trigger2);
		Assert.assertSame("Trigger channel has been changed.", buffer.getTriggerChannel(), trigger2);

		trigger1.commit();
		Assert.assertEquals("Changing the unrelated trigger1 to true has no effect on the source.", source.getValue(), sourceValue);
		Assert.assertSame("Changing the unrelated trigger1 to true has no effect on the buffer.", buffer.getValue(), bufferedValue);

		trigger1.flush();
		Assert.assertEquals("Changing the unrelated trigger1 to false has no effect on the source.", source.getValue(), sourceValue);
		Assert.assertSame("Changing the unrelated trigger1 to false has no effect on the buffer.", buffer.getValue(), bufferedValue);

		//using trigger2.
		trigger2.commit();
		Assert.assertEquals("Commits the buffered value.", buffer.getValue(), source.getValue());

		buffer.setValue("change2");
		sourceValue = source.getValue();
		trigger2.flush();
		Assert.assertEquals("Flushes the buffered value.", buffer.getValue(), source.getValue());
		Assert.assertEquals("Flushes the buffered value.", buffer.getValue(), sourceValue);
	}
	@Test
	public void testPropagatesSourceChangesIfNoValueAssigned()
	{
		BufferedValueModel<Object> buffer = createDefaultBufferedValueModel();
		PropertyChangeReport changeReport = new PropertyChangeReport();
		buffer.addValueChangeListener(changeReport);

		source.setValue("change1");
		Assert.assertEquals("Value change.", 1, changeReport.getEventCount());

		source.setValue(null);
		Assert.assertEquals("Value change.", 2, changeReport.getEventCount());

		source.setValue("change2");
		Assert.assertEquals("Value change.", 3, changeReport.getEventCount());

		source.setValue(buffer.getValue());
		Assert.assertEquals("No value change.", 3, changeReport.getEventCount());
	}
	@Test
	public void testIgnoreSsourceChangesIfValueAssigned()
	{
		BufferedValueModel<Object> buffer = createDefaultBufferedValueModel();
		PropertyChangeReport changeReport = new PropertyChangeReport();
		buffer.setValue("new buffer");
		buffer.addValueChangeListener(changeReport);

		source.setValue("change1");
		Assert.assertEquals("Value change.", 0, changeReport.getEventCount());

		source.setValue(null);
		Assert.assertEquals("Value change.", 0, changeReport.getEventCount());
	}
	@Test
	public void testCommitFiresNoChangeOnSameOldAndNewValues()
	{
		BufferedValueModel<Object> buffer = createDefaultBufferedValueModel();
		buffer.setValue("value1");
		PropertyChangeReport changeReport = new PropertyChangeReport();
		buffer.addValueChangeListener(changeReport);

		Assert.assertEquals("No initial change.", 0, changeReport.getEventCount());
		triggerChannel.commit();
		Assert.assertEquals("First commit: no change.", 0, changeReport.getEventCount());

		buffer.setValue("value2");
		Assert.assertEquals("Setting a value: a change.", 1, changeReport.getEventCount());
		triggerChannel.commit();
		Assert.assertEquals("Second commit: no change.", 1, changeReport.getEventCount());
	}
	@Test
	public void testFlushFiresTrueValueChanges()
	{
		BufferedValueModel<Object> buffer = createDefaultBufferedValueModel();
		PropertyChangeReport changeReport = new PropertyChangeReport();
		buffer.setValue("new buffer");
		source.setValue("new source");
		buffer.addValueChangeListener(changeReport);
		
		triggerChannel.flush();
		Assert.assertEquals("First flush changes value.", 1, changeReport.getEventCount());

		buffer.setValue(source.getValue());
		Assert.assertEquals("Resetting value: no change.", 1, changeReport.getEventCount());
		triggerChannel.flush();
		Assert.assertEquals("Second flush: no change.", 1, changeReport.getEventCount());

		buffer.setValue("new buffer2");
		Assert.assertEquals("Second value change.", 2, changeReport.getEventCount());
		source.setValue("new source2");
		Assert.assertEquals("Setting new source value: no change.", 2, changeReport.getEventCount());
		
		buffer.setValue(source.getValue());
		Assert.assertEquals("Third value change.", 3, changeReport.getEventCount());
		triggerChannel.flush();
		Assert.assertEquals("Third flush: no change.", 3, changeReport.getEventCount());
	}
	@Test
	public void testValueNoficationAfterSourceChanged()
	{
		ValueModel<Object> initialsource = ValueHolders.create(INITIAL_VALUE);
		BufferedValueModel<Object> buffer = new BufferedValueModel<Object>(initialsource, new Trigger());
		PropertyChangeReport changeReport = new PropertyChangeReport();
		buffer.addValueChangeListener(changeReport);
		
		Object newValue = "changed";
		initialsource.setValue(newValue);
		Assert.assertEquals("Value changed on original source", 1, changeReport.getEventCount());
		
		ValueModel<Object> sourceWithSame = ValueHolders.create(newValue);
		buffer.setSource(sourceWithSame);
		Assert.assertEquals("source changed but same value as old", 1, changeReport.getEventCount());
		
		ValueModel<Object> source2 = ValueHolders.create((Object)"changedsourceWithDifferentValue");
		buffer.setSource(source2);
		Assert.assertEquals("Value changed by changing the source with different value", 2, changeReport.getEventCount());
	}
	@Test
	public void testRejectGetValueWhensourceIsNull()
	{
		BufferedValueModel<Object> buffer = new BufferedValueModel<Object>(null, triggerChannel);
		try
		{
			buffer.getValue();
			Assert.fail("The Buffer must reject attempts to read unbuffered values when the source is null.");
		}catch(IllegalArgumentException ex)
		{
			// The expected behavior
		}
		buffer.setSource(source);
		buffer.setValue("now buffering");
		buffer.setSource(null);
		try
		{
			buffer.getValue();
			Assert.fail("The Buffer must reject attempts to read buffered values when the source is null.");
		} catch (IllegalArgumentException ex)
		{
			// The expected behavior
		}
	}
	@Test
	public void testRejectSetValueWhenSourceIsNull()
	{
		BufferedValueModel<Object> buffer = new BufferedValueModel<Object>(null, triggerChannel);
		try
		{
			Object value = "valuewithoutsource??";
			buffer.setValue(value);
			Assert.fail("The Buffer must reject attempts to set values when the source is null.");
		} catch (IllegalArgumentException ex)
		{
			// The expected behavior
		}
		buffer.setSource(source);
		buffer.setValue("now buffering");
		buffer.setSource(null);
		try
		{
			buffer.setValue("a new value");
			Assert.fail("The Buffer must reject attempts to set values when the source is null - even if buffering.");
		} catch (IllegalArgumentException ex)
		{
			// The expected behavior
		}
	}
	@Test
	public void testRejectCommitWhensourceIsNull()
	{
		BufferedValueModel<Object> buffer = new BufferedValueModel<Object>(null, triggerChannel);
		try
		{
			triggerChannel.commit();
			Assert.fail("The Buffer must reject attempts to commit when the source is null.");
		} catch (IllegalArgumentException ex)
		{
			// The expected behavior
		}
		buffer.setSource(source);
		buffer.setValue("now buffering");
		buffer.setSource(null);
		try
		{
			triggerChannel.commit();
			Assert.fail("The Buffer must reject attempts to commit when the source is null - even if buffering.");
		} catch (IllegalArgumentException ex)
		{
			// The expected behavior
		}
	}
	@Test
	public void testRejectFlushWhenSourceIsNull()
	{
		BufferedValueModel<Object> buffer = new BufferedValueModel<Object>(null, triggerChannel);
		try
		{
			triggerChannel.flush();
			Assert.fail("The Buffer must reject attempts to commit when the source is null.");
		} catch (IllegalArgumentException ex)
		{
			// The expected behavior
		}
		buffer.setSource(source);
		buffer.setValue("now buffering");
		buffer.setSource(null);
		try
		{
			triggerChannel.flush();
			Assert.fail("The Buffer must reject attempts to commit when the source is null - even if buffering.");
		} catch (IllegalArgumentException ex)
		{
			// The expected behavior
		}
	}
	private BufferedValueModel<Object> createDefaultBufferedValueModel()
	{
		source.setValue(RESET_VALUE);
		return new BufferedValueModel<Object>(source, triggerChannel);
	}*/
}
