/**
 * Copyright 2012 Cheng Wei, Robert Taylor
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
package org.robobinding.property;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public abstract class AbstractValueModel<T> implements ValueModel<T> {
    private static final String PROPERTY_VALUE = "value";

    protected T value;
    private PresentationModelPropertyChangeSupport propertyChangeSupport;

    public AbstractValueModel(T value) {
	this.value = value;

	propertyChangeSupport = new PresentationModelPropertyChangeSupport(this);
    }

    @Override
    public T getValue() {
	return value;
    }

    @Override
    public void setValue(T newValue) {
	value = newValue;
	fireValueChange();
    }

    protected void fireValueChange() {
	propertyChangeSupport.firePropertyChange(PROPERTY_VALUE);
    }

    @Override
    public final void addPropertyChangeListener(PresentationModelPropertyChangeListener listener) {
	propertyChangeSupport.addPropertyChangeListener(PROPERTY_VALUE, listener);
    }

    @Override
    public final void removePropertyChangeListener(PresentationModelPropertyChangeListener listener) {
	propertyChangeSupport.removePropertyChangeListener(PROPERTY_VALUE, listener);
    }

    @Override
    public String toString() {
	return getClass().getName() + "[" + paramString() + "]";
    }

    protected String paramString() {
	return "value=" + valueString();
    }

    protected String valueString() {
	try {
	    Object value = getValue();
	    return value == null ? "null" : value.toString();
	} catch (Exception e) {
	    return "Can't read";
	}
    }
}