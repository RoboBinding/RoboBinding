package org.robobinding.viewattribute.grouped;

import org.robobinding.attribute.EnumAttribute;
import org.robobinding.attribute.StaticResourceAttribute;
import org.robobinding.attribute.ValueModelAttribute;
import org.robobinding.viewattribute.property.MultiTypePropertyViewAttribute;
import org.robobinding.viewattribute.property.MultiTypePropertyViewAttributeFactory;
import org.robobinding.viewattribute.property.PropertyViewAttribute;
import org.robobinding.viewattribute.property.PropertyViewAttributeFactory;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public interface ChildViewAttributesBuilder<ViewType> {
    void add(String attributeName, ChildViewAttribute childAttribute);
    void add(String attributeName, ChildViewAttributeFactory factory);
    void addDependent(String attributeName,
	    ChildViewAttributeFactory factory);

    void add(String attributeName, PropertyViewAttribute<ViewType, ?> viewAttribute);
    void add(String attributeName,
	    PropertyViewAttributeFactory<ViewType> factory);

    void add(String attributeName, MultiTypePropertyViewAttribute<ViewType> viewAttribute);
    void add(String attributeName,
	    MultiTypePropertyViewAttributeFactory<ViewType> factory);

    void failOnFirstBindingError();

    boolean hasAttribute(String attributeName);
    ValueModelAttribute valueModelAttributeFor(String attributeName);
    StaticResourceAttribute staticResourceAttributeFor(String attributeName);
    <E extends Enum<E>> EnumAttribute<E> enumAttributeFor(String attributeName);
}
