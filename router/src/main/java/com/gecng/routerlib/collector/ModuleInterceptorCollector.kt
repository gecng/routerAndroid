package com.gecng.routerlib.collector

import com.gecng.routeannotation.IInterceptorTable
import com.gecng.routeannotation.InterceptorInfo
import com.gecng.routeannotation.RouteConst

class ModuleInterceptorCollector {

    companion object {

        fun collect(moduleList: List<String>): LinkedHashMap<String, InterceptorInfo> {

            val totalMap = LinkedHashMap<String, InterceptorInfo>()
            moduleList.forEach { module ->
                try {
                    val clazzPath = String.format(RouteConst.INTERCEPTOR_PATH_FORMAT, module)
                    val moduleMap = (Class.forName(clazzPath).newInstance() as? IInterceptorTable)?.register()
                    if (moduleMap != null) {
                        totalMap.putAll(moduleMap)
                    }

                }catch (e:ClassNotFoundException){

                }

            }

            return totalMap
        }
    }
}