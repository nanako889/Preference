package com.qbw.annotation.preference.compiler;

import com.google.auto.service.AutoService;
import com.qbw.annotation.preference.compiler.common.Log;
import com.qbw.annotation.preference.compiler.common.VariableEnity;
import com.qbw.annotation.preference.Constant;
import com.qbw.annotation.preference.SharedPreference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;
import java.util.WeakHashMap;

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

    private Set<String> mNotSupportedVariableType;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        Log.setMessager(processingEnvironment.getMessager());
        mElements = processingEnvironment.getElementUtils();
        mFiler = processingEnvironment.getFiler();
        mVariables = new LinkedHashMap<>();
        mNotSupportedVariableType = getNotSupportedVariableTypes();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        Log.i("Run in PreferenceProcessor process");

        mVariables.clear();


        for (Element element : roundEnvironment.getElementsAnnotatedWith(SharedPreference.class)) {

            String variableName = element.toString();
            TypeMirror variableType = element.asType();

            if (!isVariableTypeSupported(variableType.toString())) {
                Log.e("Not support " + variableType.toString());
                return false;
            }

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

            ParasitePoet variables = mVariables.get(className);
            if (null == variables) {
                variables = new ParasitePoet(mFiler, packageName, Constant.appendSuffix(complexClassName), Constant.appendSuffix(simpleClassName), complexClassName);
                mVariables.put(className, variables);
            }
            variables.getVariableNames().add(new VariableEnity(variableName, variableType));
        }

        if (!mVariables.isEmpty()) {

            for (Map.Entry<String, ParasitePoet> entry : mVariables.entrySet()) {
                entry.getValue().generate();
            }

            new PreferencePoet(mFiler, mVariables).generate();
            //new InitPoet(mFiler, mVariables).generate();
        }


        return true;
    }

    protected Set<String> getNotSupportedVariableTypes() {
        Set<String> vts = new HashSet<>();

        vts.add(Map.class.getCanonicalName());
        vts.add(HashMap.class.getCanonicalName());
        vts.add(Hashtable.class.getCanonicalName());
        vts.add(WeakHashMap.class.getCanonicalName());
        vts.add(LinkedHashMap.class.getCanonicalName());
        vts.add(TreeMap.class.getCanonicalName());
        vts.add(IdentityHashMap.class.getCanonicalName());

        vts.add(Set.class.getCanonicalName());
        vts.add(HashSet.class.getCanonicalName());
        vts.add(LinkedHashSet.class.getCanonicalName());
        vts.add(TreeSet.class.getCanonicalName());

        vts.add(List.class.getCanonicalName());
        vts.add(Vector.class.getCanonicalName());
        vts.add(ArrayList.class.getCanonicalName());
        vts.add(LinkedList.class.getCanonicalName());

        vts.add(String[].class.getCanonicalName());

        return vts;
    }

    private boolean isVariableTypeSupported(String canonicalName) {
        boolean ret = true;
        for (String s : mNotSupportedVariableType) {
            if (canonicalName.startsWith(s)) {
                ret = false;
                break;
            }
        }
        return ret;
    }
}
