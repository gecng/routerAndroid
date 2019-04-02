package com.gecng.processor.handler

import com.gecng.processor.RouterProcessor
import com.gecng.processor.log.Logger
import com.squareup.kotlinpoet.FileSpec
import java.io.File
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.TypeElement


/**
 *
 * kotlin生成代码 规则：https://github.com/square/kotlinpoet
 */

open class BaseProcessor : AbstractProcessor() {

    companion object {
        const val PACKAGE_NAME = "com.gecng.router"
        const val ROUTE_CLASS_NAME = "com.gecng.routeannotation.IRouteTable"
        const val INTERCEPTOR_CLASS_NAME = "com.gecng.routeannotation.IInterceptorTable"
    }

    lateinit var logger: Logger

    var moduleName = ""

    override fun init(pe: ProcessingEnvironment?) {
        super.init(pe)
        moduleName = pe?.options?.get(RouterProcessor.MODULE_NAME_ARG) ?: ""
        logger = Logger(pe?.messager)

    }


    override fun process(set: MutableSet<out TypeElement>?, roundEnv: RoundEnvironment?): Boolean {
        return false
    }


    fun write2File(fileSpec: FileSpec) {
        val kaptKotlinGeneratedDir = processingEnv.options[RouterProcessor.KAPT_KOTLIN_GENERATED_OPTION_NAME]
        val outputFile = File(kaptKotlinGeneratedDir).apply {
            mkdirs()
        }
        fileSpec.writeTo(outputFile.toPath())
    }

}