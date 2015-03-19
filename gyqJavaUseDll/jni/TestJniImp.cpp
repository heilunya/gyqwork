#include <jni.h>

#include "jni_TestJni.h"

#include <stdio.h>

JNIEXPORT void JNICALL Java_jni_TestJni_display(JNIEnv *env, jobject arg, jstring inString){

       const jbyte* str;

       //��inString�ַ���ȡ��ָ���ַ���UTF�����ָ��

str=(const jbyte*)env->GetStringUTFChars(inString,JNI_FALSE);

       printf("Hello,%s\n",str);

       //֪ͨ����������������벻����Ҫͨ��str����java�ַ���

       env->ReleaseStringUTFChars(inString,(const char*)str);

       return;

  }