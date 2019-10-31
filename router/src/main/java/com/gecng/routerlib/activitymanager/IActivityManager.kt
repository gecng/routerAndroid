package com.gecng.routerlib.activitymanager

import android.app.Activity
import android.app.Application

interface IActivityManager {


    fun getActivityLifeCycleCallback(): Application.ActivityLifecycleCallbacks

    fun popActivity()

    fun getTopActivity(): Activity?

    fun isActivityExist(): Boolean

    fun finishActivity(path: String)

    fun clearTop()
}