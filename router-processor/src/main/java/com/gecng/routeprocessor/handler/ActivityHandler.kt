package com.gecng.routeprocessor.handler

import com.gecng.routeannotation.IRouteTable
import com.gecng.routeprocessor.ActivityFilter
import com.gecng.routeannotation.Route
import com.gecng.routeannotation.RouteConst
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
            .addStatement("\n")

        activityList.forEachIndexed { index, e ->
            val routeAnn = e.getAnnotation(Route::class.java)
            val path = routeAnn.path
            val interceptors = routeAnn.interceptors

            funBuilder
                .addStatement("val info%L = %T(%S, %T::class.java)", index, RouterInfo::class.java, path, e.asType())
                .apply {
                    if (!interceptors.isNullOrEmpty()) {
                        //todo 为什么使用 arrayList有问题
//                        addStatement(
//                            "val interceptorArray%L = %T(%L)",
//                            index,
//                            ArrayList::class.parameterizedBy(String::class),
//                            interceptors.size
//                        )

                        addStatement(
                            "val interceptorArray%L = mutableListOf<%T>()",
                            index,
                           String::class
                        )

                        interceptors.forEachIndexed { j, intercept ->
                            addStatement("interceptorArray%L.add(%S)", index, intercept)
                        }

//                        interceptors.forEachIndexed { j, intercept ->
//                            addStatement("interceptorArray%L[%L] = %S", index, j, intercept)
//                        }

                        addStatement("info%1L.interceptors = interceptorArray%1L", index)

                    }

                }
                .addStatement("routeMap[%S] = info%L", path, index)
                .addStatement("\n")


        }

        funBuilder.addStatement("return routeMap")

        val tableClazzName = String.format(RouteConst.ROUTE_TABLE_NAME_FORMAT, moduleName)
        val fileSpec = FileSpec.builder(RouteConst.ROUTE_FILE_DIR, tableClazzName)
            .addType(
                TypeSpec.classBuilder(tableClazzName)
                    .addFunction(funBuilder.build())
                    .addSuperinterface(IRouteTable::class)
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