package org.robobinding.codegen.presentationmodel;

import org.robobinding.presentationmodel.AbstractItemPresentationModelObject;

import com.helger.jcodemodel.AbstractJClass;
import com.helger.jcodemodel.AbstractJType;
import com.helger.jcodemodel.JBlock;
import com.helger.jcodemodel.JExpr;
import com.helger.jcodemodel.JFieldVar;
import com.helger.jcodemodel.JInvocation;
import com.helger.jcodemodel.JMethod;
import com.helger.jcodemodel.JMod;
import com.helger.jcodemodel.JVar;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class ItemPresentationModelObjectClassGen extends AbstractPresentationModelObjectClassGen {
	private AbstractJClass presentationModelClass;
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
		AbstractJType itemPresentationModelClass = codeModel.ref(presentationModelInfo.getPresentationModelTypeName());
		JVar itemPresentationModelParam = constructor.param(itemPresentationModelClass, "itemPresentationModel");
		
		JBlock block = constructor.body();
		
		JInvocation superInvocation = JExpr.invoke("super").arg(itemPresentationModelParam);
		block.add(superInvocation);
		
		block.assign(presentationModelField, itemPresentationModelParam);
	}
}
