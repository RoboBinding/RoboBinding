package org.robobinding.viewattribute.grouped;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.robobinding.attribute.AbstractAttribute;
import org.robobinding.attribute.ResolvedGroupAttributes;
import org.robobinding.viewattribute.event.EventViewAttribute;
import org.robobinding.viewattribute.event.EventViewAttributeFactory;
import org.robobinding.viewattribute.property.OneWayMultiTypePropertyViewAttribute;
import org.robobinding.viewattribute.property.OneWayMultiTypePropertyViewAttributeFactory;
import org.robobinding.viewattribute.property.OneWayPropertyViewAttribute;
import org.robobinding.viewattribute.property.OneWayPropertyViewAttributeFactory;
import org.robobinding.widgetaddon.ViewAddOn;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@RunWith(MockitoJUnitRunner.class)
public class ChildViewAttributesBuilderImplTest {
	private static final int NUM_KINDS_OF_CHILD_VIEW_ATTRIBUTES = 8;

	private ChildViewAttributesBuilderImpl<View> childViewAttributesBuilder;

	@Mock
	private ChildViewAttribute childViewAttribute;
	@Mock
	private OneWayPropertyViewAttribute<View, ?> propertyViewAttribute;
	@Mock
	private ChildViewAttributeFactory childViewAttributeFactory;
	@Mock
	private OneWayPropertyViewAttributeFactory<View> propertyViewAttributeFactory;
	@Mock
	private OneWayMultiTypePropertyViewAttribute<View> multiTypePropertyViewAttribute;
	@Mock
	private OneWayMultiTypePropertyViewAttributeFactory<View> multiTypePropertyViewAttributeFactory;
	@Mock
	private EventViewAttribute<View, ViewAddOn> eventViewAttribute;
	@Mock
	private EventViewAttributeFactory<View> eventViewAttributeFactory;

	@Before
	public void setUp() {
		ResolvedGroupAttributes resolvedGroupAttributes = mock(ResolvedGroupAttributes.class);
		ViewAttributeBinderFactory viewAttributeBinderFactory = mock(ViewAttributeBinderFactory.class);
		childViewAttributesBuilder = new ChildViewAttributesBuilderImpl<View>(resolvedGroupAttributes, viewAttributeBinderFactory);
	}

	@Test
	public void whenAddAChildViewAttribute_thenTheAttributeIsAdded() {
		String attributeName = "attributeName";

		addAChildViewAttribute(attributeName);

		assertThat(numOfChildViewAttributes(), is(1));
		assertTrue(hasChildViewAttribute(attributeName));
	}

	private void addAChildViewAttribute(String attributeName) {
		int selection = new Random().nextInt(NUM_KINDS_OF_CHILD_VIEW_ATTRIBUTES);
		switch (selection) {
		case 0:
			childViewAttributesBuilder.add(attributeName, childViewAttribute);
			break;
		case 1:
			childViewAttributesBuilder.add(attributeName, childViewAttributeFactory);
			break;
		case 2:
			childViewAttributesBuilder.add(attributeName, propertyViewAttribute);
			break;
		case 3:
			childViewAttributesBuilder.add(attributeName, propertyViewAttributeFactory);
			break;
		case 4:
			childViewAttributesBuilder.add(attributeName, multiTypePropertyViewAttribute);
			break;
		case 5:
			childViewAttributesBuilder.add(attributeName, eventViewAttribute);
			break;
		case 6:
			childViewAttributesBuilder.add(attributeName, eventViewAttributeFactory);
			break;
		default:
			childViewAttributesBuilder.add(attributeName, multiTypePropertyViewAttributeFactory);
			break;
		}
	}

	private int numOfChildViewAttributes() {
		return childViewAttributesBuilder.childViewAttributeMap.size();
	}

	private boolean hasChildViewAttribute(String attributeName) {
		return childViewAttributesBuilder.childViewAttributeMap.containsKey(attributeName);
	}

	@Test
	public void whenAddDifferentChildViewAttributes_thenTheAllAttributesAreAdded() {
		String attribute1 = "attribute1";
		String attribute2 = "attribute2";
		String attribute3 = "attribute3";
		String attribute4 = "attribute4";
		String attribute5 = "attribute5";
		String attribute6 = "attribute6";
		String attribute7 = "attribute7";
		String attribute8 = "attribute8";

		childViewAttributesBuilder.add(attribute1, childViewAttribute);
		childViewAttributesBuilder.add(attribute2, childViewAttributeFactory);
		childViewAttributesBuilder.add(attribute3, propertyViewAttribute);
		childViewAttributesBuilder.add(attribute4, propertyViewAttributeFactory);
		childViewAttributesBuilder.add(attribute5, multiTypePropertyViewAttribute);
		childViewAttributesBuilder.add(attribute6, multiTypePropertyViewAttributeFactory);
		childViewAttributesBuilder.add(attribute7, eventViewAttribute);
		childViewAttributesBuilder.add(attribute8, eventViewAttributeFactory);

		assertThat(numOfChildViewAttributes(), is(NUM_KINDS_OF_CHILD_VIEW_ATTRIBUTES));
		assertTrue(hasChildViewAttribute(attribute1));
		assertTrue(hasChildViewAttribute(attribute2));
		assertTrue(hasChildViewAttribute(attribute3));
		assertTrue(hasChildViewAttribute(attribute4));
		assertTrue(hasChildViewAttribute(attribute5));
		assertTrue(hasChildViewAttribute(attribute6));
		assertTrue(hasChildViewAttribute(attribute7));
		assertTrue(hasChildViewAttribute(attribute8));
	}

	@Test
	public void whenAddChildViewAttributeWithAttribute_thenTheAttributeIsSet() {
		@SuppressWarnings("unchecked")
		ChildViewAttributeWithAttribute<AbstractAttribute> childViewAttribute = mock(ChildViewAttributeWithAttribute.class);

		childViewAttributesBuilder.add("attributeName", childViewAttribute);

		verify(childViewAttribute).setAttribute(any(AbstractAttribute.class));
	}

}