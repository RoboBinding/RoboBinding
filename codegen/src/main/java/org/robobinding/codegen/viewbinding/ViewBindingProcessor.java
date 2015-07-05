package org.robobinding.codegen.viewbinding;

import java.io.IOException;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.util.Elements;

import org.robobinding.annotation.ViewBinding;
import org.robobinding.codegen.SourceCodeWriter;
import org.robobinding.codegen.typewrapper.DeclaredTypeElementWrapper;
import org.robobinding.codegen.typewrapper.Logger;
import org.robobinding.codegen.typewrapper.ProcessingContext;
import org.robobinding.codegen.typewrapper.TypesWrapper;
import org.robobinding.customviewbinding.CustomViewBinding;
import org.robobinding.customviewbinding.ViewBindingLoader;

import com.google.common.collect.Sets;
import com.sun.codemodel.CodeWriter;
import com.sun.codemodel.JClassAlreadyExistsException;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class ViewBindingProcessor extends AbstractProcessor {
	private static final String VIEW_BINDING_OBJECT_SUFFIX = ViewBindingLoader.CLASS_SUFFIX;
	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		Elements elements = processingEnv.getElementUtils();
		TypesWrapper types = new TypesWrapper(processingEnv.getTypeUtils(), elements);
		ProcessingContext context = new ProcessingContext(types, elements, processingEnv.getMessager());
		Set<DeclaredTypeElementWrapper> typeElements = findCustomViewBindingTypeElements(roundEnv, context, types);
		for(DeclaredTypeElementWrapper typeElement : typeElements) {
			ViewBindingInfoBuilder builder = new ViewBindingInfoBuilder(typeElement, 
					typeElement.typeName() + VIEW_BINDING_OBJECT_SUFFIX);
			ViewBindingInfo info = builder.build();
			
			Logger log = context.loggerFor(typeElement);
			try {
				generateViewBindingObjectSourceFile(info, log);
			} catch (IOException e) {
				log.error(e);
				throw new RuntimeException(e);
			} catch (JClassAlreadyExistsException e) {
				log.error(e);
				throw new RuntimeException(e);
			} catch (ClassNotFoundException e) {
				log.error(e);
				throw new RuntimeException(e);
			}
		}
		return true;
	}

	private Set<DeclaredTypeElementWrapper> findCustomViewBindingTypeElements(RoundEnvironment env, 
			ProcessingContext context, TypesWrapper types) {
	    Set<DeclaredTypeElementWrapper> typeElements = Sets.newHashSet();
	    for (Element element : env.getElementsAnnotatedWith(ViewBinding.class)) {
	    	DeclaredTypeElementWrapper typeElement = new DeclaredTypeElementWrapper(context, types, 
	    			(TypeElement)element, (DeclaredType)element.asType());

	    	if(typeElement.isAssignableTo(CustomViewBinding.class) && typeElement.isConcreteClass()) {
	            typeElements.add(typeElement);
	    	}
	    }
	    return typeElements;
	  }
	
	private void generateViewBindingObjectSourceFile(ViewBindingInfo info, Logger log) throws IOException, JClassAlreadyExistsException, ClassNotFoundException {
		try {
			ViewBindingObjectClassGen gen = new ViewBindingObjectClassGen(info);
			run(gen);
			gen.writeTo(createOutput());
			log.info("ViewBinding '"+info.viewBindingObjectTypeName() + "' generated.");
		} catch (java.lang.NoClassDefFoundError e) {
			RuntimeException error = new RuntimeException(
					"an error occured when generating source code for '"+info.viewBindingObjectTypeName()+"'", e);
			log.error(error);
			throw error;
		}
	}

	private void run(ViewBindingObjectClassGen gen) throws JClassAlreadyExistsException {
		gen.defineFields();
		gen.defineConstructor();
		gen.defineSimpleOneWayPropertyClasses();
		gen.defineMapBindingAttributesMethod();
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
