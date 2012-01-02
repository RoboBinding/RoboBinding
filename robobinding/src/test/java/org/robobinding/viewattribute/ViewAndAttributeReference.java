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
package org.robobinding.viewattribute;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import android.app.Activity;
import android.content.Context;

/**
* Modified version of Bob Lee's original code (see http://gafter.blogspot.com/2006/12/super-type-tokens.html)
*
* @since 1.0
* @version $Revision: 1.0 $
* @author Robert Taylor
*/
public abstract class ViewAndAttributeReference<V, A> {

    private final Type viewType;
    private final Type attributeType;
    private volatile Constructor<?> viewConstructor;
    private volatile Constructor<?> attributeConstructor;

    protected ViewAndAttributeReference() {
        Type superclass = getClass().getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        this.viewType = ((ParameterizedType) superclass).getActualTypeArguments()[0];
        this.attributeType = ((ParameterizedType) superclass).getActualTypeArguments()[1];
    }

    @SuppressWarnings("unchecked")
    public V newViewInstance()
            throws NoSuchMethodException, IllegalAccessException,
                   InvocationTargetException, InstantiationException {
    	if (viewConstructor == null) {
            Class<?> rawType = viewType instanceof Class<?>
                ? (Class<?>) viewType
                : (Class<?>) ((ParameterizedType) viewType).getRawType();
            viewConstructor = rawType.getConstructor(Context.class);
        }
		return (V) viewConstructor.newInstance(new Activity());
    }

    @SuppressWarnings("unchecked")
    public A newAttributeInstance()
            throws NoSuchMethodException, IllegalAccessException,
                   InvocationTargetException, InstantiationException {
    	if (attributeConstructor == null) {
            Class<?> rawType = attributeType instanceof Class<?>
                ? (Class<?>) attributeType
                : (Class<?>) ((ParameterizedType) attributeType).getRawType();
                attributeConstructor = rawType.getConstructor();
        }
        return (A) attributeConstructor.newInstance();
    }
    
}
