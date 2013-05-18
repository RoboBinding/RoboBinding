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
package org.robobinding.util;

/**
 * Migrated some of methods from {@link org.apache.commons.lang3.SystemUtils}.
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
class SystemUtils {

    // System property constants
    // -----------------------------------------------------------------------
    // These MUST be declared first. Other constants depend on this.

    /**
     * <p>
     * The {@code java.specification.version} System Property. Java Runtime
     * Environment specification version.
     * </p>
     * <p>
     * Defaults to {@code null} if the runtime does not have security access to
     * read this property or the property does not exist.
     * </p>
     * <p>
     * This value is initialized when the class is loaded. If
     * {@link System#setProperty(String,String)} or
     * {@link System#setProperties(java.util.Properties)} is called after this
     * class is loaded, the value will be out of sync with that System property.
     * </p>
     * 
     * @since Java 1.3
     */
    public static final String JAVA_SPECIFICATION_VERSION = getSystemProperty("java.specification.version");
    private static final JavaVersion JAVA_SPECIFICATION_VERSION_AS_ENUM = JavaVersion.get(JAVA_SPECIFICATION_VERSION);

    // Java version checks
    // -----------------------------------------------------------------------
    // These MUST be declared after those above as they depend on the
    // values being set up

    // Operating system checks
    // -----------------------------------------------------------------------
    // These MUST be declared after those above as they depend on the
    // values being set up
    // OS names from http://www.vamphq.com/os.html
    // Selected ones included - please advise dev@commons.apache.org
    // if you want another added or a mistake corrected

    /**
     * <p>
     * Is the Java version at least the requested version.
     * </p>
     * <p>
     * Example input:
     * </p>
     * <ul>
     * <li>{@code 1.2f} to test for Java 1.2</li>
     * <li>{@code 1.31f} to test for Java 1.3.1</li>
     * </ul>
     * 
     * @param requiredVersion
     *            the required version, for example 1.31f
     * @return {@code true} if the actual version is equal or greater than the
     *         required version
     */
    public static boolean isJavaVersionAtLeast(final JavaVersion requiredVersion) {
	return JAVA_SPECIFICATION_VERSION_AS_ENUM.atLeast(requiredVersion);
    }

    // -----------------------------------------------------------------------
    /**
     * <p>
     * Gets a System property, defaulting to {@code null} if the property cannot
     * be read.
     * </p>
     * <p>
     * If a {@code SecurityException} is caught, the return value is
     * {@code null} and a message is written to {@code System.err}.
     * </p>
     * 
     * @param property
     *            the system property name
     * @return the system property value or {@code null} if a security problem
     *         occurs
     */
    private static String getSystemProperty(final String property) {
	try {
	    return System.getProperty(property);
	} catch (final SecurityException ex) {
	    // we are not allowed to look at this property
	    System.err.println("Caught a SecurityException reading the system property '" + property
		    + "'; the SystemUtils property value will default to null.");
	    return null;
	}
    }

    // -----------------------------------------------------------------------
    /**
     * <p>
     * SystemUtils instances should NOT be constructed in standard programming.
     * Instead, the class should be used as {@code SystemUtils.FILE_SEPARATOR}.
     * </p>
     * <p>
     * This constructor is public to permit tools that require a JavaBean
     * instance to operate.
     * </p>
     */
    public SystemUtils() {
	super();
    }

}
