package com.gecng.routerlib

import android.app.Application
import android.content.Context
import android.content.Intent
import com.gecng.routeannotation.RouterInfo
import com.gecng.routerlib.activitymanager.ActivityStackManager
import com.gecng.routerlib.collector.ModuleTableCollector
import com.gecng.routerlib.interceptor.InterceptorManager
import com.gecng.routerlib.parser.PathParserManager
import com.gecng.routerlib.parser.RouteRequestBody

/**
 * Simple Router
 */
class SRouter {

    companion object {
        val INSTANCE: SRouter by lazy { SRouter() }
    }

    //存放全部的路由信息
    private val routeMap: LinkedHashMap<String, RouterInfo> = LinkedHashMap()
    //拦截管理器
    private val interceptorManager: InterceptorManager = InterceptorManager()

    /**
     * 在application中初始化
     * @param moduleList List<String>
     */
    fun init(app: Application, moduleList: List<String>) {
        //监听activity，方便手动管理activity栈
        app.registerActivityLifecycleCallbacks(ActivityStackManager.INSTANCE.getActivityLifeCycleCallback())
        //装载路由表
        routeMap.putAll(ModuleTableCollector.collect(moduleList))
        //装载拦截器
        interceptorManager.init(moduleList)
    }

    fun route(body: RouteRequestBody) {

        //获取路由表中的path
        val routeTablePath = PathParserManager.parseRoutePath(body)
        val intent = PathParserManager.parseParams(body)

        val routerInfo = routeMap[routeTablePath] ?: return
        val ctx = body.getContext() ?: ActivityStackManager.INSTANCE.getTopActivity()


        //组装 RouteRequest
        intent.setClass(ctx!!, routerInfo.clazz)
        val req = RouteRequest(context = ctx, path = routeTablePath, intent = intent)

        //是否拦截
        when (interceptorManager.onIntercept(req, routerInfo.interceptors)) {
            true -> {//被拦截了，路由转发

            }
            else -> {//没有被拦截，进行路由跳转
                ctx.startActivity(intent)
            }
        }
        body.recycle()
        req.recycle()
    }


}