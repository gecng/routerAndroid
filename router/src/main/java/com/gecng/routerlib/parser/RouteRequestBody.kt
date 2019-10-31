package com.gecng.routerlib.parser

import android.content.Context
import android.content.Intent

class RouteRequestBody {
    private var mIntent: Intent? = null

    private var mPath = ""

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
        this.mPath = path
    }

    fun setIntent(i: Intent) {
        this.mIntent = i
    }

    fun getPath() = mPath

    fun recycle() {
        mContext = null
    }
}