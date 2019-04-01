package com.gecng.processor

import javax.lang.model.element.Element

class ActivityFilter {

    /**
     * https://www.jianshu.com/p/f1ca4314f804
     * https://www.jianshu.com/p/f41d21850cb4
     * 类型判断
     */
    companion object {
        //todo 筛选 类型为activity 的element
        fun filter(element: Element): Boolean {
//            val tmMetadata = element.getTypeElement("kotlin.Metadata").asType()
//            return element.annotationMirrors.find { it.annotationType == tmMetadata } == null
            return true
        }
    }
}