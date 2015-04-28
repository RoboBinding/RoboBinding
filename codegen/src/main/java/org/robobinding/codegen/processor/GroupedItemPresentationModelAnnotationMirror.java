package org.robobinding.codegen.processor;

/**
 * Created by jihunlee on 4/25/15.
 */
public class GroupedItemPresentationModelAnnotationMirror {
    private final AnnotationMirrorWrapper annotationMirror;
    public GroupedItemPresentationModelAnnotationMirror(AnnotationMirrorWrapper annotationMirror) {
        this.annotationMirror = annotationMirror;
    }

    public String groupedItemPresentationModelTypeName() {
        return annotationMirror.annotationValueAsType("value").typeName();
    }

    public boolean hasFactoryMethod() {
        return annotationMirror.hasAnnotationValue("factoryMethod");
    }

    public String factoryMethod() {
        return annotationMirror.annotationValueAsText("factoryMethod");
    }
}
