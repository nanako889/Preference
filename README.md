#简介


使用编译时注解,简化 SharedPreference 的使用.支持int,long,float,boolean,String,不支持其他的类型.变量的访问权限不可以是 Private


#使用说明(请参考demo)


必须在Application的onCreate里面初始化'PreferenceUtil.init(context)'

通过'Preference.类名()'可调用'save'，'restore'，'remove'，功能分别是保存到系统SharedPreference，从系统SharedPreference恢复到对象里面对应的变量，删除保存在系统SharedPreference里面的值（对象值此时并没有修改）

通过'Preference.clear()'，清空所有使用'Preference.类名()'保存的所有值


#Gradle


    allprojects {
        repositories {
            jcenter()
            maven { url "https://jitpack.io" }
        }
    }

compile 'com.github.qbaowei.Preference:preference-core:1.3.0'

provided 'com.github.qbaowei.Preference:preference-compiler:1.3.0'


#作者


qbaowei@qq.com


