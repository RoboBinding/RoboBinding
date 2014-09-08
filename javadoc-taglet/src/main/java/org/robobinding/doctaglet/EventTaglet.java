package org.robobinding.doctaglet;

import java.text.MessageFormat;
import java.util.Map;

import com.sun.javadoc.Doc;
import com.sun.javadoc.Tag;
import com.sun.tools.doclets.formats.html.TagletOutputImpl;
import com.sun.tools.doclets.formats.html.TagletWriterImpl;
import com.sun.tools.doclets.internal.toolkit.taglets.Taglet;
import com.sun.tools.doclets.internal.toolkit.taglets.TagletOutput;
import com.sun.tools.doclets.internal.toolkit.taglets.TagletWriter;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class EventTaglet implements Taglet {
    private static final String NAME = "event";

    private final EventParser parser;
    private final InlineLinkTagTranslator inlineLinkTagTranslator;

    public EventTaglet(EventParser parser, InlineLinkTagTranslator inlineLinkTagTranslator) {
	this.parser = parser;
	this.inlineLinkTagTranslator = inlineLinkTagTranslator;
    }

    public String getName() {
	return NAME;
    }

    public TagletOutput getTagletOutput(Doc doc, TagletWriter tagletWriter) throws IllegalArgumentException {
	Tag[] tags = doc.tags(NAME);
	if (tags.length == 0) {
	    return null;
	}

	TagletWriterImpl tagletWriterImpl = (TagletWriterImpl)tagletWriter;

	StringBuilder sb = new StringBuilder();
	sb.append("<table style=\"border: 2px solid gray\"><caption style=\"text-align:left\"><strong>Supported Events</strong></caption><tr style=\"background-color:#555555; color: #ffffff\"><th>Name</th><th>Event class</th></tr>");
	for (int i=0; i<tags.length; i++) {
	    Tag tag = tags[i];
	    Event event = parser.parse(tag);
	    String translatedClassName = inlineLinkTagTranslator.translate(event.getClassName(), tag, doc, tagletWriterImpl);
	    sb.append(MessageFormat.format("<tr style=\"background-color:{0}\"><td><strong>{1}</strong></td><td>{2}</td></tr>",
		    (i % 2 == 0)?"#eeeeef":"#ffffff", event.getName(), translatedClassName));
	}
	sb.append("</table></br>");
	return new TagletOutputImpl(sb.toString());
    }

    public boolean inPackage() {
	return true;
    }

    public static void register(Map<String, Object> tagletMap) {
        Taglet existing = (Taglet) tagletMap.get(NAME);
        if (existing != null) {
            tagletMap.remove(NAME);
        }
        EventTaglet target = new EventTaglet(new EventParser(), new InlineLinkTagTranslator());
        tagletMap.put(target.getName(), target);
    }

    public boolean inConstructor() {
	return false;
    }

    public boolean inField() {
	return false;
    }

    public boolean inMethod() {
	return false;
    }

    public boolean inOverview() {
	return true;
    }

    public boolean inType() {
	return true;
    }

    public boolean isInlineTag() {
	return false;
    }

    public TagletOutput getTagletOutput(Tag arg0, TagletWriter arg1) throws IllegalArgumentException {
	throw new UnsupportedOperationException();
    }

}
