package com.gecng.routeannotation

interface IInterceptor {
    /**
     * 判断是否拦截
     * true -》 被拦截了
     * false -》 不拦截
     */
    fun onIntercept(): Boolean

}