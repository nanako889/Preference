package com.qbw.annotation.compiler.preference;

import com.qbw.annotation.compiler.preference.common.AbstractPoet;
import com.qbw.annotation.compiler.preference.common.VariableEnity;
import com.qbw.annotation.compiler.preference.common.ClassNames;
import com.qbw.annotation.compiler.preference.common.Constant;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;

import static com.qbw.annotation.compiler.preference.common.ClassNames.PREFERENCE_UTIL;

/**
 * @author QBW
 * @createtime 2016/08/13 18:20
 * @company 9zhitx.com
 * @description
 */


public class HostPoet extends AbstractPoet {

    private List<VariableEnity> mVariableNames;
    private ClassName mHostClassName;

    public HostPoet(Filer filer, String packageName, String complexClassName, String simpleClassName, String hostComplexClassName) {
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
        return fieldSpecs;
    }

    @Override
    protected List<MethodSpec> getMethods() {
        List<MethodSpec> methodSpecs = new ArrayList<>();

        MethodSpec.Builder builder = MethodSpec.methodBuilder(M_SAVE).addAnnotation(Override.class).addModifiers(Modifier.PUBLIC).
                addParameter(ParameterSpec.builder(ClassName.OBJECT, P_TARGET).build()).
                addStatement("$L = ($T) $L", F_TARGET, mHostClassName, P_TARGET).
                addStatement("$T pu = $T.$L($S)", PREFERENCE_UTIL, PREFERENCE_UTIL, M_G_INST, mPackageName);
        for (VariableEnity variableEnity : mVariableNames) {
            builder.addStatement("pu.put($S, $S, mTarget.$L)", getKey(variableEnity.getName()), variableEnity.getSimpleClassName(), variableEnity.getName());
        }
        methodSpecs.add(builder.build());

        builder = MethodSpec.methodBuilder(M_RESTORE).addModifiers(Modifier.PUBLIC).addAnnotation(Override.class).
                addParameter(ParameterSpec.builder(ClassName.OBJECT, P_TARGET).build()).
                addStatement("$L = ($T) $L", F_TARGET, mHostClassName, P_TARGET).
                addStatement("$T t = $T.getInstance($S)", PREFERENCE_UTIL, PREFERENCE_UTIL, mPackageName);
        for (VariableEnity variableEnity : mVariableNames) {
            builder.addStatement("$L.$L = ($T) t.get($S, $S)", F_TARGET, variableEnity.getName(), variableEnity.getPoetTypeName(), getKey(variableEnity.getName()), variableEnity.getSimpleClassName());
        }
        methodSpecs.add(builder.build());

        return methodSpecs;
    }

    private String getKey(String variableName) {
        return mFileClassName + Constant.INNER_LINK + variableName;
    }

    @Override
    protected ClassName getParent() {
        return ClassNames.IPREFERENCE;
    }
}
