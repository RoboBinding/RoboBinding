package org.robobinding.codegen;

import java.io.File;
import java.text.MessageFormat;

import org.apache.commons.lang3.StringUtils;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
@SuppressWarnings("serial")
public class TextFile extends File{
	public TextFile(Class<?> typeAsPath, String textFileName) {
		super(MessageFormat.format("src/test/java/{0}/{1}", 
				StringUtils.replace(typeAsPath.getPackage().getName(), ".", "/"),
				textFileName));
	}
}
