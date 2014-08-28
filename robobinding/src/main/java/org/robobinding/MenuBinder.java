package org.robobinding;




/**
 * It is for inflating and binding a layout to a presentation model in Options Menu(ActionBar), Context Menu(Contextual Action Mode) and so on.
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public interface MenuBinder {
    void inflateAndBind(int menuRes, Object presentationModel);
}
