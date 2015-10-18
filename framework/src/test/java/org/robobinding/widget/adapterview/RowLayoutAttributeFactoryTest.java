package org.robobinding.widget.adapterview;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.robobinding.attribute.Attributes.aStaticResourceAttribute;
import static org.robobinding.attribute.Attributes.aStaticResourcesAttribute;
import static org.robobinding.attribute.Attributes.aValueModelAttribute;

import org.junit.Before;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.robobinding.attribute.AbstractPropertyAttribute;
import org.robobinding.viewattribute.grouped.ChildViewAttribute;
import org.robobinding.viewattribute.grouped.ChildViewAttributeAdapter;
import org.robobinding.widget.adapterview.RowLayoutAttributeFactory.UpdaterProvider;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@RunWith(Theories.class)
public class RowLayoutAttributeFactoryTest {
	private RowLayoutAttributeFactory rowLayoutAttributeFactory;
	
	@Before
	public void setUp() {
		View view = mock(View.class);
		UpdaterProvider updaterProvider = mock(UpdaterProvider.class);
		rowLayoutAttributeFactory = new RowLayoutAttributeFactory(view, updaterProvider);
	}
	
	@DataPoints
	public static RowLayoutAttributeExpectation[] expectations = {
		aPropertyAttribute(aStaticResourceAttribute("@layout/layout1")).itsLayoutAttributeType(StaticLayoutAttribute.class),
		aPropertyAttribute(aValueModelAttribute("{itemLayout}")).itsLayoutAttributeType(ChildViewAttributeAdapter.class),
		aPropertyAttribute(aStaticResourcesAttribute("[@layout/layout1, @layout/layout2]")).itsLayoutAttributeType(StaticLayoutsAttribute.class),
	};
	
	@Theory
	public void shouldCreateExpectedLayoutAttribute(RowLayoutAttributeExpectation expectation) {
		ChildViewAttribute viewAttribute = rowLayoutAttributeFactory.createRowLayoutAttribute(expectation.propertyAttribute);
		
		expectation.assertLayoutAttributeType(viewAttribute);
	}
	
	private static RowLayoutAttributeExpectation aPropertyAttribute(AbstractPropertyAttribute propertyAttribute) {
		return new RowLayoutAttributeExpectation(propertyAttribute);
	}
	
	private static class RowLayoutAttributeExpectation {
		public final AbstractPropertyAttribute propertyAttribute;
		private Class<?> type;
		
		public RowLayoutAttributeExpectation(AbstractPropertyAttribute propertyAttribute) {
			this.propertyAttribute = propertyAttribute;
		}
		
		public RowLayoutAttributeExpectation itsLayoutAttributeType(Class<?> type) {
			this.type = type;
			return this;
		}
		
		public void assertLayoutAttributeType(ChildViewAttribute viewAttribute) {
			assertThat(viewAttribute, instanceOf(type));
		}
	}
}
