package org.robobinding.doctaglet;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class Strings {
    public static boolean containNullOrEmptyElement(String... strings){
	if((strings == null) || (strings.length==0)) {
	    return false;
	}

	for(String str : strings) {
	    if(isNullOrEmpty(str)) {
		return true;
	    }
	}

	return false;
    }

    public static boolean isNullOrEmpty(String str) {
	if((str == null) || (str.trim().length() == 0)) {
	    return true;
	}

	return false;
    }
}
