package com.qbw.annotation.compiler.preference;

import com.qbw.annotation.compiler.preference.common.AbstractPoet;
import com.qbw.annotation.compiler.preference.common.Constant;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;

import static com.qbw.annotation.compiler.preference.common.ClassNames.CONTEXT;
import static com.qbw.annotation.compiler.preference.common.ClassNames.HASHMAP;
import static com.qbw.annotation.compiler.preference.common.ClassNames.MAP;
import static com.qbw.annotation.compiler.preference.common.ClassNames.PREFERENCE;
import static com.qbw.annotation.compiler.preference.common.ClassNames.PREFERENCE_UTIL;
import static com.qbw.annotation.compiler.preference.common.ClassNames.SHARED_PREFERENCE;
import static com.qbw.annotation.compiler.preference.common.ClassNames.SHARED_PREFERENCE_EDITOR;
import static com.qbw.annotation.compiler.preference.common.ClassNames.STRING;
import static com.qbw.annotation.compiler.preference.common.Constant.SN_INTEGER;
import static com.squareup.javapoet.ClassName.OBJECT;
import static com.squareup.javapoet.TypeName.INT;

/**
 * @author QBW
 * @createtime 2016/08/17 15:20
 * @company 9zhitx.com
 * @description
 */


public class PreUtilPoet extends AbstractPoet {

    protected final String F_S_UTILMAP = "sUtilMap";
    protected final String M_PUT = "put";
    protected final String M_GET = "get";
    protected final String F_SP = "mSP";
    protected final String P_KEY = "key";
    protected final String P_PACKAGENAME = "packageName";
    protected final String P_VALUECLASSNAME = "valueClassName";
    protected final String P_VALUE = "value";

    public PreUtilPoet(Filer filer) {
        super(filer, PREFERENCE_UTIL.packageName(), PREFERENCE_UTIL.simpleName(), PREFERENCE_UTIL.simpleName());
    }

    @Override
    protected List<FieldSpec> getFields() {
        List<FieldSpec> fieldSpecs = new ArrayList<>();

        FieldSpec fieldSpec = FieldSpec.builder(MAP, F_S_UTILMAP).addModifiers(Modifier.PUBLIC, Modifier.STATIC).initializer("new $T()", HASHMAP).build();
        fieldSpecs.add(fieldSpec);

        fieldSpec = FieldSpec.builder(SHARED_PREFERENCE, F_SP).addModifiers(Modifier.PRIVATE).build();
        fieldSpecs.add(fieldSpec);

        return fieldSpecs;
    }

    @Override
    protected List<MethodSpec> getMethods() {
        List<MethodSpec> methodSpecs = new ArrayList<>();

        MethodSpec methodSpec = MethodSpec.constructorBuilder().addModifiers(Modifier.PRIVATE).addParameter(ParameterSpec.builder(STRING, P_KEY).build()).
                addStatement("$L = $T.getContext().getSharedPreferences($L, $T.MODE_PRIVATE)", F_SP, PREFERENCE, P_KEY, CONTEXT).
                build();
        methodSpecs.add(methodSpec);

        methodSpec = MethodSpec.methodBuilder(M_G_INST).
                addModifiers(Modifier.PUBLIC, Modifier.STATIC).returns(mParasiteClassName).
                addParameter(ParameterSpec.builder(STRING, P_PACKAGENAME).build()).
                addStatement("$T $L = $L + $S + $S", STRING, P_KEY, P_PACKAGENAME, Constant.LINK, Constant.SUFFIX).
                addStatement("$T t = null", mParasiteClassName).
                beginControlFlow("if (null == $L.get($L))", F_S_UTILMAP, P_KEY).
                beginControlFlow("$L($L)", SYNCHRONIZED, F_S_UTILMAP).
                beginControlFlow("if (null == $L.get($L))", F_S_UTILMAP, P_KEY).
                addStatement("t = new $T($L)", mParasiteClassName, P_KEY).
                addStatement("$L.put($L, t)", F_S_UTILMAP, P_KEY).
                endControlFlow().
                endControlFlow().
                endControlFlow().
                addStatement("return null == t ? ($T) $L.get($L) : t", PREFERENCE_UTIL, F_S_UTILMAP, P_KEY).
                build();
        methodSpecs.add(methodSpec);

        methodSpec = MethodSpec.methodBuilder(M_PUT).addModifiers(Modifier.PUBLIC).
                addParameter(ParameterSpec.builder(STRING, P_KEY).build()).
                addParameter(ParameterSpec.builder(STRING, P_VALUECLASSNAME).build()).
                addParameter(ParameterSpec.builder(OBJECT, P_VALUE).build()).
                addStatement("$T e = $L.edit()", SHARED_PREFERENCE_EDITOR, F_SP).
                beginControlFlow("if ($S.equals($L))", SN_INTEGER, P_VALUECLASSNAME).

                beginControlFlow("if (null == $L)", P_VALUE).
                addStatement("e.putInt($L, 0)", P_KEY).
                nextControlFlow("else").
                addStatement("$T v = ($T) $L", INT, INT, P_VALUE).
                addStatement("e.putInt($L, v)", P_KEY).
                endControlFlow().

                endControlFlow().

                addStatement("e.commit()").
                build();
        methodSpecs.add(methodSpec);

        methodSpec = MethodSpec.methodBuilder(M_GET).addModifiers(Modifier.PUBLIC).returns(OBJECT).
                addParameter(ParameterSpec.builder(STRING, P_KEY).build()).addParameter(ParameterSpec.builder(STRING, P_VALUECLASSNAME).build()).
                beginControlFlow("if ($S.equals($L))", SN_INTEGER, P_VALUECLASSNAME).
                addStatement("return $L.getInt($L, 0)", F_SP, P_KEY).
                endControlFlow().
                addStatement("return null").
                build();
        methodSpecs.add(methodSpec);

        return methodSpecs;
    }
}
