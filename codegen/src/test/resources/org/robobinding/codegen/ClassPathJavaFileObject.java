package org.robobinding.codegen;

import java.net.URI;

import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class ClassPathJavaFileObject extends SimpleJavaFileObject {
	public ClassPathJavaFileObject(String fullClassName) {
		super(URI.create(fullClassName + ".java"), JavaFileObject.Kind.SOURCE);
		// TODO Auto-generated constructor stub
	}
}
