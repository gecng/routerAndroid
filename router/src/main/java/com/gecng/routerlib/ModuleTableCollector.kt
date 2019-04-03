package com.gecng.routerlib

import com.gecng.routeannotation.IRouteTable
import com.gecng.routeannotation.RouteConst
import com.gecng.routeannotation.RouterInfo

class ModuleTableCollector {


    companion object {

        fun collect(moduleList: List<String>): LinkedHashMap<String, RouterInfo> {

            val totalMap = LinkedHashMap<String, RouterInfo>()

            moduleList.forEach { module ->
                val clazzPath = String.format(RouteConst.ROUTE_TABLE_PATH_FORMAT, module)
                try {
                    val moduleMap = (Class.forName(clazzPath).newInstance() as? IRouteTable)?.register()
                    if (moduleMap != null) {
                        totalMap.putAll(moduleMap)
                    }

                }catch (e:ClassNotFoundException){
                    //部分模块 可能木有拦截器
                }

            }

            return totalMap
        }

    }


}