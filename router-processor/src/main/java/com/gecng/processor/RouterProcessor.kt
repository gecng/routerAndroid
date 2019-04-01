package com.gecng.processor

import com.gecng.processor.log.Logger
import com.gecng.routeannotation.Route
import com.gecng.routeannotation.RouterInfo
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import java.io.File
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.TypeElement

/**
 * 处理注解，生成 路由表
 *
 *
 * kotlin生成代码：https://github.com/square/kotlinpoet
 *
 *
 */
@Suppress("SpellCheckingInspection")
class RouterProcessor : AbstractProcessor() {

    companion object {
        //gradle 传参
        const val MODULE_NAME_ARG = "moduleNameArg"
        const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
    }


    private lateinit var logger: Logger
    var moduleName = ""

    override fun init(pe: ProcessingEnvironment?) {
        super.init(pe)
        logger = Logger(pe?.messager)
        //获取 当前被处理的module的 名称
        moduleName = pe?.options?.get(MODULE_NAME_ARG) ?: ""


        logger.N("$moduleName init ============== >")
    }

    /**
     * 处理带有route注解的class，收集path，activity Class ，添加到路由表中
     */
    override fun process(set: MutableSet<out TypeElement>?, roundEnv: RoundEnvironment?): Boolean {

        logger.N("$moduleName process ============== >")

        if (moduleName.isBlank()) {
            return false
        }
        val elements = roundEnv?.getElementsAnnotatedWith(Route::class.java)
        logger.N(roundEnv.toString())
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

//        //todo 尽量减少硬编码
//        FileSpec.builder("com.gecng.router.$moduleName", "${moduleName}_module_router_table")
//            .addType(
//                TypeSpec.classBuilder("${moduleName}_module_router_table")
//                    .addFunction(funBuilder.build())
//                    .addSuperinterface(ClassName.bestGuess("com.gecng.routeannotation.IRouteTable"))
//                    .build()
//            )
//            .build()
//            .writeFile()
        return true
    }

    private fun FileSpec.writeFile() {


        val kaptKotlinGeneratedDir = processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME]
        logger.N("file path =======>  \n        \t\t  $kaptKotlinGeneratedDir\t\t  \n  ===================== ")
        val outputFile = File(kaptKotlinGeneratedDir).apply {
            mkdirs()
        }
        writeTo(outputFile.toPath())
    }


//    /**
//     * 添加需要被处理的注解
//     */
//    override fun getSupportedAnnotationTypes(): MutableSet<String> {
//        val set = LinkedHashSet<String>()
//        set.add(Route::class.java.canonicalName)
//       // set.add(Interceptor::class.java.canonicalName)
//        return set
//    }
}