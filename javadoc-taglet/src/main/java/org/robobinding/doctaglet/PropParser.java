package org.robobinding.doctaglet;

import com.sun.javadoc.Tag;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class PropParser {

    public Prop parse(Tag tag) {
	String[] parts = tag.text().split(";", 3);

	if ((parts.length != 3) || Strings.containNullOrEmptyElement(parts)) {
	    throw new RuntimeException("Invalid prop tag format '" + tag.text()
		    + "', expected format @prop [name]; [supported type description]; [two-way?]");
	}

	return new Prop(parts[0], parts[1], parts[2]);
    }

}
