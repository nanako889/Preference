package com.qbw.annotation.preference.compiler;

import com.qbw.annotation.preference.compiler.common.AbstractPoet;
import com.qbw.annotation.preference.compiler.common.ClassNames;
import com.qbw.annotation.preference.compiler.common.VariableEnity;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;

import static com.qbw.annotation.preference.compiler.common.ClassNames.PREFERENCE;
import static com.qbw.annotation.preference.compiler.common.ClassNames.PREFERENCE_UTIL;
import static com.qbw.annotation.preference.Constant.INNER_LINK;
import static com.qbw.annotation.preference.Constant.SPLIT;

/**
 * @author QBW
 * @createtime 2016/08/13 18:20
 * @company 9zhitx.com
 * @description
 */


public class ParasitePoet extends AbstractPoet {

    protected final String F_PUTIL = "mPUtil";
    protected final String M_REMOVE = "remove";

    private List<VariableEnity> mVariableNames;
    private ClassName mHostClassName;

    public ParasitePoet(Filer filer, String packageName, String complexClassName, String simpleClassName, String hostComplexClassName) {
        super(filer, packageName, complexClassName, simpleClassName);
        mVariableNames = new ArrayList<>();
        mHostClassName = ClassName.get(packageName, hostComplexClassName);
    }

    public List<VariableEnity> getVariableNames() {
        return mVariableNames;
    }

    @Override
    protected List<FieldSpec> getFields() {
        List<FieldSpec> fieldSpecs = new ArrayList<>();

        FieldSpec fieldSpec = FieldSpec.builder(mHostClassName, F_TARGET).addModifiers(Modifier.PRIVATE).build();
        fieldSpecs.add(fieldSpec);

        fieldSpec = FieldSpec.builder(PREFERENCE_UTIL, F_PUTIL).addModifiers(Modifier.PRIVATE).build();
        fieldSpecs.add(fieldSpec);

        return fieldSpecs;
    }

    @Override
    protected List<MethodSpec> getMethods() {
        List<MethodSpec> methodSpecs = new ArrayList<>();

        MethodSpec methodSpec = MethodSpec.constructorBuilder().addStatement("$L = $T.$L($T.$L())", F_PUTIL, PREFERENCE_UTIL, M_G_INST, PREFERENCE, M_G_CONTEXT).addModifiers(Modifier.PUBLIC).build();
        methodSpecs.add(methodSpec);

        MethodSpec.Builder builder = MethodSpec.methodBuilder(M_SAVE).addAnnotation(Override.class).addModifiers(Modifier.PUBLIC).
                addParameter(ParameterSpec.builder(ClassName.OBJECT, P_TARGET).build()).
                addStatement("$L = ($T) $L", F_TARGET, mHostClassName, P_TARGET);
        for (VariableEnity variableEnity : mVariableNames) {
            builder.addStatement("$L.put($S, $S, mTarget.$L)", F_PUTIL, getKey(variableEnity.getName()), variableEnity.getSimpleClassName(), variableEnity.getName());
        }
        methodSpecs.add(builder.build());

        builder = MethodSpec.methodBuilder(M_RESTORE).addModifiers(Modifier.PUBLIC).addAnnotation(Override.class).
                addParameter(ParameterSpec.builder(ClassName.OBJECT, P_TARGET).build()).
                addStatement("$L = ($T) $L", F_TARGET, mHostClassName, P_TARGET);
        for (VariableEnity variableEnity : mVariableNames) {
            builder.addStatement("$L.$L = ($T) $L.get($S, $S)", F_TARGET, variableEnity.getName(), variableEnity.getPoetTypeName(), F_PUTIL, getKey(variableEnity.getName()), variableEnity.getSimpleClassName());
        }
        methodSpecs.add(builder.build());

        builder = MethodSpec.methodBuilder(M_CLEAR).addAnnotation(Override.class).addModifiers(Modifier.PUBLIC).
                addParameter(ParameterSpec.builder(ClassName.OBJECT, P_TARGET).build()).
                addStatement("$L = ($T) $L", F_TARGET, mHostClassName, P_TARGET);
        for (VariableEnity variableEnity : mVariableNames) {
            builder.addStatement("$L.$L($S)", F_PUTIL, M_REMOVE, getKey(variableEnity.getName()));
        }
        methodSpecs.add(builder.build());

        return methodSpecs;
    }

    private String getKey(String variableName) {
        return SPLIT + mFileClassName + INNER_LINK + variableName;
    }

    @Override
    protected ClassName getParent() {
        return ClassNames.IPREFERENCE;
    }
}
