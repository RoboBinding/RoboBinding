package org.robobinding.codegen.presentationmodel;

import org.robobinding.presentationmodel.AbstractPresentationModelObject;

import com.helger.jcodemodel.AbstractJClass;
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
public class PresentationModelObjectClassGen extends AbstractPresentationModelObjectClassGen {
	private AbstractJClass presentationModelClass;
	public PresentationModelObjectClassGen(PresentationModelInfo presentationModelInfo) {
		super(presentationModelInfo);
		
		presentationModelClass = codeModel.ref(presentationModelInfo.getPresentationModelTypeName());
	}
	/**
	 * Note: not private in order to improve performance.
	 * 
	 * final PresentationModelType presentationModel;
	 */
	@Override
	public void defineFields() {
		JFieldVar var = definedClass.field(JMod.FINAL, presentationModelClass, "presentationModel");
		presentationModelField = JExpr.refthis(var.name());
		presentationModelFieldWithoutThis = JExpr.ref(var.name());
	}
	
	/**
	 * 	public PresentationModelType_PM(PresentationModelType presentationModel) {
	 *		super(presentationModel);
	 *		this.presentationModel = presentationModel;
	 *	}
	 *
	 */
	@Override
	public void defineConstructor() {
		/*
		 public class PresentationModelType_PM extends AbstractPresentationModelObject {
		 */
		definedClass._extends(AbstractPresentationModelObject.class);
		
		JMethod constructor = definedClass.constructor(JMod.PUBLIC);
		JVar presentationModelParam = constructor.param(presentationModelClass, "presentationModel");
		
		JBlock block = constructor.body();
		
		JInvocation superInvocation = JExpr.invoke("super").arg(presentationModelParam);
		block.add(superInvocation);
		
		block.assign(presentationModelField, presentationModelParam);
	}

}
