package org.robobinding.codegen.viewbinding;

import java.io.IOException;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

import org.robobinding.annotation.ViewBinding;
import org.robobinding.codegen.SourceCodeWriter;
import org.robobinding.codegen.apt.Logger;
import org.robobinding.codegen.apt.RoundContext;
import org.robobinding.codegen.apt.element.WrappedTypeElement;
import org.robobinding.customviewbinding.ViewBindingLoader;

import com.google.common.collect.Sets;
import com.helger.jcodemodel.AbstractCodeWriter;
import com.helger.jcodemodel.JClassAlreadyExistsException;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class ViewBindingProcessor extends AbstractProcessor {
	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		RoundContext roundContext = new RoundContext(roundEnv, processingEnv.getTypeUtils(), 
				processingEnv.getElementUtils(), processingEnv.getMessager());
		Set<WrappedTypeElement> typeElements = roundContext.typeElementsAnnotatedWith(ViewBinding.class, 
				new ViewBindingFilter());
		
		for(WrappedTypeElement typeElement : typeElements) {
			String viewBindingObjectTypeName = ViewBindingLoader.getViewBindingClassName(typeElement.binaryName());
			ViewBindingInfoBuilder builder = new ViewBindingInfoBuilder(typeElement, viewBindingObjectTypeName);
			ViewBindingInfo info = builder.build();
			
			Logger log = typeElement.logger();
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

	private AbstractCodeWriter createOutput() throws IOException, JClassAlreadyExistsException {
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
