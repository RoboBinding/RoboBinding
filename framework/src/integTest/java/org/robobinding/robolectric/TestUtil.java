package org.robobinding.robolectric;

import java.io.File;

import org.robobinding.R;
import org.robolectric.res.Fs;
import org.robolectric.res.FsFile;
import org.robolectric.util.Util;

/**
 * @since 1.0
 * @version
 * @author Cheng Wei
 * 
 */
public class TestUtil {
	public static final String SYSTEM_PACKAGE = android.R.class.getPackage().getName();
	public static final String TEST_PACKAGE = R.class.getPackage().getName();

	public static FsFile resourceFile(String... pathParts) {
		File testDir = Util.file("src", "test", "resources");
		FsFile resourcesBaseDir = Fs.newFile(testDir);
		return resourcesBaseDir.join(pathParts);
	}
}
