package org.robobinding.codegen;

import com.sun.codemodel.*;
import org.robobinding.presentationmodel.AbstractGroupedItemPresentationModelObject;

/**
 * @since 1.0
 * @author Jihun Lee
 *
 */
public class GroupedItemPresentationModelObjectClassGen extends AbstractPresentationModelObjectClassGen {
	private JClass presentationModelClass;
	public GroupedItemPresentationModelObjectClassGen(PresentationModelInfo presentationModelInfo) {
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
		JFieldVar var = definedClass.field(JMod.FINAL, presentationModelClass, "groupedItemPresentationModel");
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
		definedClass._extends(AbstractGroupedItemPresentationModelObject.class);
		
		JMethod constructor = definedClass.constructor(JMod.PUBLIC);
		JType itemPresentationModelClass = codeModel.ref(presentationModelInfo.getPresentationModelTypeName());
		JVar groupedItemPresentationModelParam = constructor.param(itemPresentationModelClass, "groupedItemPresentationModel");
		
		JBlock block = constructor.body();
		
		JInvocation superInvocation = JExpr.invoke("super").arg(groupedItemPresentationModelParam);
		block.add(superInvocation);
		
		block.assign(presentationModelField, groupedItemPresentationModelParam);
	}
}
