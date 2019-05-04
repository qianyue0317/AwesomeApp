package com.j4u.annotationlib;

import com.google.auto.service.AutoService;
import com.j4u.annotationlib.api.FirstAnnotation;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

@AutoService(Processor.class)
public class J4UAnnotationProcessor extends AbstractProcessor {
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(FirstAnnotation.class.getCanonicalName());
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
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
        return false;
    }
}
