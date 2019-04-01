package com.gecng.routeannotation


/**
 * 路由注解
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class Route(val path: String)

/**
 * 拦截器注解
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class Interceptor(val name: String)


