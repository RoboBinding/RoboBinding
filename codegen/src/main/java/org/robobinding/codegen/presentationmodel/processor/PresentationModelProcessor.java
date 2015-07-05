package org.robobinding.codegen.presentationmodel.processor;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.util.Elements;

import org.robobinding.annotation.PresentationModel;
import org.robobinding.binder.PresentationModelObjectLoader;
import org.robobinding.codegen.SourceCodeWriter;
import org.robobinding.codegen.presentationmodel.AbstractPresentationModelObjectClassGen;
import org.robobinding.codegen.presentationmodel.DataSetPropertyInfo;
import org.robobinding.codegen.presentationmodel.ItemPresentationModelObjectClassGen;
import org.robobinding.codegen.presentationmodel.PresentationModelInfo;
import org.robobinding.codegen.presentationmodel.PresentationModelObjectClassGen;
import org.robobinding.codegen.typewrapper.AbstractTypeElementWrapper;
import org.robobinding.codegen.typewrapper.DeclaredTypeElementWrapper;
import org.robobinding.codegen.typewrapper.Logger;
import org.robobinding.codegen.typewrapper.ProcessingContext;
import org.robobinding.codegen.typewrapper.TypesWrapper;
import org.robobinding.itempresentationmodel.ItemPresentationModel;

import com.google.common.collect.Sets;
import com.sun.codemodel.CodeWriter;
import com.sun.codemodel.JClassAlreadyExistsException;

/**
 * @since 1.0
 * @author Cheng Wei
 * 
 */
//@SupportedAnnotationTypes("org.robobinding.annotation.PresentationModel")
public class PresentationModelProcessor extends AbstractProcessor {
	private static final String PRESENTATION_MODEL_OBJECT_SUFFIX = PresentationModelObjectLoader.CLASS_SUFFIX;
	private Set<String> processedItemPresentationModels;
	
	@Override
	public synchronized void init(ProcessingEnvironment processingEnv) {
		super.init(processingEnv);
		
		processedItemPresentationModels = Sets.newHashSet();
	}
	
	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		Elements elements = processingEnv.getElementUtils();
		TypesWrapper types = new TypesWrapper(processingEnv.getTypeUtils(), elements);
		ProcessingContext context = new ProcessingContext(types, elements, processingEnv.getMessager());
		Set<AbstractTypeElementWrapper> typeElements = findPresentationModelTypeElements(roundEnv, context, types);
		for(AbstractTypeElementWrapper typeElement : typeElements) {
			PresentationModelInfoBuilder builder = new PresentationModelInfoBuilder(typeElement, 
					typeElement.typeName() + PRESENTATION_MODEL_OBJECT_SUFFIX, true);
			PresentationModelInfo presentationModelInfo = builder.build();
			
			Logger log = context.loggerFor(typeElement);
			try {
				generateClasses(presentationModelInfo, context, log);
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

	private Set<AbstractTypeElementWrapper> findPresentationModelTypeElements(RoundEnvironment env, 
			ProcessingContext context, TypesWrapper types) {
	    Set<AbstractTypeElementWrapper> typeElements = Sets.newHashSet();
	    for (Element element : env.getElementsAnnotatedWith(PresentationModel.class)) {
	    	AbstractTypeElementWrapper typeElement = new DeclaredTypeElementWrapper(context, types, 
	    			(TypeElement)element, (DeclaredType)element.asType());
	        
	    	checkIsConcreteClass(typeElement);
	    	
	    	if(isNotItemPresentationModel(typeElement)) {
	            typeElements.add(typeElement);
	    	}
	    }
	    return typeElements;
	  }

	private void checkIsConcreteClass(AbstractTypeElementWrapper typeElement) {
		if(typeElement.isNotConcreteClass()){
			throw new RuntimeException(MessageFormat.format("@{0} can only be used to annotate a concrete PresentationModel, '{1}' is not.", 
					PresentationModel.class.getName(), typeElement.typeName()));
		}
	}

	private boolean isNotItemPresentationModel(AbstractTypeElementWrapper typeElement) {
		return !typeElement.isAssignableTo(ItemPresentationModel.class);
	}

	protected void generateClasses(PresentationModelInfo presentationModelInfo, ProcessingContext context,
			Logger log) throws IOException, JClassAlreadyExistsException, ClassNotFoundException {
		createItemPresentationModelObjectSourceFiles(presentationModelInfo, context);
		createPresentationModelObjectSourceFiles(presentationModelInfo, log);
	}
	
	private void createItemPresentationModelObjectSourceFiles(PresentationModelInfo presentationModelInfo, ProcessingContext context) 
			throws JClassAlreadyExistsException, IOException {
		for(DataSetPropertyInfo info : presentationModelInfo.dataSetProperties()) {
			if(processedItemPresentationModels.contains(info.itemPresentationModelTypeName())) {
				continue;
			}
			
			AbstractTypeElementWrapper typeElement = context.declaredTypeElementOf(info.itemPresentationModelTypeName());
			
			PresentationModelInfoBuilder builder = new PresentationModelInfoBuilder(
					typeElement, info.itemPresentationModelObjectTypeName(), false);
			PresentationModelInfo itemPresentationModelInfo = builder.build();
			Logger log = context.loggerFor(typeElement);
			try {
				ItemPresentationModelObjectClassGen gen = new ItemPresentationModelObjectClassGen(itemPresentationModelInfo);
				run(gen);
				gen.writeTo(createOutput());
				log.info("ItemPresentationModel '"+itemPresentationModelInfo.getPresentationModelObjectTypeName() + "' generated.");
			} catch (java.lang.NoClassDefFoundError e) {
				RuntimeException error = new RuntimeException(
						"an error occured when generating source code for '"+presentationModelInfo.getPresentationModelObjectTypeName()+"'", e);
				log.error(error);
				throw error;
			}
			
			processedItemPresentationModels.add(info.itemPresentationModelTypeName());
		}
	}

	private void run(AbstractPresentationModelObjectClassGen gen) {
		gen.defineFields();
		gen.defineConstructor();
		gen.definePropertyNames();
		gen.defineDataSetPropertyNames();
		gen.defineEventMethods();
		gen.definePropertyDependencies();
		gen.defineTryToCreateProperty();
		gen.defineTryToCreateDataSetProperty();
		gen.defineTryToCreateFunction();
	}
	
	private CodeWriter createOutput() throws IOException, JClassAlreadyExistsException {
		return new SourceCodeWriter(processingEnv.getFiler());
	}

	private void createPresentationModelObjectSourceFiles(PresentationModelInfo presentationModelInfo, Logger log) 
			throws JClassAlreadyExistsException, IOException {
		try {
			PresentationModelObjectClassGen gen = new PresentationModelObjectClassGen(presentationModelInfo);
			run(gen);
			gen.writeTo(createOutput());
			log.info("PresentationModel '"+presentationModelInfo.getPresentationModelObjectTypeName() + "' generated.");
		} catch (java.lang.NoClassDefFoundError e) {
			RuntimeException error = new RuntimeException(
					"an error occured when generating source code for '"+presentationModelInfo.getPresentationModelObjectTypeName()+"'", e);
			log.error(error);
			throw error;
		}
	}

	@Override
	public Set<String> getSupportedAnnotationTypes() {
		Set<String> supportedAnnotationTypes = Sets.newHashSet();
		supportedAnnotationTypes.add(PresentationModel.class.getName());
		return supportedAnnotationTypes;
	}

	@Override
	public SourceVersion getSupportedSourceVersion() {
		return SourceVersion.latest();
	}
}
