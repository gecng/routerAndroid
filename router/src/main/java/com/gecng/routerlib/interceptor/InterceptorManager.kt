package com.gecng.routerlib.interceptor

import android.util.Log
import com.gecng.routerlib.IInterceptor
import com.gecng.routeannotation.InterceptorInfo
import com.gecng.routerlib.RouteInterceptor
import com.gecng.routerlib.RouteRequest
import com.gecng.routerlib.collector.ModuleInterceptorCollector


class InterceptorManager {


    //全局拦截器，全局拦截器，对所有的路由进行拦截
    val globalInceptor: List<RouteInterceptor> = listOf()
    // todo 是否需要添加module 级别的拦截器，对该module 级别下的路由进行拦截
    val moduleInceptor = null


    //拦截信息
    private val interceptorList: LinkedHashMap<String, InterceptorInfo> = LinkedHashMap()

    fun init(moduleList: List<String>) {
        interceptorList.clear()
        interceptorList.putAll(ModuleInterceptorCollector.collect(moduleList))
    }

    /**
     * 添加全局拦截器
     */
    fun addGlobalInceptor() {

    }

    fun onIntercept(request: RouteRequest, interceptors: List<String>?): Boolean {
        if (interceptors.isNullOrEmpty()) {
            return false
        }
        interceptors.forEach { url ->
            if (!url.isBlank()) {
                val info = interceptorList[url]
                if (shouldIntercept(request, info)) {
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
    private fun shouldIntercept(request: RouteRequest, info: InterceptorInfo?): Boolean {
        if (info == null) {
            return false
        }
        var interceptor: IInterceptor? = null
        try {
            interceptor = info.clazz.newInstance() as? IInterceptor
        } catch (e: Exception) {
        }
        if (interceptor == null) return false
        return interceptor.onIntercept(request)
    }
}