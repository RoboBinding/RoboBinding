package org.robobinding.codegen;

import org.robobinding.presentationmodel.AbstractItemPresentationModelObject;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JFieldVar;
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
	private JClass presentationModelClass;
	public ItemPresentationModelObjectClassGen(PresentationModelInfo presentationModelInfo) {
		super(presentationModelInfo);
		
		presentationModelClass = codeModel.ref(presentationModelInfo.getPresentationModelTypeName());
	}
	
	/**
	 * Note: not private in order to improve performance.
	 * 
	 * final ItemPresentationModelType itemPresentationModel;
	 */
	@Override
	public void defineFields() {
		JFieldVar var = definedClass.field(JMod.FINAL, presentationModelClass, "itemPresentationModel");
		presentationModelField = JExpr.refthis(var.name());
		presentationModelFieldWithoutThis = JExpr.ref(var.name());
	}
	/**
	 *	public StringItemPresentationModel_IPM(StringItemPresentationModel itemPresentationModel) {
	 *		super(itemPresentationModel);
	 *		this.itemPresentationModel = itemPresentationModel;
	 *	}
	 * 
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
		
		JInvocation superInvocation = JExpr.invoke("super").arg(itemPresentationModelParam);
		block.add(superInvocation);
		
		block.assign(presentationModelField, itemPresentationModelParam);
	}
}
