package com.gecng.processor.handler

import com.gecng.routeannotation.Interceptor
import com.gecng.routeannotation.InterceptorInfo
import com.gecng.routeannotation.Route
import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.annotation.processing.SupportedSourceVersion
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement

/**
 * 处理拦截器注解
 * 获取当前的module name， 生成拦截映射表
 *
 *
 * 生成 url ->  Interceptor
 */

@AutoService(Processor::class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
class InterceptorHandler : BaseProcessor() {

    override fun init(pe: ProcessingEnvironment?) {
        super.init(pe)
        logger.W("Interceptor begin to init")
    }


    override fun process(set: MutableSet<out TypeElement>?, roundEnv: RoundEnvironment?): Boolean {


        val elements = roundEnv?.getElementsAnnotatedWith(Interceptor::class.java)
        logger.W("Interceptor begin to process \t\n\n\n $elements ")
        if (elements.isNullOrEmpty()) {
            logger.W("Interceptor begin to end")
            return false
        }
        logger.W("Interceptor begin to process")
        val activityList = elements.filter { return@filter true }.toMutableList()
        val mapType = LinkedHashMap::class.parameterizedBy(String::class, InterceptorInfo::class)
        val funBuilder = FunSpec.builder("register")
            .addModifiers(KModifier.OVERRIDE)
            .returns(mapType)
            .addStatement(
                "val interceptorMap = %T<%T,%T>()",
                LinkedHashMap::class.java,
                String::class,
                InterceptorInfo::class
            )

        activityList.forEach { e ->
            val routeAnn = e.getAnnotation(Interceptor::class.java)
            val path = routeAnn.name
            funBuilder.addStatement(
                "interceptorMap[%S] = %T(%S, %T::class.java)",
                path,
                InterceptorInfo::class.java,
                path,
                e.asType()
            )
        }

        funBuilder.addStatement("return interceptorMap")

        FileSpec.builder("$PACKAGE_NAME.$moduleName", "${moduleName}_module_interceptor_table")
            .addType(
                TypeSpec.classBuilder("${moduleName}_module_interceptor_table")
                    .addFunction(funBuilder.build())
                    .addSuperinterface(ClassName.bestGuess("com.gecng.routeannotation.IInterceptorTable"))
                    .build()
            )
            .build().writeFile()
        return true
    }


    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        val set = mutableSetOf<String>()
        logger.W("Interceptor begin to getSupportedAnnotationTypes")
        set.add(Interceptor::class.java.canonicalName)
        set.add(Route::class.java.canonicalName)
        return set
    }


}