#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_qy_j4u_TestMyAnnotation_getContent(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
