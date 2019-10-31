package com.gecng.routerlib.activitymanager

import android.app.Activity
import android.app.Application
import android.os.Bundle
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

    override fun clearTop() {
    }

    override fun getActivityLifeCycleCallback(): Application.ActivityLifecycleCallbacks {
        return mActivityLifeCycleImpl
    }


    override fun isActivityExist(): Boolean {
        return false
    }

    override fun finishActivity(path: String) {

    }


    private inner class LifeCycleImpl : Application.ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
            setTopActivity(activity)
        }

        override fun onActivityStarted(activity: Activity?) {
        }

        override fun onActivityResumed(activity: Activity?) {
        }


        override fun onActivityPaused(activity: Activity?) {
        }


        override fun onActivityStopped(activity: Activity?) {
        }


        override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {

        }

        override fun onActivityDestroyed(activity: Activity?) {

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