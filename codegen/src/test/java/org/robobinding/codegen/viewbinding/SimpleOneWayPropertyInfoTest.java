package org.robobinding.codegen.viewbinding;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class SimpleOneWayPropertyInfoTest {
	@Test
	public void shouldGetCorrectSetter() {
		SimpleOneWayPropertyInfo info = new SimpleOneWayPropertyInfo(null, "prop1");
		assertThat(info.getPropertySetter(), equalTo("setProp1"));
	}
	
	@Test
	public void givenPrimitivePropertyType_thenReturnWrappedType() {
		SimpleOneWayPropertyInfo info = new SimpleOneWayPropertyInfo(int.class, null);
		assertThat(info.getPropertyType().getName(), equalTo("java.lang.Integer"));
	}
	
	@Test
	public void givenObjectPropertyType_thenReturnSameType() {
		SimpleOneWayPropertyInfo info = new SimpleOneWayPropertyInfo(Object.class, null);
		assertThat(info.getPropertyType().getName(), equalTo("java.lang.Object"));
	}
	
	@Test
	public void shouldGetCorrectAttributeTypeName() {
		SimpleOneWayPropertyInfo info = new SimpleOneWayPropertyInfo(null, "prop1");
		assertThat(info.getAttributeTypeName(), equalTo("Prop1Attribute"));
	}
}
