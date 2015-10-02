package org.robobinding.robolectric;

import static org.robobinding.robolectric.TestUtil.resourceFile;

import org.junit.runners.model.InitializationError;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.manifest.AndroidManifest;

/**
 * @since 1.0
 * @version
 * @author Cheng Wei
 * 
 */
public class DefaultTestRunner extends RobolectricTestRunner {
	public DefaultTestRunner(Class<?> testClass) throws InitializationError {
		super(testClass);
	}

	@Override
	protected AndroidManifest getAppManifest(Config config) {
		return new AndroidManifest(resourceFile("AndroidManifest.xml"), resourceFile("res"), resourceFile("assets"));
	}
}
