package org.robobinding.aspects;

import org.robobinding.itempresentationmodel.ItemPresentationModel;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ItemPresentationModel_AutoCodeGeneration implements ItemPresentationModel<Object> {
    Object bean;
    public static final String PROPERTY = "property";

    public void updateData(int index, Object bean) {
	this.bean = bean;
    }

    public boolean getProperty() {
	return true;
    }

}
