package com.qbw.annotation.compiler.preference.common;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

import javax.lang.model.type.TypeMirror;

/**
 * @author QBW
 * @createtime 2016/08/15 18:47
 * @company 9zhitx.com
 * @description
 */


public class VariableEnity {

    private String mName;

    private String mType;//经典的包名类名组合(getCanonicalName)

    private TypeMirror mTypeMirror;

    public VariableEnity(String name, TypeMirror typeMirror) {
        mName = name;
        mTypeMirror = typeMirror;
        mType = mTypeMirror.toString();
        convertType();
    }

    public String getName() {
        return mName;
    }

    public String getType() {
        return mType;
    }

    public String getSimpleClassName() {
        return mType.substring(mType.lastIndexOf(".") + 1);
    }

    public TypeName getPoetTypeName() {
        return ClassName.get(mTypeMirror);
    }

    private void convertType() {
        if ("int".equals(mType)) {
            mType = "java.lang.Integer";
        } else if ("long".equals(mType)) {
            mType = "java.lang.Long";
        } else if ("float".equals(mType)) {
            mType = "java.lang.Float";
        } else if ("boolean".equals(mType)) {
            mType = "java.lang.Boolean";
        }
    }

}
