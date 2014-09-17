package org.robobinding;

/**
 * Inflate and bind a menu resource to a presentation model. It is used for
 * Options Menu(ActionBar), Context Menu(Contextual Action Mode) and so on.
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public interface MenuBinder {
	void inflateAndBind(int menuRes, Object presentationModel);
}
