/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.robobinding.internal.java_beans;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import com.google.common.base.Objects;


public class PropertyDescriptor extends FeatureDescriptor {
    private Method getter;

    private Method setter;

    private Class<?> propertyEditorClass;

    boolean constrained;

    boolean bound;

    public PropertyDescriptor(String propertyName, Class<?> beanClass,
            String getterName, String setterName) throws IntrospectionException {
        super();
        if (beanClass == null) {
            throw new IntrospectionException(Messages.getString("beans.03")); //$NON-NLS-1$
        }
        if (propertyName == null || propertyName.length() == 0) {
            throw new IntrospectionException(Messages.getString("beans.04")); //$NON-NLS-1$
        }
        this.setName(propertyName);
        if (getterName != null) {
            if (getterName.length() == 0) {
                throw new IntrospectionException(
                        "read or write method cannot be empty."); //$NON-NLS-1$    
            }
            try {
                setReadMethod(beanClass, getterName);
            } catch (IntrospectionException e) {
                setReadMethod(beanClass, createDefaultMethodName(propertyName,
                        "get")); //$NON-NLS-1$
            }
        }
        if (setterName != null) {
            if (setterName.length() == 0) {
                throw new IntrospectionException(
                        "read or write method cannot be empty."); //$NON-NLS-1$    
            }
            setWriteMethod(beanClass, setterName);
        }
    }

    public PropertyDescriptor(String propertyName, Method getter, Method setter)
            throws IntrospectionException {
        super();
        if (propertyName == null || propertyName.length() == 0) {
            throw new IntrospectionException(Messages.getString("beans.04")); //$NON-NLS-1$
        }
        this.setName(propertyName);
        setReadMethod(getter);
        setWriteMethod(setter);
    }

    public PropertyDescriptor(String propertyName, Class<?> beanClass)
            throws IntrospectionException {
        if (beanClass == null) {
            throw new IntrospectionException(Messages.getString("beans.03")); //$NON-NLS-1$
        }
        if (propertyName == null || propertyName.length() == 0) {
            throw new IntrospectionException(Messages.getString("beans.04")); //$NON-NLS-1$
        }
        this.setName(propertyName);
        try {
            setReadMethod(beanClass,
                    createDefaultMethodName(propertyName, "is")); //$NON-NLS-1$
        } catch (Exception e) {
            setReadMethod(beanClass, createDefaultMethodName(propertyName,
                    "get")); //$NON-NLS-1$
        }

        setWriteMethod(beanClass, createDefaultMethodName(propertyName, "set")); //$NON-NLS-1$
    }

    public void setWriteMethod(Method setter) throws IntrospectionException {
        if (setter != null) {
            int modifiers = setter.getModifiers();
            if (!Modifier.isPublic(modifiers)) {
                throw new IntrospectionException(Messages.getString("beans.05")); //$NON-NLS-1$
            }
            Class<?>[] parameterTypes = setter.getParameterTypes();
            if (parameterTypes.length != 1) {
                throw new IntrospectionException(Messages.getString("beans.06")); //$NON-NLS-1$
            }
            Class<?> parameterType = parameterTypes[0];
            Class<?> propertyType = getPropertyType();
            if (propertyType != null && !propertyType.equals(parameterType)) {
                throw new IntrospectionException(Messages.getString("beans.07")); //$NON-NLS-1$
            }
        }
        this.setter = setter;
    }

    public void setReadMethod(Method getter) throws IntrospectionException {
        if (getter != null) {
            int modifiers = getter.getModifiers();
            if (!Modifier.isPublic(modifiers)) {
                throw new IntrospectionException(Messages.getString("beans.0A")); //$NON-NLS-1$
            }
            Class<?>[] parameterTypes = getter.getParameterTypes();
            if (parameterTypes.length != 0) {
                throw new IntrospectionException(Messages.getString("beans.08")); //$NON-NLS-1$
            }
            Class<?> returnType = getter.getReturnType();
            if (returnType.equals(Void.TYPE)) {
                throw new IntrospectionException(Messages.getString("beans.33")); //$NON-NLS-1$
            }
            Class<?> propertyType = getPropertyType();
            if ((propertyType != null) && !returnType.equals(propertyType)) {
                throw new IntrospectionException(Messages.getString("beans.09")); //$NON-NLS-1$
            }
        }
        this.getter = getter;
    }

    public Method getWriteMethod() {
        return setter;
    }

    public Method getReadMethod() {
        return getter;
    }
    
    @Override
	public boolean equals(Object other)
	{
		if (this==other) return true;
		if (!(other instanceof PropertyDescriptor)) return false;
		
		final PropertyDescriptor that = (PropertyDescriptor)other;
		return Objects.equal(getter, that.getter)
				&& Objects.equal(setter, that.setter)
				&& Objects.equal(getPropertyType(), that.getPropertyType())
				&& Objects.equal(getPropertyEditorClass(), that.getPropertyEditorClass())
				&& Objects.equal(isBound(), that.isBound())
				&& Objects.equal(isConstrained(), that.isConstrained());
	}

    @Override
    public int hashCode() {
        return Objects.hashCode(getter, setter, getPropertyType(), getPropertyEditorClass(), isBound(), isConstrained());
    }

    public void setPropertyEditorClass(Class<?> propertyEditorClass) {
        this.propertyEditorClass = propertyEditorClass;
    }

    public Class<?> getPropertyType() {
        Class<?> result = null;
        if (getter != null) {
            result = getter.getReturnType();
        } else if (setter != null) {
            Class<?>[] parameterTypes = setter.getParameterTypes();
            result = parameterTypes[0];
        }
        return result;
    }

    public Class<?> getPropertyEditorClass() {
        return propertyEditorClass;
    }

    public void setConstrained(boolean constrained) {
        this.constrained = constrained;
    }

    public void setBound(boolean bound) {
        this.bound = bound;
    }

    public boolean isConstrained() {
        return constrained;
    }

    public boolean isBound() {
        return bound;
    }

    String createDefaultMethodName(String propertyName, String prefix) {
        String result = null;
        if (propertyName != null) {
            String bos = BeansUtils.toASCIIUpperCase(propertyName.substring(0, 1));
            String eos = propertyName.substring(1, propertyName.length());
            result = prefix + bos + eos;
        }
        return result;
    }

    void setReadMethod(Class<?> beanClass, String getterName)
            throws IntrospectionException {
        try {
            Method readMethod = beanClass.getMethod(getterName, new Class[] {});
            setReadMethod(readMethod);
        } catch (Exception e) {
            throw new IntrospectionException(e.getLocalizedMessage());
        }
    }

    void setWriteMethod(Class<?> beanClass, String setterName)
            throws IntrospectionException {
        Method writeMethod = null;
        try {
            if (getter != null) {
                writeMethod = beanClass.getMethod(setterName,
                        new Class[] { getter.getReturnType() });
            } else {
                Class<?> clazz = beanClass;
                Method[] methods = null;
                while (clazz != null && writeMethod == null) {
                    methods = clazz.getDeclaredMethods();
                    for (Method method : methods) {
                        if (setterName.equals(method.getName())) {
                            if (method.getParameterTypes().length == 1) {
                                writeMethod = method;
                                break;
                            }
                        }
                    }
                    clazz = clazz.getSuperclass();
                }
            }
        } catch (Exception e) {
            throw new IntrospectionException(e.getLocalizedMessage());
        }
        if (writeMethod == null) {
            throw new IntrospectionException(Messages.getString(
                    "beans.64", setterName)); //$NON-NLS-1$
        }
        setWriteMethod(writeMethod);
    }
}
