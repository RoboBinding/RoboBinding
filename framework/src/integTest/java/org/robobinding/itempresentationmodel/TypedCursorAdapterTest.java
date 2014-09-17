/**
 * 
 */
package org.robobinding.itempresentationmodel;

import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import android.database.Cursor;

import com.google.common.collect.Lists;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class TypedCursorAdapterTest {
	private List<Bean> beans;
	private TypedCursorAdapter<Bean> typedCursor;

	@Before
	public void setUp() {
		beans = Lists.newArrayList();
		beans.add(new Bean("bean1"));
		beans.add(new Bean("bean2"));
		Cursor cursor = BeanCursor.create(beans, Bean.class);

		typedCursor = new TypedCursorAdapter<Bean>(cursor, new BeanRowMapper());
	}

	@Test
	public void whenGetObjectAtPosition_thenReturnExpectedInstance() {
		Bean actualBean2 = typedCursor.getObjectAtPosition(1);

		Bean expectedBean2 = beans.get(1);
		Assert.assertEquals(expectedBean2, actualBean2);
	}

	@Test
	public void givenPosition_whenGetObjectAtPositionWithDifferentIndex_thenPositionUnchanged() {
		Assert.assertTrue(typedCursor.moveToFirst());
		Assert.assertEquals(0, typedCursor.getPosition());

		typedCursor.getObjectAtPosition(1);

		Assert.assertEquals(0, typedCursor.getPosition());
	}

	public static class BeanRowMapper implements RowMapper<Bean> {
		@Override
		public Bean mapRow(Cursor cursor) {
			int columnIndex = cursor.getColumnIndex(Bean.NAME);
			String name = cursor.getString(columnIndex);
			return new Bean(name);
		}
	}

	public static class Bean {
		public static final String NAME = "name";

		private String name;

		public Bean(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		@Override
		public boolean equals(Object other) {
			if (this == other)
				return true;
			if (!(other instanceof Bean))
				return false;

			final Bean that = (Bean) other;
			return new EqualsBuilder().append(name, that.name).isEquals();
		}

		@Override
		public int hashCode() {
			return new HashCodeBuilder().append(name).toHashCode();
		}
	}
}
