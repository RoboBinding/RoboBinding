package org.robobinding.property;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.robobinding.util.RandomValues;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class PropertyDescriptorTest {
	@Test
	public void givenReadOnlyProperty_thenIsReadableButNotWritable() {
		PropertyDescriptor readOnlyProperty = newPropertyDescriptor(Bean.READ_ONLY_PROPERTY);

		assertThat(readOnlyProperty.isReadable(), is(true));
		assertThat(readOnlyProperty.isWritable(), is(false));

	}

	private PropertyDescriptor newPropertyDescriptor(String propertyName) {
		return PropertyUtils.findPropertyDescriptor(Bean.class, propertyName);
	}

	@Test
	public void givenReadWriteProperty_thenAreBothReadableAndWritable() {
		PropertyDescriptor readOnlyProperty = newPropertyDescriptor(Bean.READ_WRITE_PROPERTY);

		assertThat(readOnlyProperty.isReadable(), is(true));
		assertThat(readOnlyProperty.isWritable(), is(true));
	}

	@Test(expected = RuntimeException.class)
	public void givenReadOnlyProperty_whenDoSet_thenThrowException() {
		PropertyDescriptor readOnlyProperty = newPropertyDescriptor(Bean.READ_ONLY_PROPERTY);

		readOnlyProperty.doSet(new Bean(), new Object());
	}

	@Test
	public void givenWriteOnlyProperty_whenSetValue_thenValueSet() {
		PropertyDescriptor writeOnlyProperty = newPropertyDescriptor(Bean.WRITE_ONLY_PROPERTY);

		Bean bean = new Bean();
		boolean newValue = RandomValues.trueOrFalse();
		writeOnlyProperty.doSet(bean, newValue);

		assertThat(bean.writeOnlyPropertyValue, is(newValue));
	}

	@Test(expected = RuntimeException.class)
	public void givenWriteOnlyProperty_whenGetValue_thenThrowException() {
		PropertyDescriptor writeOnlyProperty = newPropertyDescriptor(Bean.WRITE_ONLY_PROPERTY);

		writeOnlyProperty.doGet(new Bean());
	}

	@Test
	public void givenReadableProperty_whenCheckReadable_thenPassCheck() {
		PropertyDescriptor readableProperty = newPropertyDescriptor(Bean.READ_ONLY_PROPERTY);

		readableProperty.checkReadable();
	}

	@Test(expected = RuntimeException.class)
	public void givenNonReadableProperty_whenCheckReadable_thenThrowException() {
		PropertyDescriptor nonReadableProperty = newPropertyDescriptor(Bean.WRITE_ONLY_PROPERTY);

		nonReadableProperty.checkReadable();
	}

	@Test
	public void givenWritableProperty_whenCheckWritable_thenPassCheck() {
		PropertyDescriptor writableProperty = newPropertyDescriptor(Bean.WRITE_ONLY_PROPERTY);

		writableProperty.checkWritable();
	}

	@Test(expected = RuntimeException.class)
	public void givenNonWritableProperty_whenCheckWritable_thenThrowException() {
		PropertyDescriptor nonWritableProperty = newPropertyDescriptor(Bean.READ_ONLY_PROPERTY);

		nonWritableProperty.checkWritable();
	}

	@Test(expected = IllegalArgumentException.class)
	public void givenABooleanWritableProperty_whenDoSetWithStringValue_then() {
		PropertyDescriptor booleanWritableProperty = newPropertyDescriptor(Bean.BOOLEAN_WRITABLE_PROPERTY);

		booleanWritableProperty.doSet(new Bean(), "newText");
	}

	public static class Bean {
		public static final String READ_ONLY_PROPERTY = "readOnlyProperty";
		public static final String WRITE_ONLY_PROPERTY = "writeOnlyProperty";
		public static final String READ_WRITE_PROPERTY = "readWriteProperty";
		public static final String BOOLEAN_WRITABLE_PROPERTY = "booleanWritableProperty";

		public boolean writeOnlyPropertyValue;

		public Object getReadOnlyProperty() {
			return null;
		}

		public void setWriteOnlyProperty(boolean newValue) {
			this.writeOnlyPropertyValue = newValue;
		}

		public Object getReadWriteProperty() {
			return null;
		}

		public void setReadWriteProperty(Object o) {

		}

		public void setBooleanWritableProperty(boolean newValue) {

		}
	}
}
