package com.gecng.routerlib.activitymanager

import android.app.Activity
import android.app.Application

interface IActivityManager {


    fun getActivityLifeCycleCallback(): Application.ActivityLifecycleCallbacks

    fun popActivity()

    fun getTopActivity(): Activity?

    fun isActivityExist(path: String): Boolean

    fun finishActivity(path: String)

    /**
     * 清楚 path对应 activity 上方的所有的activity
     * @param path String
     * @param includeSelf Boolean 是否包含自己本身
     */
    fun popUntil(path: String, includeSelf: Boolean)

    fun popUntil(targetClazz: Class<*>, includeSelf: Boolean)
}