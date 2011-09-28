/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.lang3.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

/**
 * <p> Utility reflection methods focused on constructors, modeled after
 * {@link MethodUtils}. </p>
 *
 * <h3>Known Limitations</h3> <h4>Accessing Public Constructors In A Default
 * Access Superclass</h4> <p>There is an issue when invoking public constructors
 * contained in a default access superclass. Reflection locates these
 * constructors fine and correctly assigns them as public. However, an
 * <code>IllegalAccessException</code> is thrown if the constructors is
 * invoked.</p>
 *
 * <p><code>ConstructorUtils</code> contains a workaround for this situation. It
 * will attempt to call <code>setAccessible</code> on this constructor. If this
 * call succeeds, then the method can be invoked as normal. This call will only
 * succeed when the application has sufficient security privileges. If this call
 * fails then a warning will be logged and the method may fail.</p>
 *
 * @since 2.5
 * @version $Id$
 */
public class ConstructorUtils {

    /**
     * <p>ConstructorUtils instances should NOT be constructed in standard
     * programming. Instead, the class should be used as
     * <code>ConstructorUtils.invokeConstructor(cls, args)</code>.</p>
     *
     * <p>This constructor is public to permit tools that require a JavaBean
     * instance to operate.</p>
     */
    public ConstructorUtils() {
        super();
    }

    //-----------------------------------------------------------------------
    /**
     * <p>Finds a constructor given a class and signature, checking accessibility.</p>
     * 
     * <p>This finds the constructor and ensures that it is accessible.
     * The constructor signature must match the parameter types exactly.</p>
     *
     * @param <T> the constructor type
     * @param cls  the class to find a constructor for, not null
     * @param parameterTypes  the array of parameter types, null treated as empty
     * @return the constructor, null if no matching accessible constructor found
     * @see Class#getConstructor
     * @see #getAccessibleConstructor(java.lang.reflect.Constructor)
     */
    public static <T> Constructor<T> getAccessibleConstructor(Class<T> cls,
            Class<?>... parameterTypes) {
        try {
            return getAccessibleConstructor(cls.getConstructor(parameterTypes));
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    /**
     * <p>Checks if the specified constructor is accessible.</p>
     * 
     * <p>This simply ensures that the constructor is accessible.</p>
     *
     * @param <T> the constructor type
     * @param ctor  the prototype constructor object, not null
     * @return the constructor, null if no matching accessible constructor found
     * @see java.lang.SecurityManager
     */
    public static <T> Constructor<T> getAccessibleConstructor(Constructor<T> ctor) {
        return MemberUtils.isAccessible(ctor)
                && Modifier.isPublic(ctor.getDeclaringClass().getModifiers()) ? ctor : null;
    }

}
