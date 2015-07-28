package org.robobinding.codegen;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.text.StrBuilder;
import org.robobinding.codegen.presentationmodel.TextFile;

import com.helger.jcodemodel.writer.SingleStreamCodeWriter;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class SourceCodeAssert {

	public static void assertOutputSameTextFile(SourceCodeWritable writable, String textFileName) {
		try {
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			writable.writeTo(new SingleStreamCodeWriter(output));
			String actual = output.toString();
			String expected = FileUtils.readFileToString(new TextFile(writable.getClass(), textFileName));
			assertThat(withSpacesRemoved(actual), equalTo(withSpacesRemoved(expected)));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private static String withSpacesRemoved(String text) {
		StrBuilder sb = new StrBuilder(text);
		sb.deleteAll("\r");
		sb.deleteAll("\t");
		sb.deleteAll("\n");
		sb.deleteAll(" ");
		return sb.toString();
	}

}
