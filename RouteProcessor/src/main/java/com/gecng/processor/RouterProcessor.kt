package com.gecng.processor

import com.gecng.routeannotation.Route
import com.gecng.routeannotation.RouterInfo
import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeSpec
import java.io.File
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic


@AutoService(Processor::class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
class RouterProcessor : AbstractProcessor() {

    companion object {
        //gradle 传参
        const val MODULE_NAME_ARG = "moduleNameArg"
        const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
    }


    var messager: Messager? = null
    var moduleName = ""

    override fun init(pe: ProcessingEnvironment?) {
        super.init(pe)
        messager = pe?.messager

        val options = pe?.options
        moduleName = options?.get(MODULE_NAME_ARG) ?: ""
        messager?.printMessage(Diagnostic.Kind.WARNING, "$moduleName init ============== >")
    }

    /**
     * 处理带有route注解的class，收集path，activity Class ，添加到路由表中
     */
    override fun process(set: MutableSet<out TypeElement>?, roundEnv: RoundEnvironment?): Boolean {

        messager?.printMessage(Diagnostic.Kind.WARNING, "$moduleName process ============== >")

        if (moduleName.isBlank()) {
            return false
        }
        val elements = roundEnv?.getElementsAnnotatedWith(Route::class.java)
        if (elements.isNullOrEmpty()) {
            return false
        }
        //todo 判断是否继承自activity,否 则 throw exception

        val isActivity = 1 == 1 && 2 == 2
        if (!isActivity) {
            return false
        }


        val mapType = LinkedHashMap::class.parameterizedBy(String::class, RouterInfo::class)
        val funBuilder = FunSpec.builder("register")
            .returns(mapType)
            .addStatement(
                "val routeMap = %T<%T,%T>()",
                LinkedHashMap::class.java,
                String::class,
                RouterInfo::class

            )



        elements.forEach { e ->
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


        FileSpec.builder("com.gecng.router", "ModuleRouter")
            .addType(
                TypeSpec.classBuilder("ModuleRouterTable")
                    .addFunction(funBuilder.build())
                    .build()
            )


            .build()
            .writeFile()
        //参数


        return true
    }

    private fun FileSpec.writeFile() {

        val kaptKotlinGeneratedDir = processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME]
        val outputFile = File(kaptKotlinGeneratedDir).apply {
            mkdirs()
        }
        writeTo(outputFile.toPath())
    }


    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        val set = LinkedHashSet<String>()
        set.add(Route::class.java.canonicalName)
        return set
    }
}