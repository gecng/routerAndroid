package com.gecng.routeprocessor.handler

import com.gecng.routeprocessor.log.Logger
import com.squareup.kotlinpoet.FileSpec
import java.io.File
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.ProcessingEnvironment


/**
 *
 * kotlin生成代码 规则：https://github.com/square/kotlinpoet
 */

abstract class BaseProcessor : AbstractProcessor() {

    companion object {
        //传参
        const val MODULE_NAME_ARG = "moduleNameArg"
        //代码生成 目录
        const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"

    }

    lateinit var logger: Logger

    var moduleName = ""

    override fun init(pe: ProcessingEnvironment?) {
        super.init(pe)
        moduleName = pe?.options?.get(MODULE_NAME_ARG) ?: ""
        logger = Logger(pe?.messager)

    }

    /**
     * 写文件
     */
    fun write2File(fileSpec: FileSpec) {
        val kaptKotlinGeneratedDir = processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME]
        val outputFile = File(kaptKotlinGeneratedDir).apply {
            mkdirs()
        }
        fileSpec.writeTo(outputFile.toPath())
    }

}