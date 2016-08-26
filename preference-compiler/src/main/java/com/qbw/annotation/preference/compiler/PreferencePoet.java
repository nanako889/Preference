package com.qbw.annotation.preference.compiler;

import com.qbw.annotation.preference.compiler.common.AbstractPoet;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;

import static com.qbw.annotation.preference.compiler.common.ClassNames.ACTIVITY;
import static com.qbw.annotation.preference.compiler.common.ClassNames.CONTEXT;
import static com.qbw.annotation.preference.compiler.common.ClassNames.IPREFERENCE_FLAG;
import static com.qbw.annotation.preference.compiler.common.ClassNames.PARASITE_MANAGER;
import static com.qbw.annotation.preference.compiler.common.ClassNames.PREFERENCE;
import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;

/**
 * @author QBW
 * @createtime 2016/08/25 15:49
 * @company 9zhitx.com
 * @description
 */


public class PreferencePoet extends AbstractPoet {

    protected final String F_CONTEXT = "sContext";
    protected final String P_CONTEXT = "context";
    protected final String F_PARASITE_MANAGER = "sParasiteManager";

    protected final String M_INIT = "init";
    protected final String M_GET_CONTEXT = "getContext";
    protected final String M_CLEAR_ALL = "clearAll";

    private Map<String, ParasitePoet> mVariables;

    public PreferencePoet(Filer filer, Map<String, ParasitePoet> variables) {
        super(filer, PREFERENCE.packageName(), PREFERENCE.simpleName(), PREFERENCE.simpleName());
        mVariables = variables;
    }

    @Override
    protected List<FieldSpec> getFields() {
        List<FieldSpec> fieldSpecs = new ArrayList<>();

        FieldSpec fieldSpec = FieldSpec.builder(CONTEXT, F_CONTEXT).addModifiers(Modifier.PRIVATE, STATIC).build();
        fieldSpecs.add(fieldSpec);

        fieldSpec = FieldSpec.builder(PARASITE_MANAGER, F_PARASITE_MANAGER).addModifiers(Modifier.PRIVATE, STATIC).build();
        fieldSpecs.add(fieldSpec);

        return fieldSpecs;
    }

    @Override
    protected List<MethodSpec> getMethods() {
        List<MethodSpec> methodSpecs = new ArrayList<>();

        MethodSpec.Builder bmethodSpec = MethodSpec.methodBuilder(M_INIT).addModifiers(PUBLIC, STATIC).
                addParameter(ParameterSpec.builder(CONTEXT, P_CONTEXT).build()).
                addStatement("$L = $L", F_CONTEXT, P_CONTEXT).
                addStatement("$L = $T.$L($L)", F_PARASITE_MANAGER, PARASITE_MANAGER, M_G_INST, P_CONTEXT);
        if (null != mVariables && !mVariables.isEmpty()) {
            for (Map.Entry<String, ParasitePoet> h : mVariables.entrySet()) {
                bmethodSpec.addStatement("$L.hostBrigeParasite($S, new $T())", F_PARASITE_MANAGER, h.getKey(), h.getValue().getTargetClassName());
            }
        }
        methodSpecs.add(bmethodSpec.build());

        MethodSpec methodSpec = MethodSpec.methodBuilder(M_GET_CONTEXT).addModifiers(PUBLIC, STATIC).returns(CONTEXT).
                addStatement("return $L", F_CONTEXT).
                build();
        methodSpecs.add(methodSpec);

        methodSpec = MethodSpec.methodBuilder(M_SAVE).addModifiers(PUBLIC, STATIC).addParameter(ParameterSpec.builder(ACTIVITY, P_TARGET).build()).
                addStatement("$L.$L($L)", F_PARASITE_MANAGER, M_SAVE, P_TARGET).
                build();
        methodSpecs.add(methodSpec);

        methodSpec = MethodSpec.methodBuilder(M_SAVE).addModifiers(PUBLIC, STATIC).addParameter(ParameterSpec.builder(IPREFERENCE_FLAG, P_TARGET).build()).
                addStatement("$L.$L($L)", F_PARASITE_MANAGER, M_SAVE, P_TARGET).
                build();
        methodSpecs.add(methodSpec);

        methodSpec = MethodSpec.methodBuilder(M_RESTORE).addModifiers(PUBLIC, STATIC).addParameter(ParameterSpec.builder(ACTIVITY, P_TARGET).build()).
                addStatement("$L.$L($L)", F_PARASITE_MANAGER, M_RESTORE, P_TARGET).
                build();
        methodSpecs.add(methodSpec);

        methodSpec = MethodSpec.methodBuilder(M_RESTORE).addModifiers(PUBLIC, STATIC).addParameter(ParameterSpec.builder(IPREFERENCE_FLAG, P_TARGET).build()).
                addStatement("$L.$L($L)", F_PARASITE_MANAGER, M_RESTORE, P_TARGET).
                build();
        methodSpecs.add(methodSpec);

        methodSpec = MethodSpec.methodBuilder(M_CLEAR).addModifiers(PUBLIC, STATIC).addParameter(ParameterSpec.builder(ACTIVITY, P_TARGET).build()).
                addStatement("$L.$L($L)", F_PARASITE_MANAGER, M_CLEAR, P_TARGET).
                build();
        methodSpecs.add(methodSpec);

        methodSpec = MethodSpec.methodBuilder(M_CLEAR).addModifiers(PUBLIC, STATIC).addParameter(ParameterSpec.builder(IPREFERENCE_FLAG, P_TARGET).build()).
                addStatement("$L.$L($L)", F_PARASITE_MANAGER, M_CLEAR, P_TARGET).
                build();
        methodSpecs.add(methodSpec);

        methodSpec = MethodSpec.methodBuilder(M_CLEAR_ALL).addModifiers(PUBLIC, STATIC).
                addStatement("$L.$L()", F_PARASITE_MANAGER, M_CLEAR_ALL).
                build();
        methodSpecs.add(methodSpec);

        return methodSpecs;
    }

//    @Override
//    protected CodeBlock getStaticBlock() {
//        if (null != mVariables && !mVariables.isEmpty()) {
//            CodeBlock.Builder b = CodeBlock.builder();
//            b.addStatement("$T t = $T.$L($L)", PARASITE_MANAGER, PARASITE_MANAGER, M_G_INST, F_CONTEXT);
//            for (Map.Entry<String, ParasitePoet> h : mVariables.entrySet()) {
//                b.addStatement("t.hostBrigeParasite($S, new $T())", h.getKey(), h.getValue().getTargetClassName());
//            }
//            return b.build();
//
//        }
//        return null;
//    }
}
