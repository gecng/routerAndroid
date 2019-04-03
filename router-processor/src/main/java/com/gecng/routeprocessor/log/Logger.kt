package com.gecng.routeprocessor.log

import javax.annotation.processing.Messager
import javax.tools.Diagnostic

@Suppress("MemberVisibilityCanBePrivate", "SpellCheckingInspection")
class Logger(val messager: Messager? = null) {

    fun E(msg: String) {
        printMsg(Diagnostic.Kind.ERROR, msg)
    }

    fun W(msg: String) {
        printMsg(Diagnostic.Kind.WARNING, msg)
    }

    fun N(msg: String) {

        printMsg(Diagnostic.Kind.WARNING, msg)
    }

    fun O(msg: String) {
        printMsg(Diagnostic.Kind.OTHER, msg)
    }


    fun printMsg(level: Diagnostic.Kind, msg: String) {
        messager?.printMessage(level, msg)
    }


}