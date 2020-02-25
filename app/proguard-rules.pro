-optimizationpasses 5

#When not preverifing in a case-insensitive filing system, such as Windows. Because this tool unpacks your processed jars, you should then use:
-dontusemixedcaseclassnames

#Specifies not to ignore non-public library classes. As of version 4.5, this is the default setting
-dontskipnonpubliclibraryclasses

#Preverification is irrelevant for the dex compiler and the Dalvik VM, so we can switch it off with the -dontpreverify option.
-dontpreverify

#Specifies to write out some more information during processing. If the program terminates with an exception, this option will print out the entire stack trace, instead of just the exception message.
-verbose

#The -optimizations option disables some arithmetic simplifications that Dalvik 1.0 and 1.5 can't handle. Note that the Dalvik VM also can't handle aggressive overloading (of static fields).
#To understand or change this check http://proguard.sourceforge.net/index.html#/manual/optimizations.html
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

#To repackage classes on a single package
#-repackageclasses ''

#Uncomment if using annotations to keep them.
-keepattributes *Annotation*

#Keep classes that are referenced on the AndroidManifest
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class com.android.vending.licensing.ILicensingService


#To remove debug logs:
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
}



#Maintain java native methods
-keepclasseswithmembernames class * {
    native <methods>;
}

#To maintain custom components names that are used on layouts XML.
#Uncomment if having any problem with the approach below
#-keep public class custom.components.package.and.name.**

#To maintain custom components names that are used on layouts XML:
-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

#Maintain enums
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

#To keep parcelable classes (to serialize - deserialize objects to sent through Intents)
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

#Keep the R
-keepclassmembers class **.R$* {
    public static <fields>;
}

###### ADDITIONAL OPTIONS NOT USED NORMALLY

#To keep callback calls. Uncomment if using any
#http://proguard.sourceforge.net/index.html#/manual/examples.html#callback
#-keep class mypackage.MyCallbackClass {
#   void myCallbackMethod(java.lang.String);
#}

#Uncomment if using Serializable
-keepclassmembers class * implements java.io.Serializable {
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# ButterKnife 7
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

#appcompat support
-keep public class android.support.v7.widget.** { *; }
-keep public class android.support.v7.internal.widget.** { *; }
-keep public class android.support.v7.internal.view.menu.** { *; }

-keep public class * extends android.support.v4.view.ActionProvider {
    public <init>(android.content.Context);
}

#design support
-dontwarn android.support.design.**
-keep class android.support.design.** { *; }
-keep interface android.support.design.** { *; }
-keep public class android.support.design.R$* { *; }


# Retrofit 2.X
## https://square.github.io/retrofit/ ##

-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

#Gson & hawk
-keep class com.google.gson.** { *; }
-keep class org.sqlite.** { *; }
-keep class org.sqlite.database.** { *; }


## GSON 2.2.4 specific rules ##

# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

-keepattributes EnclosingMethod

# Gson specific classes
#-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }


# Application classes that will be serialized/deserialized over Gson
-keep class com.app.m1remit.model.** { *; }
##---------------End: proguard configuration for Gson  ----------


#saripaar
-keep class com.mobsandgeeks.saripaar.** {*;}

-keep class me.zhanghai.android.materialprogressbar.** { *; }

#proguard
-dontwarn com.squareup.okhttp.**


# Crashlytics 2.+
-keep class com.crashlytics.** { *; }
-dontwarn com.crashlytics.**

-keep class com.crashlytics.** { *; }
-keep class com.crashlytics.android.**
-keepattributes SourceFile, LineNumberTable, *Annotation*

# If you are using custom exceptions, add this line so that custom exception types are skipped during obfuscation:
-keep public class * extends java.lang.Exception

# For Fabric to properly de-obfuscate your crash reports, you need to remove this line from your ProGuard config:
#-printmapping mapping.txt

-dontwarn rx.**
-dontwarn okio.**

-keepattributes *Annotation*,EnclosingMethod,Signature
-dontwarn com.squareup.okhttp.**


-dontwarn javax.annotation.**
-dontwarn javax.inject.**
-dontwarn sun.misc.Unsafe

#jsoup rules
-keep public class org.jsoup.** {
    public *;
}


#Calliography
-keep class uk.co.chrisjenx.calligraphy.* { *; }
-keep class uk.co.chrisjenx.calligraphy.*$* { *; }


#Material library
-keep class com.rey.material.** { *; }
-dontwarn com.rey.material.**


#apache
-dontwarn org.apache.commons.**
-keep class org.apache.http.** { *; }
-dontwarn org.apache.http.**

#Barcode
-keep class me.dm7.barcodescanner.** {
  *;
}
-keep class net.sourceforge.zbar.** {
  *;
}


#Data binding
-keep public class **.BR { public *; }
-keep public class **.BR$* { public *; }
-keepclassmembers class **.BR$* {
    public static <fields>;
}
-keepclassmembers class **.R$* {
    public static <fields>;
}
-keep class * extends android.databinding.** { *; }
-keep class android.databinding.** { *; }

#google gms
-keep class com.google.android.gms.** { *; }
-dontwarn com.google.android.gms.**

#gson
-keep class com.google.**
-dontwarn com.google.**

-keep class eparchi.model.** { *; }

# Keep the support library
-keep class android.support.v4.** { *; }
-keep interface android.support.v4.** { *; }


-keepattributes javax.xml.bind.annotation.*
-keepattributes javax.annotation.processing.*
-keepclassmembers class * extends java.lang.Enum { *; }
-keepclasseswithmembernames class android.**
-keepclasseswithmembernames interface android.**
-dontwarn android.databinding.**

#okhttp3
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**
-dontwarn org.conscrypt.**

#Android-Iconics
-keep class .R
-keep class **.R$* {
    <fields>;
}

#Bumptech Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

#cropper
-keep class android.support.v7.widget.** { *; }

#PROGUARD RULE FOR FIREBASE CRASHLYTICS
#----------------------------------------------------------------------
# https://firebase.google.com/docs/crashlytics/get-deobfuscated-reports
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable
-keep public class * extends java.lang.Exception


# To let Crashlytics automatically upload the ProGuard or DexGuard mapping file, remove this line from the config file
# -printmapping mapping.txt

-keep class com.crashlytics.** { *; }
-dontwarn com.crashlytics.**


#slidinguppanel
-dontwarn com.sothree.**
-keep class com.sothree.**
-keep interface com.sothree.**
