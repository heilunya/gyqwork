#include <jni.h>
#include "jni_HelloWorld.h"
#include <stdio.h>

JNIEXPORT void JNICALL Java_jni_HelloWorld_displayHelloWorld(JNIEnv *, jobject)
{
    printf("Hello world!/n");
    return;
}