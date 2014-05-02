package org.robobinding;

import java.util.Collection;

import org.robobinding.attribute.MissingRequiredAttributesException;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public interface ViewResolutionErrors {
    View getView();

    int numErrors();

    void assertNoErrors();

    boolean hasErrors();

    Collection<AttributeResolutionException> getAttributeErrors();

    Collection<MissingRequiredAttributesException> getMissingRequiredAttributeErrors();

    Collection<Exception> getErrors();
}
