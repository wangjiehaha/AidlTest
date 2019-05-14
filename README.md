#说明
1.app模块为Settings的源码，所以依赖的jar文件来源于系统编译后获取，settingslib.aar和setupwizardlib.aar
  可以分别通过编译settingslib和setupwizard模块获取
2.编译所需要的 android.jar 需要是打开所有隐藏 api，制作方法为：
（1）编译系统后，生成out\target\common\obj\JAVA_LIBRARIES\framework_intermediates\classes.jar
（2）将classes.jar解压，然后把解压后的类文件添加和替换到sdk/platforms/android-25/android.jar

├─app ---------------------- app模块：settings源码，编译生成apk文件
│  ├─libs ----------------- app模块依赖的库文件，根据Android.mk文件进行配置
│  └─src
│      ├─androidTest
│      └─main
│          ├─java
│          └─res
│
├─settingslib ------------- settingslib模块：编译生成aar文件，提供给app模块使用
│  ├─libs
│  └─src
│      ├─androidTest
│      └─main
│          ├─java
│          └─res
│
├─setupwizard ------------ setupwizard模块：编译生成aar文件，提供给app模块使用
│  ├─libs
│  └─src
│      ├─androidTest
│      └─main
│          ├─java
│          └─res
│
├─systemSign ------------- 系统签名信息
│
└─settings.gradle -------- 配置要编译的模块，建议不要所有模块都编译，会很慢