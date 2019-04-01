package com.gecng.routeannotation

interface IInterceptorTable {
    fun register(): LinkedHashMap<String, InterceptorInfo>
}