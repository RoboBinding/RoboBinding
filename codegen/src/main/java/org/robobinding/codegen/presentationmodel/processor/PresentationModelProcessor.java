package org.robobinding.codegen.presentationmodel.processor;

import java.io.IOException;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

import org.robobinding.annotation.PresentationModel;
import org.robobinding.binder.ItemPresentationModelObjectLoader;
import org.robobinding.binder.PresentationModelObjectLoader;
import org.robobinding.codegen.SourceCodeWriter;
import org.robobinding.codegen.apt.Logger;
import org.robobinding.codegen.apt.ProcessingContext;
import org.robobinding.codegen.apt.RoundContext;
import org.robobinding.codegen.apt.element.WrappedTypeElement;
import org.robobinding.codegen.presentationmodel.AbstractPresentationModelObjectClassGen;
import org.robobinding.codegen.presentationmodel.DataSetPropertyInfo;
import org.robobinding.codegen.presentationmodel.ItemPresentationModelObjectClassGen;
import org.robobinding.codegen.presentationmodel.PresentationModelInfo;
import org.robobinding.codegen.presentationmodel.PresentationModelObjectClassGen;
import org.robobinding.itempresentationmodel.ItemPresentationModel;

import com.google.common.collect.Sets;
import com.helger.jcodemodel.AbstractCodeWriter;
import com.helger.jcodemodel.JClassAlreadyExistsException;

/**
 * @since 1.0
 * @author Cheng Wei
 * 
 */
//@SupportedAnnotationTypes("org.robobinding.annotation.PresentationModel")
public class PresentationModelProcessor extends AbstractProcessor {
	private static final String PRESENTATION_MODEL_OBJECT_SUFFIX = PresentationModelObjectLoader.CLASS_SUFFIX;
	private static final String ITEM_PRESENTATION_MODEL_OBJECT_SUFFIX = ItemPresentationModelObjectLoader.CLASS_SUFFIX;
	private Set<String> processedItemPresentationModels;
	
	@Override
	public synchronized void init(ProcessingEnvironment processingEnv) {
		super.init(processingEnv);
		
		processedItemPresentationModels = Sets.newHashSet();
	}
	
	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		RoundContext roundContext = new RoundContext(roundEnv, processingEnv.getTypeUtils(), 
				processingEnv.getElementUtils(), processingEnv.getMessager());
		Set<WrappedTypeElement> typeElements = roundContext.typeElementsAnnotatedWith(PresentationModel.class, 
				new PresentationModelFilter());
		
		for(WrappedTypeElement typeElement : typeElements) {
			String presentationModelObjectTypeName = typeElement.qName() + PRESENTATION_MODEL_OBJECT_SUFFIX;
			PresentationModelInfoBuilder builder = new PresentationModelInfoBuilder(typeElement, 
					presentationModelObjectTypeName, true);
			PresentationModelInfo presentationModelInfo = builder.build();
			
			Logger log = typeElement.logger();
			ProcessingContext context = new ProcessingContext(processingEnv.getTypeUtils(), processingEnv.getElementUtils(),
					processingEnv.getMessager());
			try {
				if (isItemPresentationModelAlso(typeElement)) {
					createItemPresentationModelObjectSourceFiles(presentationModelInfo, context);
					createItemPresentationModelObjectSourceFiles(typeElement.qName(),
							typeElement.qName() + ITEM_PRESENTATION_MODEL_OBJECT_SUFFIX, context);
				} else {
					generateAllClasses(presentationModelInfo, context, log);
				}
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
	
	private boolean isItemPresentationModelAlso(WrappedTypeElement typeElement) {
		return typeElement.isAssignableTo(ItemPresentationModel.class);
	}


	protected void generateAllClasses(PresentationModelInfo presentationModelInfo, ProcessingContext context,
			Logger log) throws IOException, JClassAlreadyExistsException, ClassNotFoundException {
		createItemPresentationModelObjectSourceFiles(presentationModelInfo, context);
		createPresentationModelObjectSourceFiles(presentationModelInfo, log);
	}
	
	private void createItemPresentationModelObjectSourceFiles(PresentationModelInfo presentationModelInfo, ProcessingContext context) 
			throws JClassAlreadyExistsException, IOException {
		for(DataSetPropertyInfo info : presentationModelInfo.dataSetProperties()) {
			createItemPresentationModelObjectSourceFiles(info.itemPresentationModelTypeName(),
					info.itemPresentationModelObjectTypeName(), context);
		}
	}

	private void createItemPresentationModelObjectSourceFiles(String itemPresentationModelTypeName,
															  String itemPresentationModelObjectTypeName,
															  ProcessingContext context)
			throws JClassAlreadyExistsException, IOException {
		if(processedItemPresentationModels.contains(itemPresentationModelTypeName)) {
			return;
		}

		WrappedTypeElement typeElement = context.typeElementOf(itemPresentationModelTypeName);

		PresentationModelInfoBuilder builder = new PresentationModelInfoBuilder(
				typeElement, itemPresentationModelObjectTypeName, true);
		PresentationModelInfo itemPresentationModelInfo = builder.build();
		Logger log = typeElement.logger();
		try {
			ItemPresentationModelObjectClassGen gen = new ItemPresentationModelObjectClassGen(itemPresentationModelInfo);
			run(gen);
			gen.writeTo(createOutput());
			log.info("ItemPresentationModel '"+itemPresentationModelInfo.getPresentationModelObjectTypeName() + "' generated.");
		} catch (java.lang.NoClassDefFoundError e) {
			RuntimeException error = new RuntimeException(
					"an error occured when generating source code for '"+itemPresentationModelInfo.getPresentationModelObjectTypeName()+"'", e);
			log.error(error);
			throw error;
		}

		processedItemPresentationModels.add(itemPresentationModelTypeName);
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
	
	private AbstractCodeWriter createOutput() throws IOException, JClassAlreadyExistsException {
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
