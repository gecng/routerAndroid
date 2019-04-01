package com.gecng.routeannotation

interface IRouteTable {
    fun register(): LinkedHashMap<String, RouterInfo>
}