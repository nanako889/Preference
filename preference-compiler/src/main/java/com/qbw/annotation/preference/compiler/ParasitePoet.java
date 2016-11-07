package com.qbw.annotation.preference.compiler;

import com.qbw.annotation.preference.Constant;
import com.qbw.annotation.preference.compiler.common.ClassNames;
import com.qbw.annotation.preference.compiler.common.CommonPoet;
import com.qbw.annotation.preference.compiler.common.VariableEnity;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;

/**
 * @author QBW
 * @createtime 2016/08/13 18:20
 */


public class ParasitePoet extends CommonPoet {

    protected final String M_REMOVE = "remove";

    private List<VariableEnity> mVariableNames;

    public ParasitePoet(Filer filer, String hostPackageName, String hostComplexClassName) {
        super(filer, hostPackageName, hostComplexClassName, hostPackageName, Constant.appendSuffix(hostComplexClassName));
        mVariableNames = new ArrayList<>();
    }

    public List<VariableEnity> getVariableNames() {
        return mVariableNames;
    }

    @Override
    protected List<FieldSpec> getFields() {
        List<FieldSpec> fieldSpecs = new ArrayList<>();

        FieldSpec.Builder builder = FieldSpec.builder(ClassNames.PREFERENCE_UTIL, F_PUTIL);
        builder.addModifiers(Modifier.PRIVATE);
        fieldSpecs.add(builder.build());

        return fieldSpecs;
    }

    @Override
    protected List<MethodSpec> getMethods() {
        List<MethodSpec> methodSpecs = new ArrayList<>();

        MethodSpec.Builder builder = MethodSpec.constructorBuilder();
        builder.addModifiers(Modifier.PUBLIC);
        builder.addStatement("$L = $L.$L()", F_PUTIL, ClassNames.PREFERENCE, M_G_PUTIL);
        methodSpecs.add(builder.build());

        //save
        builder = MethodSpec.methodBuilder(M_SAVE).addModifiers(Modifier.PUBLIC);
        builder.addParameter(ParameterSpec.builder(mHostClassName, P_TARGET).build());
        for (VariableEnity variableEnity : mVariableNames) {
            builder.addStatement("$L($L)", getSaveMethodName(variableEnity), P_TARGET);
        }
        methodSpecs.add(builder.build());

        //save every field
        for (VariableEnity variableEnity : mVariableNames) {
            builder = MethodSpec.methodBuilder(getSaveMethodName(variableEnity));
            builder.addModifiers(Modifier.PUBLIC);
            builder.addParameter(ParameterSpec.builder(mHostClassName, P_TARGET).build());
            builder.addStatement("$L.put($L.getClass().getName() + $S, $S, $L.$L)", F_PUTIL, P_TARGET, getKey(variableEnity.getName()), variableEnity.getSimpleClassName(), P_TARGET, variableEnity.getName());
            methodSpecs.add(builder.build());
        }

        //restore
        builder = MethodSpec.methodBuilder(M_RESTORE).addModifiers(Modifier.PUBLIC);
        builder.addParameter(ParameterSpec.builder(mHostClassName, P_TARGET).build());
        for (VariableEnity variableEnity : mVariableNames) {
            builder.addStatement("$L($L)", getRestoreMethodName(variableEnity), P_TARGET);
        }
        methodSpecs.add(builder.build());

        //restore every field
        for (VariableEnity variableEnity : mVariableNames) {
            builder = MethodSpec.methodBuilder(getRestoreMethodName(variableEnity));
            builder.addModifiers(Modifier.PUBLIC);
            builder.addParameter(ParameterSpec.builder(mHostClassName, P_TARGET).build());
            builder.addStatement("$L.$L = ($T) $L.get($L.getClass().getName() + $S, $S)", P_TARGET, variableEnity.getName(), variableEnity.getPoetTypeName(), F_PUTIL, P_TARGET, getKey(variableEnity.getName()), variableEnity.getSimpleClassName());
            methodSpecs.add(builder.build());
        }

        //remove
        builder = MethodSpec.methodBuilder(this.M_REMOVE).addModifiers(Modifier.PUBLIC);
        builder.addParameter(ParameterSpec.builder(mHostClassName, P_TARGET).build());
        for (VariableEnity variableEnity : mVariableNames) {
            builder.addStatement("$L($L)", getRemoveMethodName(variableEnity), P_TARGET);
        }
        methodSpecs.add(builder.build());

        //remove every field
        for (VariableEnity variableEnity : mVariableNames) {
            builder = MethodSpec.methodBuilder(getRemoveMethodName(variableEnity));
            builder.addModifiers(Modifier.PUBLIC);
            builder.addParameter(ParameterSpec.builder(mHostClassName, P_TARGET).build());
            builder.addStatement("$L.$L($L.getClass().getName() + $S)", F_PUTIL, M_REMOVE, P_TARGET, getKey(variableEnity.getName()));
            methodSpecs.add(builder.build());
        }

        return methodSpecs;
    }

    private String getKey(String variableName) {
        return Constant.INNER_LINK + variableName;
    }

    private String getSaveMethodName(VariableEnity variableEnity) {
        return M_SAVE + Character.toUpperCase(variableEnity.getName().charAt(0)) + variableEnity.getName().substring(1);
    }

    private String getRestoreMethodName(VariableEnity variableEnity) {
        return M_RESTORE + Character.toUpperCase(variableEnity.getName().charAt(0)) + variableEnity.getName().substring(1);
    }

    private String getRemoveMethodName(VariableEnity variableEnity) {
        return M_REMOVE + Character.toUpperCase(variableEnity.getName().charAt(0)) + variableEnity.getName().substring(1);
    }
}
