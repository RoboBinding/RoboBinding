package org.robobinding.attribute;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public abstract class AbstractPropertyAttribute extends AbstractAttribute {
	public AbstractPropertyAttribute(String name) {
		super(name);
	}

	public abstract boolean isTwoWayBinding();

	/*
	public final ValueModelAttribute asValueModelAttribute() {
		if (isValueModel()) {
			return (ValueModelAttribute) this;
		} else {
			throw new RuntimeException("Not a value model attribute");
		}
	}
	
	boolean isValueModel() {
		return ValueModelAttribute.class.isInstance(this);
	}

	public final StaticResourceAttribute asStaticResourceAttribute() {
		if (isStaticResource()) {
			return (StaticResourceAttribute) this;
		} else {
			throw new RuntimeException("Not a static resource attribute value");
		}
	}

	public boolean isStaticResource() {
		return StaticResourceAttribute.class.isInstance(this);
	}
	*/
	
	public abstract <T> T accept(PropertyAttributeVisitor<T> visitor);
}
