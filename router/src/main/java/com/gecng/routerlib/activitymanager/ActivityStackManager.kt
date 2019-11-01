package com.gecng.routerlib.activitymanager

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.gecng.routerlib.SRouter
import java.util.*

/**
 * 这里的代码 参照了 http://blankj.com 的 android Utils里面的写法
 *
 */
class ActivityStackManager : IActivityManager {


    companion object {
        val INSTANCE = SingleHolder.holder
    }

    private object SingleHolder {
        val holder = ActivityStackManager()
    }

    private val mActivityList: LinkedList<Activity> = LinkedList()

    private val mActivityLifeCycleImpl = LifeCycleImpl()

    override fun popActivity() {
        mActivityList.pollLast()?.finish()
    }

    override fun getTopActivity(): Activity? {
        return mActivityList.peekLast()
    }


    override fun popUntil(targetClazz: Class<*>, includeSelf: Boolean) {

        val needPopList = mutableListOf<Activity>()
        val iterator = mActivityList.descendingIterator()
        while (iterator.hasNext()) {
            val act = iterator.next()
            if (act.javaClass == targetClazz) {
                if (includeSelf) needPopList.add(act)
                break
            }
            needPopList.add(act)
        }
        needPopList.forEach { item -> item.finish() }
    }

    override fun popUntil(path: String, includeSelf: Boolean) {
        //查找路由表,判断路由表中是否存在 路由信息
        val targetRouteInfo = SRouter.INSTANCE.getRouteInfoByPath(path)
        targetRouteInfo ?: return
        //判断手动维护的activity栈中是否存在
        val isExist = isActivityExist(path)
        if (!isExist) return

        popUntil(targetRouteInfo.clazz, includeSelf)
    }

    override fun getActivityLifeCycleCallback(): Application.ActivityLifecycleCallbacks {
        return mActivityLifeCycleImpl
    }


    override fun isActivityExist(path: String): Boolean {
        val routerInfo = SRouter.INSTANCE.getRouteInfoByPath(path)
        routerInfo ?: return false
        val activityClazz = routerInfo.clazz
        val targetActivity = mActivityList.firstOrNull { act ->
            act.javaClass == activityClazz.javaClass
        }
        return targetActivity == null
    }

    override fun finishActivity(path: String) {
        val routerInfo = SRouter.INSTANCE.getRouteInfoByPath(path)
        routerInfo ?: return
        val activityClazz = routerInfo.clazz
        val targetActivity = mActivityList.firstOrNull { act ->
            act.javaClass == activityClazz.javaClass
        }
        targetActivity?.finish()
    }


    private inner class LifeCycleImpl : SimpleActivityLifecycleCallbacks() {
        override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
            setTopActivity(activity)
        }

        override fun onActivityDestroyed(activity: Activity?) {
            activity ?: return
            mActivityList.remove(activity)
        }

        private fun setTopActivity(activity: Activity?) {
            activity ?: return
            when (mActivityList.contains(activity)) {
                true -> {
                    if (mActivityList.last != activity) {
                        mActivityList.remove(activity)
                        mActivityList.addLast(activity)
                    }
                }
                else -> mActivityList.addLast(activity)
            }
        }
    }

}