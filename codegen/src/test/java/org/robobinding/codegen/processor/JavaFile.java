package org.robobinding.codegen.processor;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;

import org.apache.commons.lang3.StringUtils;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class JavaFile {
	private final File file;
	public JavaFile(Class<?> type) {
		file = new File(MessageFormat.format(
				"src/test/java/{0}.java", StringUtils.replace(type.getName(), ".", "/")));
	}
	
	public URL toURL() {
		try {
			return file.toURI().toURL();
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}
}
