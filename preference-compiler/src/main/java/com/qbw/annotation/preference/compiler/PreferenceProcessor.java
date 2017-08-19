package com.qbw.annotation.preference.compiler;

import com.google.auto.service.AutoService;
import com.qbw.annotation.preference.SharedPreference;
import com.qbw.annotation.preference.SharedPreferenceDynamic;
import com.qbw.annotation.preference.compiler.common.Log;
import com.qbw.annotation.preference.compiler.common.VariableEnity;

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

    private Map<String, ParasitePoet> mVariables;

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
            if (!parseElement(SharedPreference.class, element)) {
                return false;
            }
        }

        for (Element element : roundEnvironment.getElementsAnnotatedWith(SharedPreferenceDynamic.class)) {
            if (!parseElement(SharedPreferenceDynamic.class, element)) {
                return false;
            }
        }

        if (!mVariables.isEmpty()) {

            ParasitePoet[] poets;
            mVariables.values().toArray(poets = new ParasitePoet[mVariables.values().size()]);
            int ppCount = poets.length;
            for (int i = 0; i < ppCount - 1; i++) {
                for (int j = i + 1; j < ppCount; j++) {
                    String iname = poets[i].getHostComplexClassName();
                    String jname = poets[j].getHostComplexClassName();
                    if (iname.equals(jname)) {
                        Log.e(String.format(
                                "[Preference]\n*****\nSharedPreference is not allowed to be used in classes whichs classname is equal, such as [%s.%s, %s.%s]\n*****",
                                poets[i].getGeneratePackageName(),
                                iname,
                                poets[j].getGeneratePackageName(),
                                jname));
                        return false;
                    }
                }
            }

            for (Map.Entry<String, ParasitePoet> entry : mVariables.entrySet()) {
                entry.getValue().generate();
            }

            new PreferencePoet(mFiler, mVariables).generate();
        }


        return true;
    }

    private boolean parseElement(Class c, Element element) {
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
        String complexClassName = className.substring(className.indexOf(packageName) + packageName.length() + 1);//Inner Class

        ParasitePoet variables = mVariables.get(className);
        if (null == variables) {
            variables = new ParasitePoet(mFiler, packageName, complexClassName);
            mVariables.put(className, variables);
        }
        VariableEnity enity = new VariableEnity(variableName, variableType);
        variables.getVariableNames().add(enity);
        if (c.getName().equals(SharedPreferenceDynamic.class.getName())) {
            enity.setDynamic(true);
        }
        return true;
    }
}
