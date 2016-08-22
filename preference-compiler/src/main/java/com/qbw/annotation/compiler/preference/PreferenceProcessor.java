package com.qbw.annotation.compiler.preference;

import com.google.auto.service.AutoService;
import com.qbw.annotation.compiler.preference.common.Constant;
import com.qbw.annotation.compiler.preference.common.Log;
import com.qbw.annotation.compiler.preference.common.VariableEnity;
import com.qbw.annotation.preference.SharedPreference;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;

/**
 * @author QBW
 * @createtime 2016/08/13 17:54
 * @company 9zhitx.com
 * @description
 */

@SupportedAnnotationTypes("com.qbw.annotation.preference.SharedPreference")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
@AutoService(Processor.class)
public class PreferenceProcessor extends AbstractProcessor {

    private Elements mElements;
    private Filer mFiler;

    private Map<String, HostPoet> mVariables;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        Log.setMessager(processingEnvironment.getMessager());
        mElements = processingEnvironment.getElementUtils();
        mFiler = processingEnvironment.getFiler();
        mVariables = new LinkedHashMap<>();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        Log.i("Run in PreferenceProcessor process");

        mVariables.clear();


        for (Element element : roundEnvironment.getElementsAnnotatedWith(SharedPreference.class)) {

            String variableName = element.toString();
            TypeMirror variableType = element.asType();

            if (ElementKind.FIELD != element.getKind()) {
                Log.e(variableName + " should be a field variable");
                return false;
            }

            for (Modifier modifier : element.getModifiers()) {
                if (modifier == Modifier.PRIVATE) {
                    Log.e(variableName + " can not be Private");
                    return false;
                }
            }

            TypeElement parentElement = (TypeElement) element.getEnclosingElement();
            String packageName = mElements.getPackageOf(parentElement).getQualifiedName().toString();
            String className = parentElement.getQualifiedName().toString();
            String simpleClassName = parentElement.getSimpleName().toString();
            String complexClassName = className.substring(className.indexOf(packageName) +
                    packageName.length() + 1);//Inner Class

            HostPoet variables = mVariables.get(className);
            if (null == variables) {
                variables = new HostPoet(mFiler, packageName, Constant.appendSuffix(complexClassName), Constant.appendSuffix(simpleClassName), complexClassName);
                mVariables.put(className, variables);
            }
            variables.getVariableNames().add(new VariableEnity(variableName, variableType));
        }

        if (!mVariables.isEmpty()) {

            //new IPrePoet(mFiler).generate();

            new PreUtilPoet(mFiler).generate();

            //new PreHelperPoet(mFiler).generate();

            //new PreferencePoet(mFiler).generate();

            for (Map.Entry<String, HostPoet> entry : mVariables.entrySet()) {
                entry.getValue().generate();
            }
        }


        return true;
    }
}
