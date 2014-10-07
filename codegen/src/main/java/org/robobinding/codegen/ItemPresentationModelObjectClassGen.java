package org.robobinding.codegen;

import org.robobinding.presentationmodel.AbstractItemPresentationModelObject;
import org.robobinding.presentationmodel.PresentationModelChangeSupport;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JInvocation;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JType;
import com.sun.codemodel.JVar;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class ItemPresentationModelObjectClassGen extends AbstractPresentationModelObjectClassGen {
	public ItemPresentationModelObjectClassGen(PresentationModelInfo presentationModelInfo) {
		super(presentationModelInfo);
	}
	
	@Override
	public void defineFields() {
		//define a reference to AbstractItemPresentationModelObject.itemPresentationModel.
		presentationModelField = JExpr.refthis("itemPresentationModel");
	}
	/**
	 * 1.
	 *	public StringItemPresentationModel_IPM(StringItemPresentationModel itemPresentationModel) {
	 *		super(itemPresentationModel, new PresentationModelChangeSupport(itemPresentationModel));
	 *	}
	 * 
	 * 2.
	 *	public StringItemPresentationModel_IPM(StringItemPresentationModel itemPresentationModel) {
	 *		super(itemPresentationModel, itemPresentationModel.getPresentationModelChangeSupport());
	 *	}
	 */
	@Override
	public void defineConstructor() {
		/*
		public class StringItemPresentationModel_IPM extends AbstractItemPresentationModelObject {
		 */
		definedClass._extends(AbstractItemPresentationModelObject.class);
		
		JMethod constructor = definedClass.constructor(JMod.PUBLIC);
		JType itemPresentationModelClass = codeModel.ref(presentationModelInfo.getPresentationModelTypeName());
		JVar itemPresentationModelParam = constructor.param(itemPresentationModelClass, "itemPresentationModel");
		
		JBlock block = constructor.body();
		
		JInvocation superInvocation = JExpr.invoke("super");
		superInvocation.arg(itemPresentationModelParam);
		if (presentationModelInfo.extendsHasPresentationModelChangeSupport()) {
			superInvocation.arg(itemPresentationModelParam.invoke("getPresentationModelChangeSupport"));
		} else {
			superInvocation.arg(
					JExpr._new(codeModel.ref(PresentationModelChangeSupport.class)).arg(itemPresentationModelParam));
		}
		block.add(superInvocation);
	}
}
