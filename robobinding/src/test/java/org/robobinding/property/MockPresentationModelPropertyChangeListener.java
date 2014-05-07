package org.robobinding.property;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class MockPresentationModelPropertyChangeListener implements PresentationModelPropertyChangeListener {
    public boolean propertyChangedFired = false;

    @Override
    public void propertyChanged() {
	propertyChangedFired = true;
    }
}