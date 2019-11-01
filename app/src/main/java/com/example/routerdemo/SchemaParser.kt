//package com.example.routerdemo
//
//import android.content.Intent
//import android.net.Uri
//import com.gecng.routerlib.RouteRequestBody
//import com.gecng.routerlib.parser.IParser
//
///**
// * 解析 schema
// * abc://module/submodule/action?param1=x&param2=y
// */
//class SchemaParser : IParser {
//    override fun getPath(body: RouteRequestBody): String {
//    }
//
//    override fun parseParams(body: RouteRequestBody): Intent {
//        Uri.parse(body.getPath())
//    }
//
//    override fun check(body: RouteRequestBody): Boolean {
//
//    }
//}