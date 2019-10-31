package com.gecng.routerlib.parser

import android.content.Intent

/**
 * 将各种形式的路由进行转换
 *
 * 场景1：原生发起路由  "/app/login"
 * 场景2：server下发btn的跳转路径 schema "xxx://module/submodule/component?param=1&param2=test"
 */
interface IParser {

    /**
     * 检查当前的body 是否可以解析
     * @param body RouteRequestBody
     * @return Boolean
     */
    fun check(body: RouteRequestBody): Boolean

    /**
     * 解析成 路由表的里的路径
     * @param body RouteRequestBody
     * @return String
     */
    fun getPath(body: RouteRequestBody): String

    /**
     * 解析参数，加载到intent中
     * @param body RouteRequestBody
     * @return Intent
     */
    fun parseParams(body: RouteRequestBody): Intent
}