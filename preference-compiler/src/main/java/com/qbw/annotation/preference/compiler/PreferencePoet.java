package com.qbw.annotation.preference.compiler;

import com.qbw.annotation.preference.compiler.common.CommonPoet;
import com.squareup.javapoet.MethodSpec;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;

import static com.qbw.annotation.preference.compiler.common.ClassNames.PREFERENCE;
import static com.qbw.annotation.preference.compiler.common.ClassNames.PREFERENCE_UTIL;
import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;

/**
 * @author QBW
 * @createtime 2016/08/25 15:49
 */


public class PreferencePoet extends CommonPoet {

    protected final String F_CONTEXT = "sContext";
    protected final String P_CONTEXT = "context";

    protected final String M_INIT = "init";
    protected final String M_CLEAR = "clear";

    private Map<String, ParasitePoet> mVariables;

    public PreferencePoet(Filer filer, Map<String, ParasitePoet> variables) {
        super(filer, PREFERENCE.packageName(), PREFERENCE.simpleName(), PREFERENCE.packageName(), PREFERENCE.simpleName());
        mVariables = variables;
    }

//    @Override
//    protected List<FieldSpec> getFields() {
//        List<FieldSpec> fieldSpecs = new ArrayList<>();
//
//        FieldSpec fieldSpec = FieldSpec.builder(CONTEXT, F_CONTEXT).addModifiers(Modifier.PRIVATE, STATIC).build();
//        fieldSpecs.add(fieldSpec);
//
//        fieldSpec = FieldSpec.builder(PREFERENCE_UTIL, F_PUTIL).addModifiers(Modifier.PRIVATE, STATIC).build();
//        fieldSpecs.add(fieldSpec);
//
//        return fieldSpecs;
//    }

    @Override
    protected List<MethodSpec> getMethods() {
        List<MethodSpec> methodSpecs = new ArrayList<>();

        //init函数
//        MethodSpec.Builder bmethodSpec = MethodSpec.methodBuilder(M_INIT).addModifiers(PUBLIC, STATIC);
//        bmethodSpec.addParameter(ParameterSpec.builder(CONTEXT, P_CONTEXT).build());
//        bmethodSpec.addStatement("$L = $L", F_CONTEXT, P_CONTEXT);
//        bmethodSpec.addStatement("$L = $L.$L()", F_PUTIL, PREFERENCE_UTIL, M_G_INST);
//        methodSpecs.add(bmethodSpec.build());


        if (null != mVariables && !mVariables.isEmpty()) {
            for (Map.Entry<String, ParasitePoet> h : mVariables.entrySet()) {
                MethodSpec.Builder gms = MethodSpec.methodBuilder(h.getValue().getHostComplexClassName().replace(".", ""));
                gms.addModifiers(Modifier.STATIC, Modifier.PUBLIC);
                gms.returns(h.getValue().getGenerateClassName());
                gms.addStatement("return new $T()", h.getValue().getGenerateClassName());
                methodSpecs.add(gms.build());
            }
        }

//        MethodSpec methodSpec = MethodSpec.methodBuilder(M_G_CONTEXT).addModifiers(PUBLIC, STATIC).returns(CONTEXT).
//                addStatement("return $L", F_CONTEXT).
//                build();
//        methodSpecs.add(methodSpec);

        MethodSpec methodSpec = MethodSpec.methodBuilder(M_CLEAR).addModifiers(PUBLIC, STATIC).
                addStatement("$L.$L().$L()", PREFERENCE_UTIL, M_G_INST, M_CLEAR).
                build();
        methodSpecs.add(methodSpec);

        methodSpec = MethodSpec.methodBuilder(M_G_PUTIL).addModifiers(STATIC, PUBLIC).addStatement("return $L.$L()", PREFERENCE_UTIL, M_G_INST).returns(PREFERENCE_UTIL).build();
        methodSpecs.add(methodSpec);

        return methodSpecs;
    }
}
