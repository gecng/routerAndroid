package com.gecng.routerlib

fun SRouter.build(path: String): ParamBuilder {
    return ParamBuilder(path)
}