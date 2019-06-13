package com.j4u.annotationlib;

import com.google.auto.service.AutoService;
import com.qianyue.annotation_api.FirstAnnotation;
import com.qianyue.annotation_api.InstanceState;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
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
        try {
            processInstanceState(roundEnv);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
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
    private void processInstanceState(RoundEnvironment roundEnv) throws ClassNotFoundException {
        Set<? extends Element> instanceStateAnnos =
                roundEnv.getElementsAnnotatedWith(InstanceState.class);
        Map<String, Set<Element>> temp = new HashMap<>();
        for (Element e : instanceStateAnnos) {
            if (e.getKind() != ElementKind.FIELD) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "only support " +
                        "field");
                break;
            }
            Name qualifiedName = ((TypeElement) e.getEnclosingElement()).getQualifiedName();
            Set<Element> elements =
                    temp.get(qualifiedName.toString());
            if (elements == null) {
                elements = new HashSet<>();
                temp.put(qualifiedName.toString(), elements);
            }
            elements.add(e);
        }
        Set<String> keySet = temp.keySet();
        for (String key : keySet) {
            Set<Element> elements = temp.get(key);
            TypeElement enclosingElement = null;
            /*生成方法*/
            ClassName clzName = ClassName.get("android.os", "Bundle");
            MethodSpec.Builder saveMethodBuilder = MethodSpec.methodBuilder("save")
                    .addModifiers(Modifier.PUBLIC)
                    .returns(void.class)
                    .addParameter(clzName, "savedInstanceState")
                    .addParameter(Object.class, "activity");
            MethodSpec.Builder restoreMethodBuilder = MethodSpec.methodBuilder("restore")
                    .addModifiers(Modifier.PUBLIC)
                    .returns(void.class)
                    .addParameter(clzName, "savedInstanceState")
                    .addParameter(Object.class, "activity");
            for (Element e : elements) {
                if (enclosingElement == null) {
                    enclosingElement = (TypeElement) e.getEnclosingElement();
                }
                InstanceState instanceState = e.getAnnotation(InstanceState.class);
                String s = ((VariableElement) e).asType().toString();
                saveMethodBuilder.addStatement("savedInstanceState.put$L($S, $L)", getTypeName(s),
                        instanceState.key(),
                        String.format("((%s)activity).%s", enclosingElement.getSimpleName(),
                                e.getSimpleName()));
                restoreMethodBuilder.addStatement("(($L)activity).$L " +
                                "= savedInstanceState.get$L($S)",
                        enclosingElement.getSimpleName(), e.getSimpleName(), getTypeName(s)
                        , instanceState.key());
            }
            assert enclosingElement != null;
            TypeSpec createdClass =
                    TypeSpec.classBuilder(enclosingElement.getSimpleName() +
                            "$$InstanceStateHolder").addModifiers(Modifier.PUBLIC,
                            Modifier.FINAL).addSuperinterface(ClassName.get("com.qy.j4u.j4uLib",
                            "InstanceStateInjectorInterface"))
                            .addMethod(restoreMethodBuilder.build())
                            .addMethod(saveMethodBuilder.build()).build();//指定生成的类
            JavaFile javaFile =
                    JavaFile.builder(((PackageElement) enclosingElement.getEnclosingElement()).getQualifiedName().toString(), createdClass).build();

            try {
                javaFile.writeTo(processingEnv.getFiler());
            } catch (IOException ee) {
                ee.printStackTrace();
            }

        }

    }

    private String getTypeName(String s) {
        if ("int".equals(s)) {
            return "Int";
        } else if ("float".equals(s)) {
            return "Float";
        } else if ("double".equals(s)) {
            return "Double";
        } else if ("long".equals(s)) {
            return "Long";
        } else if ("boolean".equals(s)) {
            return "Boolean";
        } else if ("java.lang.String".equals(s)) {
            return "String";
        } else if ("byte".equals(s)) {
            return "Byte";
        } else if ("short".equals(s)) {
            return "Short";
        } else {
            throw new RuntimeException("暂时不支持其他类型");
        }
    }

}


/*
package com.qy.j4u.app.programming.activities;

import android.os.Bundle;

public class EssayListActivity$$InstanceStateHolder implements InstanceStateInjectorInterface {


    public void save(Bundle savedInstanceState,Object activity) {
        savedInstanceState.putInt("test", 2);
    }

    public void restore(Bundle saveDInstanceState, Object activity) {
        ((EssayListActivity)activity).mTestInstanceState = saveDInstanceState.getInt("test");
    }
}
 */
