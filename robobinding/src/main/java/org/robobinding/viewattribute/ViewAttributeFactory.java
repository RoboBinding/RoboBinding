package org.robobinding.viewattribute;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public interface ViewAttributeFactory<T extends ViewAttribute> {

    T create();

}
