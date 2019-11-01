package com.gecng.routerlib.parser

import android.content.Intent
import com.gecng.routerlib.RouteRequestBody

object PathParserManager {

    private val parserList: MutableList<IParser> = mutableListOf()

    private val defParser = DefaultParser()

    fun parseParams(body: RouteRequestBody): Intent {
        val targetParser = parserList.firstOrNull { parser ->
            parser.check(body)
        }
        return when (targetParser == null) {
            false -> targetParser.parseParams(body)
            else -> defParser.parseParams(body)
        }
    }

    fun parseRoutePath(body: RouteRequestBody): String {
        val targetParser = parserList.firstOrNull { parser ->
            parser.check(body)
        }
        return when (targetParser == null) {
            false -> targetParser.getPath(body)
            else -> defParser.getPath(body)
        }
    }

    fun addParser(parser: IParser) {
        parserList.add(parser)
    }

    fun removeParse() {}
}