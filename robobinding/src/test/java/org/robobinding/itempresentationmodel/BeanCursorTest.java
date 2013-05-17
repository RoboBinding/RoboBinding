/**
 * BeanCursorTest.java
 * Nov 10, 2011 Copyright Cheng Wei and Robert Taylor
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 */
package org.robobinding.itempresentationmodel;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.common.collect.Lists;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@RunWith(RobolectricTestRunner.class)
public class BeanCursorTest {
    private List<Bean> beans;
    private BeanCursor<Bean> beanCursor;

    @Before
    public void setUp() {
	Robolectric.bindShadowClass(ShadowAbstractCursor.class);

	beans = Lists.newArrayList();
	beans.add(new Bean("bean1 prop1"));
	beans.add(new Bean("bean2 prop1"));

	beanCursor = new BeanCursor<BeanCursorTest.Bean>(beans, Bean.class);
    }

    @Test
    public void whenGetProperty1WithCorrectStringType_thenReturnExpectedResult() {
	beanCursor.moveToFirst();
	String property1 = beanCursor.getString(property1ColumnIndex());

	Assert.assertEquals(expectedProperty1OfBean1(), property1);
    }

    private String expectedProperty1OfBean1() {
	Bean bean1 = beans.get(0);
	return bean1.getProperty1();
    }

    @Test(expected = ClassCastException.class)
    public void whenGetProperty1WithWrongDoubleType_thenThrowException() {
	beanCursor.moveToFirst();
	beanCursor.getDouble(property1ColumnIndex());
    }

    @Test
    public void givenMoveToSecondPosition_whenGetProperty1_thenReturnExpectedResult() {
	beanCursor.moveToFirst();
	beanCursor.moveToNext();

	String property1 = beanCursor.getString(property1ColumnIndex());

	Assert.assertEquals(expectedProperty1OfBean2(), property1);
    }

    private int property1ColumnIndex() {
	return beanCursor.getColumnIndex(Bean.PROPERTY1);
    }

    private String expectedProperty1OfBean2() {
	Bean bean2 = beans.get(1);
	return bean2.getProperty1();
    }

    @Test
    public void whenGetObjectAtFirstPosition_thenReturnExpectedBean() {
	Bean bean1 = beanCursor.getObjectAtPosition(0);

	Assert.assertEquals(beans.get(0), bean1);
    }

    @Test
    public void whenGetObjectAtSecondPosition_thenReturnExpectedBean() {
	Bean bean2 = beanCursor.getObjectAtPosition(1);

	Assert.assertEquals(beans.get(1), bean2);
    }

    public static class Bean {
	public static final String PROPERTY1 = "property1";
	public static final String PROPERTY2 = "property2";
	private String property1;

	public Bean(String property1) {
	    this.property1 = property1;
	}

	public String getProperty1() {
	    return property1;
	}

	public int getProperty2() {
	    return 0;
	}

	public double nonProperty() {
	    return 0.0;
	}
    }
}
