package com.qbw.annotation.preference.compiler;

import com.qbw.annotation.preference.compiler.common.AbstractPoet;
import com.squareup.javapoet.CodeBlock;

import java.util.Map;

import javax.annotation.processing.Filer;

import static com.qbw.annotation.preference.compiler.common.ClassNames.INIT;
import static com.qbw.annotation.preference.compiler.common.ClassNames.PARASITE_MANAGER;

/**
 * @author QBW
 * @createtime 2016/08/25 16:41
 * @company 9zhitx.com
 * @description
 */


public class InitPoet extends AbstractPoet {

    private Map<String, ParasitePoet> mVariables;

    public InitPoet(Filer filer, Map<String, ParasitePoet> variables) {
        super(filer, INIT.packageName(), INIT.simpleName(), INIT.simpleName());
        mVariables = variables;
    }

    @Override
    protected CodeBlock getStaticBlock() {
        if (null != mVariables && !mVariables.isEmpty()) {
            CodeBlock.Builder b = CodeBlock.builder();
            b.addStatement("$T t = $T.$L()", PARASITE_MANAGER, PARASITE_MANAGER, M_G_INST);
            for (Map.Entry<String, ParasitePoet> h : mVariables.entrySet()) {
                b.addStatement("t.hostBrigeParasite($S, new $T())", h.getKey(), h.getValue().getTargetClassName());
            }
            return b.build();

        }
        return null;
    }
}
