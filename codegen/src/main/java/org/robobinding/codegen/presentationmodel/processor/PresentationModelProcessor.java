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
import javax.tools.Diagnostic.Kind;

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
import org.robobinding.codegen.typewrapper.ProcessingContext;
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
		ProcessingContext context = new ProcessingContext(processingEnv.getTypeUtils(), processingEnv.getElementUtils());
		Set<AbstractTypeElementWrapper> typeElements = findPresentationModelTypeElements(roundEnv, context);
		for(AbstractTypeElementWrapper typeElement : typeElements) {
			PresentationModelInfoBuilder builder = new PresentationModelInfoBuilder(typeElement, 
					typeElement.typeName() + PRESENTATION_MODEL_OBJECT_SUFFIX, true);
			PresentationModelInfo presentationModelInfo = builder.build();
			try {
				generateClasses(presentationModelInfo, context);
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

	private Set<AbstractTypeElementWrapper> findPresentationModelTypeElements(RoundEnvironment env, ProcessingContext context) {
	    Set<AbstractTypeElementWrapper> typeElements = Sets.newHashSet();
	    for (Element element : env.getElementsAnnotatedWith(PresentationModel.class)) {
	    	AbstractTypeElementWrapper typeElement = new DeclaredTypeElementWrapper(context, processingEnv.getTypeUtils(), (TypeElement)element);
	        
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

	protected void generateClasses(PresentationModelInfo presentationModelInfo, ProcessingContext context) throws IOException, JClassAlreadyExistsException, ClassNotFoundException {
		createItemPresentationModelObjectSourceFiles(presentationModelInfo, context);
		createPresentationModelObjectSourceFiles(presentationModelInfo);
	}
	
	private void createItemPresentationModelObjectSourceFiles(PresentationModelInfo presentationModelInfo, ProcessingContext context) 
			throws JClassAlreadyExistsException, IOException {
		for(DataSetPropertyInfo info : presentationModelInfo.dataSetProperties()) {
			if(processedItemPresentationModels.contains(info.itemPresentationModelTypeName())) {
				continue;
			}
			
			AbstractTypeElementWrapper typeElement = context.typeElementOf(info.itemPresentationModelTypeName());
			
			PresentationModelInfoBuilder builder = new PresentationModelInfoBuilder(
					typeElement, info.itemPresentationModelObjectTypeName(), false);
			PresentationModelInfo itemPresentationModelInfo = builder.build();
			try {
				ItemPresentationModelObjectClassGen gen = new ItemPresentationModelObjectClassGen(itemPresentationModelInfo);
				run(gen);
				gen.writeTo(createOutput());
				printMessage("ItemPresentationModel '"+itemPresentationModelInfo.getPresentationModelObjectTypeName() + "' generated.");
			} catch (java.lang.NoClassDefFoundError e) {
				throw new RuntimeException(
						"an error occured when generating source code for '"+presentationModelInfo.getPresentationModelObjectTypeName()+"'", e);
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
	
	private void printMessage(String message) {
		processingEnv.getMessager().printMessage(Kind.NOTE, message);
	}

	private CodeWriter createOutput() throws IOException, JClassAlreadyExistsException {
		return new SourceCodeWriter(processingEnv.getFiler());
	}

	private void createPresentationModelObjectSourceFiles(PresentationModelInfo presentationModelInfo) 
			throws JClassAlreadyExistsException, IOException {
		try {
			PresentationModelObjectClassGen gen = new PresentationModelObjectClassGen(presentationModelInfo);
			run(gen);
			gen.writeTo(createOutput());
			printMessage("PresentationModel '"+presentationModelInfo.getPresentationModelObjectTypeName() + "' generated.");
		} catch (java.lang.NoClassDefFoundError e) {
			throw new RuntimeException(
					"an error occured when generating source code for '"+presentationModelInfo.getPresentationModelObjectTypeName()+"'", e);
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
