package org.robobinding.codegen.viewbinding;

import com.google.common.base.Objects;
import com.helger.jcodemodel.JDefinedClass;
import org.robobinding.codegen.apt.element.MethodElement;
import org.robobinding.codegen.apt.type.WrappedTypeMirror;

/**
 * Created by dhu on 15/8/27.
 */
public class TwoWayPropertyInfo {
    private final String propertyName;
    private final String propertyTypeName;
    public TwoWayPropertyInfo(String propertyName, String propertyTypeName) {
        this.propertyName = propertyName;
        this.propertyTypeName = propertyTypeName;
    }

    public String propertyName() {
        return propertyName;
    }

    public String propertyTypeName() {
        return propertyTypeName;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof TwoWayPropertyInfo))
            return false;

        final TwoWayPropertyInfo that = (TwoWayPropertyInfo) other;
        return Objects.equal(propertyName, that.propertyName)
                && Objects.equal(propertyTypeName, that.propertyTypeName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(propertyName)
                + Objects.hashCode(propertyTypeName);
    }

    @Override
    public String toString() {
        return "{name: " + propertyName + ", type: " + propertyTypeName + "}";
    }
}
