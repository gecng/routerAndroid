package com.gecng.routerlib.interceptor

import android.util.Log
import com.gecng.routeannotation.IInterceptor
import com.gecng.routeannotation.InterceptorInfo
import com.gecng.routerlib.RouteInterceptor
import com.gecng.routerlib.collector.ModuleInterceptorCollector

@Suppress("SENSELESS_COMPARISON")
class InterceptorManager {


    //全局拦截器，全局拦截器，对所有的路由进行拦截
    val globalInceptor: List<RouteInterceptor> = listOf()
    // todo 是否需要添加module 级别的拦截器，对该module 级别下的路由进行拦截
    val moduleInceptor = null


    //拦截信息
    private val interceptorMap: LinkedHashMap<String, InterceptorInfo> = LinkedHashMap()

    fun init(moduleList: List<String>) {
        interceptorMap.putAll(ModuleInterceptorCollector.collect(moduleList))
    }

    fun onIntercept(interceptors: List<String>?): Boolean {
        if (interceptors.isNullOrEmpty()) {
            return false
        }
        interceptors.forEach { url ->
            if (!url.isBlank()) {
                val info = interceptorMap[url]
                if (shouldIntercept(info)) {
                    Log.d("InterceptorManager", "$url ======>  被拦截")
                    return true
                }
            }
        }
        return false

    }

    /**
     * 判读是否拦截
     * true 拦截
     * false 不拦截
     */
    private fun shouldIntercept(info: InterceptorInfo?): Boolean {
        if (info == null) {
            return false
        }
        if (info.clazz == null) {
            return false
        }
        if (info.interceptor == null) {
            try {
                info.interceptor = info.clazz.newInstance() as? IInterceptor
            } catch (e: Exception) {

            }
        }
        return info.interceptor?.onIntercept() ?: false

    }
}