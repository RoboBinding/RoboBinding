package org.robobinding.codegen;

import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class ClassMockery extends Mockery {
	public ClassMockery() {
		setImposteriser(ClassImposteriser.INSTANCE);
		setNamingScheme(NoDuplicateNamingScheme.INSTANCE);
	}
}
