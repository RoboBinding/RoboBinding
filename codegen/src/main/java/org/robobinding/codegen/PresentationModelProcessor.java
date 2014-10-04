package org.robobinding.codegen;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.text.MessageFormat;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.tools.JavaFileObject;

import org.robobinding.annotation.PresentationModel;
import org.robobinding.binder.PresentationModelObjectLoader;
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
	
	private Set<TypeElement> findPresentationModelTypeElements(RoundEnvironment env) {
	    Set<TypeElement> typeElements = Sets.newHashSet();
	    for (Element element : env.getElementsAnnotatedWith(PresentationModel.class)) {
	    	TypeElement typeElement = (TypeElement)element;
            
	    	checkIsConcreteClass(typeElement);
	    	
	    	if(isNotItemPresentationModel(typeElement)) {
	            typeElements.add(typeElement);
	    	}
	    }
	    return typeElements;
	  }

	private void checkIsConcreteClass(TypeElement typeElement) {
		if(!typeElement.getKind().isClass() || typeElement.getModifiers().contains(Modifier.ABSTRACT)){
            String typeName = typeElement.getQualifiedName().toString();
			throw new RuntimeException(MessageFormat.format("@{0} can only be used to annotate a concrete PresentationModel, '{1}' is not.", 
					PresentationModel.class.getName(), typeName));
		}
	}

	private boolean isNotItemPresentationModel(TypeElement typeElement) {
		TypeElement itemPresentationModelInterface = processingEnv.getElementUtils().getTypeElement(ItemPresentationModel.class.getName());
		return !processingEnv.getTypeUtils().isAssignable(typeElement.asType(), itemPresentationModelInterface.asType());
	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		Set<TypeElement> typeElements = findPresentationModelTypeElements(roundEnv);
		for(TypeElement typeElement : typeElements) {
			processingEnv.getTypeUtils().isSameType(typeElement.getSuperclass(), processingEnv.getTypeUtils().getNoType(TypeKind.NULL));
			try {
				generateClassFor(typeElement);
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

	private void generateClassFor(TypeElement typeElement) throws IOException, JClassAlreadyExistsException, ClassNotFoundException {
		
		PresentationModelInfoBuilder1 builder = new PresentationModelInfoBuilder1(type, type.getName() + PRESENTATION_MODEL_OBJECT_SUFFIX);
		PresentationModelInfo presentationModelInfo = buildPresentationModelInfo(builder);
		createItemPresentationModelObjectSourceFiles(presentationModelInfo);
		createPresentationModelObjectSourceFiles(presentationModelInfo);
	}
	
	private PresentationModelInfo buildPresentationModelInfo(AbstractPresentationModelInfoBuilder1 builder) {
		builder.buildProperties();
		builder.buildEventMethods();
		return builder.getResult();
	}
	
	private void createItemPresentationModelObjectSourceFiles(PresentationModelInfo presentationModelInfo) 
			throws JClassAlreadyExistsException, IOException {
		for(DataSetPropertyInfoForTest info : presentationModelInfo.dataSetProperties()) {
			ItemPresentationModelInfoBuilder1 builder = new ItemPresentationModelInfoBuilder1(
					info.itemPresentationModelType(), info.itemPresentationModelObjectTypeName());
			PresentationModelInfo itemPresentationModelInfo = buildPresentationModelInfo(builder);
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
