package org.robobinding.codegen.processor;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

import org.robobinding.annotation.PresentationModel;
import org.robobinding.binder.PresentationModelObjectLoader;
import org.robobinding.codegen.AbstractPresentationModelObjectClassGen;
import org.robobinding.codegen.DataSetPropertyInfo;
import org.robobinding.codegen.ItemPresentationModelObjectClassGen;
import org.robobinding.codegen.PresentationModelInfo;
import org.robobinding.codegen.PresentationModelObjectClassGen;
import org.robobinding.itempresentationmodel.ItemPresentationModel;

import com.google.android.collect.Sets;
import com.sun.codemodel.CodeWriter;
import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.writer.SingleStreamCodeWriter;

/**
 * @since 1.0
 * @author Cheng Wei
 * 
 */
public class PresentationModelProcessor extends AbstractProcessor {
	private static final String PRESENTATION_MODEL_OBJECT_SUFFIX = PresentationModelObjectLoader.CLASS_SUFFIX;

	@Override
	public Set<String> getSupportedAnnotationTypes() {
		Set<String> supportedAnnotationTypes = Sets.newArraySet();
		supportedAnnotationTypes.add(PresentationModel.class.getName());
		return supportedAnnotationTypes;
	}
	
	private Set<TypeElementWrapper> findPresentationModelTypeElements(RoundEnvironment env, ProcessingContext context) {
	    Set<TypeElementWrapper> typeElements = Sets.newHashSet();
	    for (Element element : env.getElementsAnnotatedWith(PresentationModel.class)) {
	    	TypeElementWrapper typeElement = new TypeElementWrapper(context, processingEnv.getTypeUtils(), (TypeElement)element);
            
	    	checkIsConcreteClass(typeElement);
	    	
	    	if(isNotItemPresentationModel(typeElement)) {
	            typeElements.add(typeElement);
	    	}
	    }
	    return typeElements;
	  }

	private void checkIsConcreteClass(TypeElementWrapper typeElement) {
		if(typeElement.isNotConcreteClass()){
			throw new RuntimeException(MessageFormat.format("@{0} can only be used to annotate a concrete PresentationModel, '{1}' is not.", 
					PresentationModel.class.getName(), typeElement.typeName()));
		}
	}

	private boolean isNotItemPresentationModel(TypeElementWrapper typeElement) {
		return !typeElement.isAssignableTo(ItemPresentationModel.class);
	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		ProcessingContext context = new ProcessingContext(processingEnv.getTypeUtils(), processingEnv.getElementUtils());
		Set<TypeElementWrapper> typeElements = findPresentationModelTypeElements(roundEnv, context);
		for(TypeElementWrapper typeElement : typeElements) {
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

	protected void generateClasses(PresentationModelInfo presentationModelInfo, ProcessingContext context) throws IOException, JClassAlreadyExistsException, ClassNotFoundException {
		createItemPresentationModelObjectSourceFiles(presentationModelInfo, context);
		createPresentationModelObjectSourceFiles(presentationModelInfo);
	}
	
	private void createItemPresentationModelObjectSourceFiles(PresentationModelInfo presentationModelInfo, ProcessingContext context) 
			throws JClassAlreadyExistsException, IOException {
		for(DataSetPropertyInfo info : presentationModelInfo.dataSetProperties()) {
			TypeElementWrapper typeElement = context.TypeElementOf(info.itemPresentationModelTypeName());
			
			PresentationModelInfoBuilder builder = new PresentationModelInfoBuilder(
					typeElement, info.itemPresentationModelObjectTypeName(), false);
			PresentationModelInfo itemPresentationModelInfo = builder.build();
			ItemPresentationModelObjectClassGen gen = new ItemPresentationModelObjectClassGen(itemPresentationModelInfo);
			run(gen);
			gen.writeTo(createOutput(itemPresentationModelInfo.getPresentationModelObjectTypeName()));
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

	private CodeWriter createOutput(String typeName) throws IOException, JClassAlreadyExistsException {
		JavaFileObject sourceFile = processingEnv.getFiler().createSourceFile(typeName);
		return new SingleStreamCodeWriter(sourceFile.openOutputStream());
	}

	private void createPresentationModelObjectSourceFiles(PresentationModelInfo presentationModelInfo) 
			throws JClassAlreadyExistsException, IOException {
		PresentationModelObjectClassGen gen = new PresentationModelObjectClassGen(presentationModelInfo);
		run(gen);
		gen.writeTo(createOutput(presentationModelInfo.getPresentationModelObjectTypeName()));
	}
}
