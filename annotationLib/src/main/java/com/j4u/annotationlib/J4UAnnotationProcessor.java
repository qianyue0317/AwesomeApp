package com.j4u.annotationlib;

import com.google.auto.service.AutoService;
import com.qianyue.annotation_api.api.FirstAnnotation;
import com.qianyue.annotation_api.api.InstanceState;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

@AutoService(Processor.class)
public class J4UAnnotationProcessor extends AbstractProcessor {
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        for (Class<? extends Annotation> annotation : getSupportAnnotations()) {
            types.add(annotation.getCanonicalName());
        }
        return types;
    }

    private Set<Class<? extends Annotation>> getSupportAnnotations() {
        Set<Class<? extends Annotation>> annotations = new LinkedHashSet<>();
        annotations.add(FirstAnnotation.class);
        annotations.add(InstanceState.class);
        return annotations;
    }


    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        processInstanceState(roundEnv);
        processFirstAnnotation(roundEnv);
        return false;
    }

    private void processFirstAnnotation(RoundEnvironment roundEnv) {
        Set<? extends Element> set = roundEnv.getElementsAnnotatedWith(FirstAnnotation.class);
        for (Element element : set) {
            if (element.getKind() != ElementKind.CLASS) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "only support " +
                        "class");
            }
            /*生成方法*/
            MethodSpec createdMethod = MethodSpec.methodBuilder("createApt")
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .returns(void.class)
                    .addParameter(String[].class, "parameters")
                    .addStatement("System.out.println($S)", "this`s java source is created by " +
                            "dynamic")
                    .build();


            TypeSpec createdClass =
                    TypeSpec.classBuilder("AptGenerator").addModifiers(Modifier.PUBLIC,
                            Modifier.FINAL).addMethod(createdMethod).build();//指定生成的类
            JavaFile javaFile = JavaFile.builder("com.qy.j4u", createdClass).build();

            try {
                javaFile.writeTo(processingEnv.getFiler());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 处理InstanceState注解
     */
    private void processInstanceState(RoundEnvironment roundEnv) {
        Set<? extends Element> instanceStateAnnos =
                roundEnv.getElementsAnnotatedWith(InstanceState.class);
        for (Element e : instanceStateAnnos) {
            if (e.getKind() != ElementKind.FIELD) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "only support " +
                        "field");
                break;
            }

            TypeElement enclosingElement = (TypeElement) e.getEnclosingElement();
            /*生成方法*/
            MethodSpec createdMethod = MethodSpec.methodBuilder("createApt")
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .returns(void.class)
                    .addParameter(String[].class, "parameters")
                    .addStatement("System.out.println($S)",
                            "enclosing1:" + enclosingElement.getQualifiedName())
                    .addStatement("System.out.println($S)",
                            "enclosing2:" + ((PackageElement) enclosingElement.getEnclosingElement()).getQualifiedName())
                    .build();


            TypeSpec createdClass =
                    TypeSpec.classBuilder("OutputContent").addModifiers(Modifier.PUBLIC,
                            Modifier.FINAL).addMethod(createdMethod).build();//指定生成的类
            JavaFile javaFile = JavaFile.builder("com.qy.j4u", createdClass).build();

            try {
                javaFile.writeTo(processingEnv.getFiler());
            } catch (IOException ee) {
                ee.printStackTrace();
            }
        }
    }
}
