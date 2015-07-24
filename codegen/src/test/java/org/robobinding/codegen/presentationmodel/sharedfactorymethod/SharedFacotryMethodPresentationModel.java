package org.robobinding.codegen.presentationmodel.sharedfactorymethod;

import java.util.List;

import org.robobinding.annotation.ItemPresentationModel;
import org.robobinding.annotation.PresentationModel;

import com.google.common.collect.Lists;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
@PresentationModel
public class SharedFacotryMethodPresentationModel{

    @ItemPresentationModel(value = MyItemPresentationModel.class, factoryMethod = "createItem")
    public List<Item> getItems1(){
        return Lists.newArrayList();
    }

    @ItemPresentationModel(value = MyItemPresentationModel.class, factoryMethod = "createItem")
    public List<Item> getItems2(){
        return Lists.newArrayList();
    }

    public MyItemPresentationModel createItem(){
        return new MyItemPresentationModel();
    }
}