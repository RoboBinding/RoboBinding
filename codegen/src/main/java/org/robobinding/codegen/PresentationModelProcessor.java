package org.robobinding.codegen;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.text.MessageFormat;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
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
	private static final String PRESENTATION_MODEL_SUFFIX = PresentationModelObjectLoader.CLASS_SUFFIX;
	private static final String ITEM_PRESENTATION_MODEL_SUFFIX = "$$IPM";

	@Override
	public Set<String> getSupportedAnnotationTypes() {
		Set<String> supportedAnnotationTypes = Sets.newArraySet();
		supportedAnnotationTypes.add(PresentationModel.class.getName());
		return supportedAnnotationTypes;
	}
	
	private Set<String> findClassNames(RoundEnvironment env) {
	    Set<String> typeNames = Sets.newHashSet();
	    for (Element element : env.getElementsAnnotatedWith(PresentationModel.class)) {
	    	TypeElement typeElement = (TypeElement)element;
            String typeName = typeElement.getQualifiedName().toString();
            
	    	if(!element.getKind().isClass() || element.getModifiers().contains(Modifier.ABSTRACT)){
	    		throw new RuntimeException(MessageFormat.format("@{0} can only be used to annotate a concrete PresentationModel, '{1}' is not.", 
	    				PresentationModel.class.getName(), typeName));
	    	}

	    	typeNames.add(typeName);
	    }
	    return typeNames;
	  }

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		Set<String> typeNames = findClassNames(roundEnv);
		for(String typeName : typeNames) {
			try {
				generateClassFor(typeName);
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

	private void generateClassFor(String typeName) throws IOException, JClassAlreadyExistsException, ClassNotFoundException {
		Class<?> type = Class.forName(typeName);
		if (ItemPresentationModel.class.isAssignableFrom(type)) {
			new ItemPresentationModelObjectGen().writeTo(
					createOutput(typeName+ITEM_PRESENTATION_MODEL_SUFFIX));
		} else {
			new PresentationModelGen().writeTo(
					createOutput(typeName+PRESENTATION_MODEL_SUFFIX));
		}
	}
	
	private CodeWriter createOutput(String typeName) throws IOException, JClassAlreadyExistsException {
		JavaFileObject sourceFile = processingEnv.getFiler().createSourceFile(typeName);
		return new SingleStreamCodeWriter(sourceFile.openOutputStream());
	}
}
