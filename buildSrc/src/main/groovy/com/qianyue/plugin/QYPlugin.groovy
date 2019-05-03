package com.qianyue.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class QYPlugin implements Plugin<Project>{

    @Override
    void apply(Project project) {
        println("钱跃plugin")
    }

}