//头文件
#ifndef SERVICEALIVETEST_NATIVE_LIB_H
#define SERVICEALIVETEST_NATIVE_LIB_H

#include <jni.h>
#include <unistd.h>
#include <string.h>
#include <sys/socket.h>
#include <sys/un.h>
#include <errno.h>
#include <android/log.h>

#define  LOG_TAG    "KEEP_ALIVE"
#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)
#define  LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)

//child是服务端，开始准备，使用的是unix域套接字
void child_prepare_socket();

//创建服务端的socket，并等待客户端的连接
int child_create_socket();

//读取socket内容
void child_listen_socket();
#endif

//**下面是cpp文件**
//socket 实现进程保活，除非卸载
#include "native_lib.h"

const char *PATH = "/data/data/com.qy.j4u.servicealivetest/keep_alive.socket";
const char *userId;
int m_client_fd;
//该函数用来创建服务端socket，并实现监听
extern "C"
JNIEXPORT void JNICALL Java_com_qy_j4u_services_KeepService_createWatcher(
        JNIEnv *env,
        jobject thiz, jstring userId_) {
    userId = env->GetStringUTFChars(userId_, 0);
    LOGI("userId %s", userId);
    //fork 一个新的进程
    pid_t pid = fork();
    //新建进程失败
    if (pid < 0) {
        LOGI("创建进程失败");
    }
    //子进程
    else if (pid == 0) {
        LOGI("pid:%d", getpid());
        child_prepare_socket();
    }
    //父进程返回新建的子进程的pid
    else if (pid > 0) {
        LOGI("父进程返回的%d", pid);
    }
    env->ReleaseStringUTFChars(userId_, userId);
}

void child_prepare_socket() {
    if (child_create_socket()) {
        child_listen_socket();
    }
}

int child_create_socket() {
    LOGI("开始创建服务端socket");
    //AF_LOCAL:本地类型socket，SOCK_STREAM：面向tcp的流，参数0，代表自己选择网络协议
    int sfd = socket(AF_LOCAL, SOCK_STREAM | SOCK_CLOEXEC, 0);
    unlink(PATH);
    struct sockaddr addr{};
    memset(&addr, 0, sizeof(sockaddr));
    addr.sa_family = AF_LOCAL;
    strcpy(addr.sa_data, PATH);
    if (bind(sfd, reinterpret_cast<const sockaddr *>(&addr), sizeof(sockaddr)) < 0) {
        LOGE("服务端绑定socket出错");
        return 0;
    }
    //5:最大连接数量，实际上由系统决定，
    listen(sfd, 5);
    int coonfd = 0;
    //监听客户端的连接
    while (true) {
        //没有客户端请求到来，会阻塞直到一个请求的到来，返回客户端地址
        if ((coonfd = accept(sfd, NULL, NULL)) < 0) {
            if (errno == EINTR) {
                continue;
            } else {
                LOGE("服务端 accept 出错");
                return 0;
            }
        }
        m_client_fd = coonfd;
        LOGI("服务端连接成功，客户端的 fd %d", m_client_fd);
        break;
    }
    return 1;
}

void child_listen_socket() {
    LOGI("服务端监听客户端的消息");
    fd_set fds;
    struct timeval timeout = {10, 0};
    while (true) {
        //使用select I/O多路复用
        //所有位置0
        FD_ZERO(&fds);
        //指定位上的值置为1,
        FD_SET(m_client_fd, &fds);
        //最长等待时间10s，timeout为null，表示一直阻塞直到收到客户端的消息
        int r = select(m_client_fd + 1, &fds, NULL, NULL, &timeout);
        LOGI("读取消息前 select %d", r);
        if (r > 0) {
            char pkg[256] = {0};
            if (FD_ISSET(m_client_fd, &fds)) {
                //如果阻塞结束，说明客户端进程被kill
                int result = read(m_client_fd, pkg, sizeof(pkg));
                LOGE("服务端开始重启服务");

                //api<17
                /*execlp("am", "am", "startservice", "-n"
                               "com.example.chen.servicealivetest/com.example.chen.servicealivetest.KeepService",
                       (char *) NULL);*/

                //>=api17  不过好像两种 api 这种方式都能启动服务
                //执行linux命令：这里是开启一个service
                //am startservice -n com.example.chen.servicealivetest/com.example.chen.servicealivetest.KeepService
                //execlp()会从PATH 环境变量所指的目录中查找符合参数file的文件名, 找到后便执行该文件,
                //然后将第二个以后的参数当做该文件的argv[0]、argv[1]……, 最后一个参数必须用空指针(NULL)作结束。
                execlp("am", "am", "startservice", "--user", userId, "-n"
                                                                     "com.example.chen.servicealivetest/com.example.chen.servicealivetest.KeepService",
                       (char *) NULL);
                break;
            }
        }
    }
}

//该函数用来创建客户端socket，连接服务端
extern "C"
JNIEXPORT void JNICALL
Java_com_qy_j4u_services_KeepService_connectServer(JNIEnv *env, jobject instance) {
    int socketfd;
    struct sockaddr_un addr;
    while (true) {
        socketfd = socket(AF_LOCAL, SOCK_STREAM, 0);
        if (socketfd < 0) {
            LOGE("客户端新建创建失败");
            return;
        }
        memset(&addr, 0, sizeof(sockaddr_un));
        addr.sun_family = AF_LOCAL;
        strcpy(addr.sun_path, PATH);
        if (connect(socketfd, reinterpret_cast<const sockaddr *>(&addr), sizeof(sockaddr_un))) {
            LOGE("客户端连接失败");
            close(socketfd);
            sleep(1);
            continue;
        }
        LOGE("客户端连接成功");
        break;
    }
}