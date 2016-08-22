package com.qbw.annotation.compiler.preference.common;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.processing.Filer;
import javax.annotation.processing.FilerException;
import javax.lang.model.element.Modifier;

/**
 * Created by Bond on 2016/8/14.
 */

public abstract class AbstractPoet {

    protected final String F_S_INST = "sInst";
    protected final String F_TARGET = "mTarget";
    protected final String M_G_INST = "getInstance";
    protected final String M_SAVE = "save";
    protected final String M_RESTORE = "restore";
    protected final String P_TARGET = "target";

    protected final String SYNCHRONIZED = "synchronized";

    private final String FILE_HEADER = "This code is auto generated, do not modify any code!\n\nAuthor:qbaowei@qq.com";


    protected Filer mFiler;

    protected String mPackageName;
    /**
     * inner class(If 'Test' is a innerclass, so com.test.MainActivity.Test, for'Test' its SimpleClassName is 'Test', ComplexClassName is 'MainActivity.Test')
     */
    protected String mComplexClassName;
    protected String mSimpleClassName;

    protected String mFileClassName;

    protected ClassName mParasiteClassName;

    public AbstractPoet(Filer filer, String packageName, String complexClassName, String simpleClassName) {
        mFiler = filer;
        mPackageName = packageName;
        mComplexClassName = complexClassName;
        mSimpleClassName = simpleClassName;
        mFileClassName = mComplexClassName.replace(".", Constant.INNER_LINK);
        mParasiteClassName = ClassName.get(mPackageName, mFileClassName);
    }

    public void generate() {

        Log.i("Start to generate code for " + mParasiteClassName.toString());
        TypeSpec.Builder bTypeSpec;
        if (TypeSpec.Kind.CLASS == getKind()) {
            bTypeSpec = TypeSpec.classBuilder(mParasiteClassName);
        } else {
            bTypeSpec = TypeSpec.interfaceBuilder(mParasiteClassName);
        }
        bTypeSpec.addModifiers(Modifier.PUBLIC);
        if (null != getParent()) {
            bTypeSpec.addSuperinterface(getParent());
        }
        for (FieldSpec fieldSpec : getFields()) {
            bTypeSpec.addField(fieldSpec);
        }
        for (MethodSpec methodSpec : getMethods()) {
            bTypeSpec.addMethod(methodSpec);
        }
        JavaFile javaFile = JavaFile.builder(mPackageName, bTypeSpec.build()).addFileComment(FILE_HEADER).
                build();
        try {
            javaFile.writeTo(mFiler);
        } catch (FilerException e) {
            Log.w(e);
        } catch (IOException e) {
            Log.w(e);
        }
    }

    protected ClassName getParent() {
        return null;
    }

    protected List<FieldSpec> getFields() {
        return new ArrayList<>();
    }

    protected List<MethodSpec> getMethods() {
        return new ArrayList<>();
    }

    protected TypeSpec.Kind getKind() {
        return TypeSpec.Kind.CLASS;
    }
}
