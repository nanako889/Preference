#简介


使用编译时注解,简化SharedPreference的使用.支持int,long,float,boolean,String,不支持其他的类型.变量的访问权限不可以是'Private'


#使用说明(请参考demo)


1.在工程Application的onCreate函数中调用'Preference.init(this)'(说明:Preference是编译时生成的类文件,所以你必须至少标注了一个变量'@SharedPreference')

2.在类中(如果不是'Activity',需要'implements IHost'-一个空接口,只是为了混淆的时候不混淆这些类),对变量标注'@SharedPreference',调用'Preference.save(对象)'保存这个类对象中'@SharedPreference'变量的值到系统的SharedPreference,同理'Preference.restore(对象)'是恢复系统SharedPreference的值到具体的变量

3.'Preference.clear(对象)',清除保存在系统SharedPreference中的值(注意:并没有修改对象变量的值,如果也需要重置,clear之后调用restore就可以了)

4.'Preference.clearAll',清楚所有使用'@SharedPreference'变量的值(注意:并没有修改对象变量的值,如果也需要重置,clearAll之后调用restore就可以了)


#混淆配置


-keep public class com.qbw.annotation.preference.SharedPreference {*;}
-keepclasseswithmembers class * implements com.qbw.annotation.preference.core.IHost {}


#Gradle


allprojects {
    repositories {
        jcenter()
        maven { url "https://jitpack.io" }
    }
}

compile 'com.github.qbaowei.Preference:preference-core:1.0.0'
provided 'com.github.qbaowei.Preference:preference-compiler:1.0.0'


#Author


qbaowei@qq.com


