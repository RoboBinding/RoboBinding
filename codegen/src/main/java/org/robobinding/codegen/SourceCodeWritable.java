package org.robobinding.codegen;

import java.io.IOException;

import com.helger.jcodemodel.AbstractCodeWriter;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public interface SourceCodeWritable {
	void writeTo(AbstractCodeWriter output) throws IOException;
}
