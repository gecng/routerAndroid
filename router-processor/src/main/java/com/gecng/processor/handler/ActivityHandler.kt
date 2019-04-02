package com.gecng.processor.handler

import com.gecng.processor.ActivityFilter
import com.gecng.routeannotation.Route
import com.gecng.routeannotation.RouterInfo
import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.annotation.processing.SupportedSourceVersion
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement

/**
 * 处理路由 注解
 *
 * 生成 路由映射表  url  ->   Activity
 */
@AutoService(Processor::class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
class ActivityHandler : BaseProcessor() {


    override fun process(set: MutableSet<out TypeElement>?, roundEnv: RoundEnvironment?): Boolean {

        val elements = roundEnv?.getElementsAnnotatedWith(Route::class.java)
        if (elements.isNullOrEmpty()) {
            return false
        }
        val activityList = elements.filter { return@filter ActivityFilter.filter(it) }.toMutableList()
        val mapType = LinkedHashMap::class.parameterizedBy(String::class, RouterInfo::class)
        val funBuilder = FunSpec.builder("register")
            .addModifiers(KModifier.OVERRIDE)
            .returns(mapType)
            .addStatement("val routeMap = %T<%T,%T>()", LinkedHashMap::class.java, String::class, RouterInfo::class)

        activityList.forEach { e ->
            val routeAnn = e.getAnnotation(Route::class.java)
            val path = routeAnn.path
            funBuilder.addStatement(
                "routeMap[%S] = %T(%S, %T::class.java)",
                path,
                RouterInfo::class.java,
                path,
                e.asType()
            )
        }

        funBuilder.addStatement("return routeMap")

        val fileSpec = FileSpec.builder("$PACKAGE_NAME.$moduleName", "${moduleName}_router_table")
            .addType(
                TypeSpec.classBuilder("${moduleName}_router_table")
                    .addFunction(funBuilder.build())
                    .addSuperinterface(ClassName.bestGuess(ROUTE_CLASS_NAME))
                    .build()
            )
            .build()
        write2File(fileSpec)
        return false
    }

    /**
     * 选取 带Route注解的元素
     */
    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        val set = mutableSetOf<String>()
        set.add(Route::class.java.canonicalName)
        return set
    }
}