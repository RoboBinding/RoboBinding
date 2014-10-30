package org.robobinding.codegen.viewbinding;

import java.io.IOException;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic.Kind;

import org.robobinding.annotation.ViewBinding;
import org.robobinding.codegen.SourceCodeWriter;
import org.robobinding.codegen.typewrapper.AbstractTypeElementWrapper;
import org.robobinding.codegen.typewrapper.DeclaredTypeElementWrapper;
import org.robobinding.codegen.typewrapper.ProcessingContext;
import org.robobinding.customviewbinding.CustomViewBinding;

import com.google.common.collect.Sets;
import com.sun.codemodel.CodeWriter;
import com.sun.codemodel.JClassAlreadyExistsException;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class ViewBindingProcessor extends AbstractProcessor {
	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		ProcessingContext context = new ProcessingContext(processingEnv.getTypeUtils(), processingEnv.getElementUtils());
		Set<AbstractTypeElementWrapper> typeElements = findCustomViewBindingTypeElements(roundEnv, context);
		for(AbstractTypeElementWrapper typeElement : typeElements) {
			ViewBindingInfoBuilder builder = new ViewBindingInfoBuilder(typeElement, 
					typeElement.typeName() + "$$VB");
			ViewBindingInfo info = builder.build();
			try {
				generateViewBindingObjectSourceFile(info);
			} catch (IOException e) {
				throw new RuntimeException(e);
			} catch (JClassAlreadyExistsException e) {
				throw new RuntimeException(e);
			} catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
			}
		}
		return true;
	}

	private Set<AbstractTypeElementWrapper> findCustomViewBindingTypeElements(RoundEnvironment env, ProcessingContext context) {
	    Set<AbstractTypeElementWrapper> typeElements = Sets.newHashSet();
	    for (Element element : env.getElementsAnnotatedWith(ViewBinding.class)) {
	    	AbstractTypeElementWrapper typeElement = new DeclaredTypeElementWrapper(context, processingEnv.getTypeUtils(), (TypeElement)element);

	    	if(typeElement.isAssignableTo(CustomViewBinding.class) && typeElement.isConcreteClass()) {
	            typeElements.add(typeElement);
	    	}
	    }
	    return typeElements;
	  }
	
	private void generateViewBindingObjectSourceFile(ViewBindingInfo info) throws IOException, JClassAlreadyExistsException, ClassNotFoundException {
		try {
			ViewBindingObjectClassGen gen = new ViewBindingObjectClassGen(info);
			run(gen);
			gen.writeTo(createOutput());
			printMessage("ViewBinding '"+info.viewBindingObjectTypeName() + "' generated.");
		} catch (java.lang.NoClassDefFoundError e) {
			throw new RuntimeException(
					"an error occured when generating source code for '"+info.viewBindingObjectTypeName()+"'", e);
		}
	}

	private void run(ViewBindingObjectClassGen gen) throws JClassAlreadyExistsException {
		gen.defineFields();
		gen.defineConstructor();
		gen.defineSimpleOneWayPropertyClasses();
		gen.defineMapBindingAttributesMethod();
	}
	
	private void printMessage(String message) {
		processingEnv.getMessager().printMessage(Kind.NOTE, message);
	}

	private CodeWriter createOutput() throws IOException, JClassAlreadyExistsException {
		return new SourceCodeWriter(processingEnv.getFiler());
	}


	@Override
	public Set<String> getSupportedAnnotationTypes() {
		Set<String> supportedAnnotationTypes = Sets.newHashSet();
		supportedAnnotationTypes.add(ViewBinding.class.getName());
		return supportedAnnotationTypes;
	}

	@Override
	public SourceVersion getSupportedSourceVersion() {
		return SourceVersion.latest();
	}

}
