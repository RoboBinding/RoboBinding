# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\windows7\android-studio\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

-dontobfuscate
-dontoptimize

-keep class org.robobinding.** { *; }
-keep interface org.robobinding.** { *; }
-keep enum org.robobinding.** { *; }
-keepparameternames
-keepattributes *Annotation,Signature,Exceptions,InnerClasses,EnclosingMethod,SourceFile,LineNumberTable,Deprecated

-dontwarn com.google.common.collect.MinMaxPriorityQueue
-dontwarn com.google.common.**
-dontwarn javax.microedition.**
-dontwarn org.apache.http.**