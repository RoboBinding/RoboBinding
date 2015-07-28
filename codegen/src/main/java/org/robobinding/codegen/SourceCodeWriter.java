package org.robobinding.codegen;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

import javax.annotation.processing.Filer;
import javax.tools.JavaFileObject;

import com.helger.jcodemodel.AbstractCodeWriter;
import com.helger.jcodemodel.JPackage;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class SourceCodeWriter extends AbstractCodeWriter {

	private final Filer filer;

	public SourceCodeWriter(Filer filer) {
		super(Charset.forName("UTF-8"));
		this.filer = filer;
	}

	@Override
	public OutputStream openBinary(JPackage pkg, String fileName) throws IOException {
		String qualifiedClassName = toQualifiedClassName(pkg, fileName);

		JavaFileObject sourceFile = filer.createSourceFile(qualifiedClassName);

		return sourceFile.openOutputStream();
	}

	private String toQualifiedClassName(JPackage pkg, String fileName) {
		int suffixPosition = fileName.lastIndexOf('.');
		String className = fileName.substring(0, suffixPosition);
		String qualifiedClassName = pkg.name() + "." + className;
		return qualifiedClassName;
	}

	@Override
	public void close() throws IOException {
	}
}