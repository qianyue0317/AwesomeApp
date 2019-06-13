package com.qianyue.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task

class QYPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        println("应用到的工程名称:${project.getName()}")
        Task helloAssemble = project.tasks.create('helloassemble'){
        }
        helloAssemble.doFirst {
            println('hello assemble')
        }
        project.getAllTasks(true).each { Project var1, Set<Task> var2 ->
            var2.each { Task task ->
                println("${var1.name}的task:${task.name}")
            }
            Task assemble = var1.tasks.getByName("assemble")
            assemble.doLast {
                println('assemble执行完毕')
            }
        }

    }


}