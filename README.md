# ImDemo
一个简单的IM通信的Demo
Demo开发步骤：

1. 注册网易云开发者，下载SDK，
2. as创建项目
3. 使用as创建的项目的packageName,applicationName在网易云端创建应用，在云端会生成一个
   app Key和 App Secret
4. 通过gradle集成SDK
   首先，在整个工程的 build.gradle 文件中，配置repositories，使用 jcenter 或者 maven ，二选一即可，如下：
       allprojects {
           repositories {
               jcenter() // 或者 mavenCentral()
           }
       }
   第二步，在主工程的 build.gradle 文件中，添加 dependencies。根据自己项目的需求，添加不同的依赖即可。注意：版本号必须一致，这里以3.3.0版本为例：
       android {
          defaultConfig {
              ndk {
                  //设置支持的SO库架构
                  abiFilters "armeabi-v7a", "x86","arm64-v8a","x86_64"
               }
          }
       }
       
       dependencies {
           compile fileTree(dir: 'libs', include: '*.jar')
           // 添加依赖。注意，版本号必须一致。
           // 基础功能 (必需)
           compile 'com.netease.nimlib:basesdk:3.3.0'
           // 音视频需要
           compile 'com.netease.nimlib:avchat:3.3.0'
           // 聊天室需要
           compile 'com.netease.nimlib:chatroom:3.3.0'
           // 实时会话服务需要
           compile 'com.netease.nimlib:rts:3.3.0'
           // 全文检索服务需要
           compile 'com.netease.nimlib:lucene:3.3.0'
       }
   
5. 配置权限和组件
   在 AndroidManifest.xml 中加入以下配置:
       <?xml version="1.0" encoding="utf-8"?>
       <manifest xmlns:android="http://schemas.android.com/apk/res/android"
              package="xxx">
       
           <!-- 权限声明 -->
           <!-- 访问网络状态-->
           <uses-permission android:name="android.permission.INTERNET" />
           <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
           <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
           <!-- 控制呼吸灯，振动器等，用于新消息提醒 -->
           <uses-permission android:name="android.permission.FLASHLIGHT" />
           <uses-permission android:name="android.permission.VIBRATE" />
           <!-- 外置存储存取权限 -->
           <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
           <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
       
           <!-- 多媒体相关 -->
           <uses-permission android:name="android.permission.CAMERA"/>
           <uses-permission android:name="android.permission.RECORD_AUDIO"/>
           <uses-permission android:name="android.permission.READ_PHONE_STATE"/>
       
           <!-- 如果需要实时音视频通话模块，下面的权限也是必须的。否则，可以不加 -->
           <uses-permission android:name="android.permission.BLUETOOTH" />
           <uses-permission android:name="android.permission.BLUETOOTH_ADMIN" />
           <uses-permission android:name="android.permission.MODIFY_AUDIO_SETTINGS"/>
           <uses-permission android:name="android.permission.BROADCAST_STICKY"/>
           <uses-feature android:name="android.hardware.camera" />
           <uses-feature android:name="android.hardware.camera.autofocus" />
           <uses-feature android:glEsVersion="0x00020000" android:required="true" />
       
           <!-- SDK 权限申明, 第三方 APP 接入时，请将 com.netease.nim.demo 替换为自己的包名 -->
           <!-- 和下面的 uses-permission 一起加入到你的 AndroidManifest 文件中。 -->
           <permission
               android:name="com.netease.nim.demo.permission.RECEIVE_MSG"
               android:protectionLevel="signature"/>
           <!-- 接收 SDK 消息广播权限， 第三方 APP 接入时，请将 com.netease.nim.demo 替换为自己的包名 -->
            <uses-permission android:name="com.netease.nim.demo.permission.RECEIVE_MSG"/>
       
           <application
               ...>
               <!-- APP key, 可以在这里设置，也可以在 SDKOptions 中提供。
                   如果 SDKOptions 中提供了，取 SDKOptions 中的值。 -->
               <meta-data
                   android:name="com.netease.nim.appKey"
                   android:value="key_of_your_app" />
       
               <!-- 声明网易云通信后台服务，如需保持后台推送，使用独立进程效果会更好。 -->
               <service 
                   android:name="com.netease.nimlib.service.NimService"
                   android:process=":core"/>
       
              <!-- 运行后台辅助服务 -->
               <service
                   android:name="com.netease.nimlib.service.NimService$Aux"
                   android:process=":core"/>
       
               <!-- 声明网易云通信后台辅助服务 -->
               <service
                   android:name="com.netease.nimlib.job.NIMJobService"
                   android:exported="true"
                   android:permission="android.permission.BIND_JOB_SERVICE"
                   android:process=":core"/>
       
               <!-- 网易云通信SDK的监视系统启动和网络变化的广播接收器，用户开机自启动以及网络变化时候重新登录，
                   保持和 NimService 同一进程 -->
               <receiver android:name="com.netease.nimlib.service.NimReceiver"
                   android:process=":core"
                   android:exported="false">
                   <intent-filter>
                       <action android:name="android.intent.action.BOOT_COMPLETED"/>
                       <action android:name="android.net.conn.CONNECTIVITY_CHANGE"/>
                   </intent-filter>
               </receiver>
       
               <!-- 网易云通信进程间通信 Receiver -->
               <receiver android:name="com.netease.nimlib.service.ResponseReceiver"/>
       
               <!-- 网易云通信进程间通信service -->
               <service android:name="com.netease.nimlib.service.ResponseService"/>
       
               <!-- 安卓保活配置 -->
               <service
                   android:name="com.netease.cosine.core.CosineService"
                   android:process=":cosine">
               </service>
       
               <receiver
                   android:name="com.netease.cosine.target.CosineReceiver"
                   android:exported="true"
                   android:process=":cosine">
               </receiver>
       
               <meta-data
                   android:name="com.netease.cosine.target"
                   android:value=""/>
               <meta-data
                   android:name="com.netease.cosine.target.receiver"
                   android:value="com.netease.nimlib.service.NimReceiver"/>
       
           </application>
       </manifest>
6. 混淆配置
   --------------通过以上的步骤，环境的配置几本完成，接下来需要在代码中做一些初始化的工作------------
7. 在Application中初始化SDK
   在你的程序的 Application 的 onCreate 中，加入网易云通信 SDK 的初始化代码：
       复制public class NimApplication extends Application {
       
           public void onCreate() {
               // ... your codes
       
               // SDK初始化（启动后台服务，若已经存在用户登录信息， SDK 将完成自动登录）
               NIMClient.init(this, loginInfo(), options());
       
               // ... your codes
             
           }
       
          
   
