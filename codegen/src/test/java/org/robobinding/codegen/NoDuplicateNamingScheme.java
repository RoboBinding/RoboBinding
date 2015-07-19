package org.robobinding.codegen;

import org.jmock.api.MockObjectNamingScheme;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class NoDuplicateNamingScheme implements MockObjectNamingScheme {
	public static final NoDuplicateNamingScheme INSTANCE = new NoDuplicateNamingScheme();
    
	private long counter;
	
	public NoDuplicateNamingScheme() {
		counter = 0;
	}
	
    public String defaultNameFor(Class<?> typeToMock) {
    	counter++;
        return "mock" + typeToMock.getSimpleName() + counter;
    }

}
