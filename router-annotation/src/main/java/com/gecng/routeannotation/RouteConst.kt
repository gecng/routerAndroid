package com.gecng.routeannotation

class RouteConst {

    companion object {


        private const val PACKAGE_NAME = "com.gecng.router"

        //自动生成代码的路径
        private const val COMMON_GENERATE_FILE_PATH = "$PACKAGE_NAME.infos"


        const val INTERCEPTOR_FILE_DIR = "$COMMON_GENERATE_FILE_PATH.interceptor"

        const val ROUTE_FILE_DIR = "$COMMON_GENERATE_FILE_PATH.route"


        const val ROUTE_TABLE_NAME_FORMAT = "%s_route_table"
        //路由表 路径
        const val ROUTE_TABLE_PATH_FORMAT = "$ROUTE_FILE_DIR.$ROUTE_TABLE_NAME_FORMAT"

        //拦截器表 命名 模块名+_interceptor_table
        const val INTERCEPTOR_NAME_FORMAT = "%s_interceptor_table"
        //拦截器表 路径
        const val INTERCEPTOR_PATH_FORMAT = "$INTERCEPTOR_FILE_DIR.$INTERCEPTOR_NAME_FORMAT"


    }
}