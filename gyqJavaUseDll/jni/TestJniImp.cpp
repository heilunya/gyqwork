#include <jni.h>

#include "jni_TestJni.h"

#include <stdio.h>

JNIEXPORT void JNICALL Java_jni_TestJni_display(JNIEnv *env, jobject arg, jstring inString){

       const jbyte* str;

       //从inString字符串取得指向字符串UTF编码的指针

str=(const jbyte*)env->GetStringUTFChars(inString,JNI_FALSE);

       printf("Hello,%s\n",str);

       //通知本地虚拟机本机代码不再需要通过str访问java字符串

       env->ReleaseStringUTFChars(inString,(const char*)str);

       return;

  }