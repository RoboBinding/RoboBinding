package org.robobinding.doctaglet;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import com.sun.javadoc.Tag;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class PropParserTest {
    @Test
    public void whenParseValidTags_thenSuccessful() {
	PropParser propParser = new PropParser();
	Tag[] validTags = new Tag[] { tag("prop1; Type1, Type2, Type3; one-way, two-way"), tag("prop2; Type1; two-way"), tag("prop2; Type1; two-way; one-way; one-shot") };
	for(Tag tag : validTags) {
	    propParser.parse(tag);
	}
    }

    @Test
    public void whenParseInvalidTags_thenFailed() {
	PropParser propParser = new PropParser();

	Tag[] invalidTags = new Tag[] { tag("prop1; Type1, Type2, Type3"), tag("prop2; ; two-way; something else") };
	for(Tag invalidTag : invalidTags) {
	    try {
		propParser.parse(invalidTag);
		fail("expected invalid tag exception");
	    }catch(RuntimeException e){
	    }
	}
    }

    private Tag tag(String description) {
	Tag mockTag = mock(Tag.class);
	when(mockTag.text()).thenReturn(description);
	return mockTag;
    }
}
