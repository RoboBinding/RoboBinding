package org.robobinding.doctaglet;

import org.junit.Test;

import com.sun.tools.javadoc.Main;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class DocletRunner {
    @Test
    public void run() {
	Main.execute(new String[] {
		  "-tagletpath",
		  "src/main/java",
		  "-taglet",
		  "org.robobinding.doctaglet.PropTaglet",
		  "-taglet",
		  "org.robobinding.doctaglet.EventTaglet",
		  "-sourcepath",
		  "src/test/java",
		  "-d",
		  "target/apidocs",
		  "org.robobinding.doctaglet",
		  });
    }
}
