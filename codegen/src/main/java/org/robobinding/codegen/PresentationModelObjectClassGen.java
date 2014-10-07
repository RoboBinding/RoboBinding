package org.robobinding.codegen;

import org.robobinding.presentationmodel.AbstractPresentationModelObject;
import org.robobinding.presentationmodel.HasPresentationModelChangeSupport;
import org.robobinding.presentationmodel.PresentationModelChangeSupport;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JInvocation;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JVar;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class PresentationModelObjectClassGen extends AbstractPresentationModelObjectClassGen {
	private JClass presentationModelClass;
	public PresentationModelObjectClassGen(PresentationModelInfo presentationModelInfo) {
		super(presentationModelInfo);
		
		presentationModelClass = codeModel.ref(presentationModelInfo.getPresentationModelTypeName());
	}
	/**
	 *	private final PresentationModelType presentationModel;
	 */
	@Override
	public void defineFields() {
		JFieldVar var = definedClass.field(JMod.PRIVATE + JMod.FINAL, presentationModelClass, "presentationModel");
		presentationModelField = JExpr.refthis(var.name());
	}
	
	/**
	 * 1.When PresentationModelType implements {@link HasPresentationModelChangeSupport}
	 * 
	 * 	public PresentationModelType_PM(PresentationModelType presentationModel) {
	 *		super(PresentationModelType.class, presentationModel.getPresentationModelChangeSupport());
	 *		this.presentationModel = presentationModel;
	 *	}
	 *
	 * 2.When PresentationModelType NOT implements {@link HasPresentationModelChangeSupport}
	 * 
	 * 	public PresentationModelType_PM(PresentationModelType presentationModel) {
	 *		super(PresentationModelType.class, new PresentationModelChangeSupport(presentationModel));
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
		
		JInvocation superInvocation = JExpr.invoke("super");
		superInvocation.arg(JExpr.dotclass(presentationModelClass));
		if (presentationModelInfo.extendsHasPresentationModelChangeSupport()) {
			superInvocation.arg(presentationModelParam.invoke("getPresentationModelChangeSupport"));
		} else {
			superInvocation.arg(
					JExpr._new(codeModel.ref(PresentationModelChangeSupport.class)).arg(presentationModelParam));
		}
		block.add(superInvocation);
		
		block.assign(presentationModelField, presentationModelParam);
	}

}
