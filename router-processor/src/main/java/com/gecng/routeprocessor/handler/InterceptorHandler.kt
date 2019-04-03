package com.gecng.routeprocessor.handler

import com.gecng.routeannotation.IInterceptorTable
import com.gecng.routeannotation.Interceptor
import com.gecng.routeannotation.InterceptorInfo
import com.gecng.routeannotation.RouteConst
import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
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

    override fun process(set: MutableSet<out TypeElement>?, roundEnv: RoundEnvironment?): Boolean {


        val elements = roundEnv?.getElementsAnnotatedWith(Interceptor::class.java)
        if (elements.isNullOrEmpty()) {
            return false
        }
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
            funBuilder
                .addStatement("val info = %T(%S, %T::class.java)", InterceptorInfo::class.java, path, e.asType())
                .addStatement("interceptorMap[%S] = info", path)

        }

        funBuilder.addStatement("return interceptorMap")

        val tableClazzName = String.format(RouteConst.INTERCEPTOR_NAME_FORMAT, moduleName)
        val fileSpec = FileSpec.builder(RouteConst.INTERCEPTOR_FILE_DIR, tableClazzName)
            .addType(
                TypeSpec.classBuilder(tableClazzName)
                    .addFunction(funBuilder.build())
                    //继承 父类
                    .addSuperinterface(IInterceptorTable::class)
                    .build()
            )
            .build()
        write2File(fileSpec)
        return true
    }


    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        val set = mutableSetOf<String>()
        set.add(Interceptor::class.java.canonicalName)
        return set
    }


}