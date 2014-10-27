package org.robobinding.doctaglet;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.text.StrBuilder;

import com.sun.javadoc.Doc;
import com.sun.javadoc.SeeTag;
import com.sun.javadoc.Tag;
import com.sun.tools.doclets.formats.html.TagletWriterImpl;
import com.sun.tools.doclets.internal.toolkit.taglets.TagletOutput;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class InlineLinkTagTranslator {
    public String translate(String text, Tag containingTag, Doc doc, TagletWriterImpl tagletWriterImpl) {
	StrBuilder sb = new StrBuilder(text);
	List<SeeTag> inlineLinkTags = getInlineLinkTags(containingTag);
	for(SeeTag inlineLinkTag : inlineLinkTags) {
	    String inlineLinkContent = "{@link "+inlineLinkTag.text()+"}";
	    if(sb.contains(inlineLinkContent)) {
        	    TagletOutput tagletOutput = tagletWriterImpl.seeTagOutput(doc, new SeeTag[]{inlineLinkTag});
        	    String seeAlsoText = tagletOutput.toString();
        	    sb.replaceAll(inlineLinkContent, seeAlsoText.split("</span></dt><dd>")[1]);
	    }
	}
	return sb.toString();
    }

    private List<SeeTag> getInlineLinkTags(Tag containingTag) {
	List<SeeTag> linkTags = new ArrayList<SeeTag>();
	Tag[] inlineTags = containingTag.inlineTags();
	for(Tag tag : inlineTags) {
	    if("@link".equals(tag.name())) {
		linkTags.add((SeeTag)tag);
	    }
	}
	return linkTags;
    }
}
