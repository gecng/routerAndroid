package com.gecng.routerlib.collector

import com.gecng.routeannotation.IRouteTable
import com.gecng.routeannotation.RouteConst
import com.gecng.routeannotation.RouterInfo
import java.lang.Exception

class ModuleTableCollector {


    companion object {

        /**
         * 遍历所有的module，全部装载到内存中
         * todo 分组
         * @param moduleList List<String> 所有module的集合
         */
        fun collect(moduleList: List<String>): LinkedHashMap<String, RouterInfo> {

            val totalMap = LinkedHashMap<String, RouterInfo>()
            moduleList.forEach { moduleName ->
                val clazzPath = String.format(RouteConst.ROUTE_TABLE_PATH_FORMAT, moduleName)
                try {
                    val routeTable = Class.forName(clazzPath).newInstance() as IRouteTable
                    val moduleMap = routeTable.register()
                    totalMap.putAll(moduleMap)
                } catch (e: Exception) {
                    e.printStackTrace()
                    //部分模块 可能木有拦截器
                }
            }
            return totalMap
        }
    }
}