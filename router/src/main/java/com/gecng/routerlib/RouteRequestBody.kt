package com.gecng.routerlib

import android.content.Context
import android.content.Intent

/**
 * todo 这里 requestBody 有点不合理，是否需要区分 不同类型过来的 requestBody
 *
 * @property mIntent Intent?
 * @property mOriginPath String
 * @property mContext Context?
 */
class RouteRequestBody {
    private var mIntent: Intent? = null

    private var mOriginPath = ""

    private var mContext: Context? = null

    fun getContext(): Context? = mContext

    fun getIntent(): Intent {
        if (mIntent == null) mIntent = Intent()
        return mIntent!!
    }

    fun setContext(context: Context) {
        this.mContext = context
    }

    fun setPath(path: String) {
        this.mOriginPath = path
    }

    fun setIntent(i: Intent) {
        this.mIntent = i
    }

    fun getPath() = mOriginPath

    fun recycle() {
        mContext = null
    }
}