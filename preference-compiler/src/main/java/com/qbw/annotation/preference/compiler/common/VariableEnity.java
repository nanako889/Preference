package com.qbw.annotation.preference.compiler.common;

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

    private String mName;//变量名
    private TypeMirror mTypeMirror;//类型信息

    private String mType;//经典的包名类名组合(getCanonicalName)

    /**
     * true,表示key为与对应的变量的值绑定
     */
    private boolean isDynamic;

    public VariableEnity(String name, TypeMirror typeMirror) {
        mName = name;
        mTypeMirror = typeMirror;
        mType = mTypeMirror.toString();
        convertType();
        Log.i(String.format("name[%s] type[%s]", mName, mType));
    }

    public String getName() {
        return mName;
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

    public boolean isDynamic() {
        return isDynamic;
    }

    public void setDynamic(boolean dynamic) {
        isDynamic = dynamic;
    }
}
