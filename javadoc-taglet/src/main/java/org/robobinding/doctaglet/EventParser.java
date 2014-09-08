package org.robobinding.doctaglet;

import com.sun.javadoc.Tag;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class EventParser {

    public Event parse(Tag tag) {
	String[] parts = tag.text().split(";", 2);

	if ((parts.length != 2) || Strings.containNullOrEmptyElement(parts)) {
	    throw new RuntimeException("Invalid event tag format '" + tag.text() + "', expected format @event [name]; [eventClass]");
	}

	return new Event(parts[0], parts[1]);

    }

}
