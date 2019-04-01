package com.gecng.routerlib

import com.gecng.routeannotation.IRouteTable
import com.gecng.routeannotation.RouterInfo

class ModuleTableCollector {


    companion object {

        fun collect(moduleList: List<String>): LinkedHashMap<String, RouterInfo> {

            val totalMap = LinkedHashMap<String, RouterInfo>()

            moduleList.forEach {
                val clazzPath = "com.gecng.router.$it.${it}_module_router_table"
                val moduleMap = (Class.forName(clazzPath).newInstance() as? IRouteTable)?.register()
                if (moduleMap != null) {
                    totalMap.putAll(moduleMap)
                }
            }

            return totalMap
        }

    }


}