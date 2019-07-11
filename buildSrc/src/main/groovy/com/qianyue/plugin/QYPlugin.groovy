package com.qianyue.plugin

import com.android.build.api.transform.*
import com.android.build.gradle.AppExtension
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils
import com.qianyue.transform.MyInject
import org.gradle.api.Plugin
import org.gradle.api.Project

class QYPlugin implements Plugin<Project> {





    @Override
    void apply(Project project) {
        AppExtension android = project.extensions.getByType(AppExtension)
        android.registerTransform(new MyTransform(project))
    }

    static class MyTransform extends Transform {

        private Project mProject


        MyTransform(Project project1) {
            this.mProject = project1
        }


        @Override
        String getName() {
            return "MyTrans"
        }

        @Override
        Set<QualifiedContent.ContentType> getInputTypes() {
            return TransformManager.CONTENT_CLASS
        }

        @Override
        Set<? super QualifiedContent.Scope> getScopes() {
            return TransformManager.SCOPE_FULL_PROJECT
        }

        @Override
        boolean isIncremental() {
            return false
        }

        @Override
        void transform(Context context, Collection<TransformInput> inputs, Collection<TransformInput> referencedInputs, TransformOutputProvider outputProvider, boolean isIncremental) throws IOException, TransformException, InterruptedException {
            // Transform的inputs有两种类型，一种是目录，一种是jar包，要分开遍历
            inputs.each { TransformInput input ->
                //对类型为“文件夹”的input进行遍历
                input.directoryInputs.each { DirectoryInput directoryInput ->
                    //文件夹里面包含的是我们手写的类以及R.class、BuildConfig.class以及R$XXX.class等
                    MyInject.injectDir(directoryInput.file.absolutePath,"com\\qy\\j4u")
//                    MyInject.processAndroidPackage(directoryInput.file.absolutePath,"android")

                    // 获取output目录
                    def dest = outputProvider.getContentLocation(directoryInput.name,
                            directoryInput.contentTypes, directoryInput.scopes,
                            Format.DIRECTORY)

                    // 将input的目录复制到output指定目录
                    FileUtils.copyDirectory(directoryInput.file, dest)
                }
                //对类型为jar文件的input进行遍历
                input.jarInputs.each { JarInput jarInput ->

                    //jar文件一般是第三方依赖库jar文件

                    // 重命名输出文件（同目录copyFile会冲突）
                    def jarName = jarInput.name
                    if (jarName.endsWith(".jar")) {
                        jarName = jarName.substring(0, jarName.length() - 4)
                    }
                    //生成输出路径
                    def dest = outputProvider.getContentLocation(jarName + "13614564",
                            jarInput.contentTypes, jarInput.scopes, Format.JAR)
                    //将输入内容复制到输出
                    FileUtils.copyFile(jarInput.file, dest)
                }
            }
        }
    }

}