package org.robobinding.codegen;

import java.io.IOException;

import com.sun.codemodel.CodeWriter;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public interface SourceCodeWritable {
	void writeTo(CodeWriter output) throws IOException;
}
