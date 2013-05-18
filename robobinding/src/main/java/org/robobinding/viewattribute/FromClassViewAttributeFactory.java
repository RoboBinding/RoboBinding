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

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class FromClassViewAttributeFactory<T extends ViewAttribute> implements ViewAttributeFactory<T> {
    private final Class<T> viewAttributeClass;

    public static <T extends ViewAttribute> ViewAttributeFactory<T> viewAttributeFactoryForClass(Class<T> viewAttributeClass) {
	return new FromClassViewAttributeFactory<T>(viewAttributeClass);
    }

    private FromClassViewAttributeFactory(Class<T> viewAttributeClass) {
	this.viewAttributeClass = viewAttributeClass;
    }

    public T create() {
	try {
	    return viewAttributeClass.newInstance();
	} catch (InstantiationException e) {
	    throw new RuntimeException("Attribute class " + viewAttributeClass.getName() + " could not be instantiated: " + e);
	} catch (IllegalAccessException e) {
	    throw new RuntimeException("Attribute class " + viewAttributeClass.getName() + " is not public");
	}
    }
}
