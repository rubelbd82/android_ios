# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Applications/adt/sdk/tools/proguard/proguard-android.txt
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

-keep class com.facebook.** {
   *;
}

-keep class com.squareup.** {
   *;
}

-keep public class * extends com.orm.SugarRecord {
  public protected *;
}
-keep class com.orm.** { *; }

-keep public class app.edikodik.com.edikodik.<ClassName> extends SugarRecord{*;}
-keep public class app.edikodik.com.edikodik.<ClassName> extends SugarApp{*;}

-keep class com.parse.** { *; }

-keep class com.google.** { *; }

-keep class com.squareup.** { *; }

-keep class com.jakewharton.picasso.** { *; }

-keep public class * extends app.edikodik.com.edikodik.entities {
  public protected *;
}

-keep public class * extends app.edikodik.com.edikodik.dao {
  public protected *;
}

-dontwarn com.google.**

-dontwarn com.squareup.**

-dontwarn okio.**