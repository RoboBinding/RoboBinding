/**
 * 
 */
package org.robobinding.itempresentationmodel;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robobinding.internal.com_google_common.collect.Lists;
import org.robobinding.internal.org_apache_commons_lang3.builder.EqualsBuilder;
import org.robobinding.internal.org_apache_commons_lang3.builder.HashCodeBuilder;
import org.robobinding.itempresentationmodel.BeanCursor;
import org.robobinding.itempresentationmodel.RowMapper;
import org.robobinding.itempresentationmodel.TypedCursorAdapter;

import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;

import android.database.Cursor;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@RunWith(RobolectricTestRunner.class)
public class TypedCursorAdapterTest
{
	private List<Bean> beans;
	private TypedCursorAdapter<Bean> typedCursor;
	@Before
	public void setUp()
	{
		Robolectric.bindShadowClass(ShadowAbstractCursor.class);
		Robolectric.bindShadowClass(ShadowCursorWrapper.class);
		
		beans = Lists.newArrayList();
		beans.add(new Bean("bean1"));
		beans.add(new Bean("bean2"));
		Cursor cursor = new BeanCursor<Bean>(beans, Bean.class);

		typedCursor = new TypedCursorAdapter<Bean>(cursor, new BeanRowMapper());
	}
	@Test
	public void whenGetObjectAtPosition_thenReturnExpectedInstance()
	{
		Bean actualBean2 = typedCursor.getObjectAtPosition(1);
		
		Bean expectedBean2 = beans.get(1);
		Assert.assertEquals(expectedBean2, actualBean2);
	}
	@Test
	public void givenPosition_whenGetObjectAtPositionWithDifferentIndex_thenPositionUnchanged()
	{
		Assert.assertTrue(typedCursor.moveToFirst());
		Assert.assertEquals(0, typedCursor.getPosition());
		
		typedCursor.getObjectAtPosition(1);
		
		Assert.assertEquals(0, typedCursor.getPosition());
	}
	public static class BeanRowMapper implements RowMapper<Bean>
	{
		@Override
		public Bean mapRow(Cursor cursor)
		{
			int columnIndex = cursor.getColumnIndex(Bean.NAME);
			String name = cursor.getString(columnIndex);
			return new Bean(name);
		}
	}
	public static class Bean
	{
		public static final String NAME = "name";
		
		private String name;
		public Bean(String name)
		{
			this.name = name;
		}
		public String getName()
		{
			return name;
		}
		@Override
		public boolean equals(Object other)
		{
			if (this==other) return true;
			if (!(other instanceof Bean)) return false;
			
			final Bean that = (Bean)other;
			return new EqualsBuilder()
					.append(name, that.name)
					.isEquals();
		}
		@Override
		public int hashCode()
		{
			return new HashCodeBuilder()
				.append(name)
				.toHashCode();
		}
	}
}
