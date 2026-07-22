package com.aris.greekcitizenshipquiz.data.remote.parser


interface JsonParser {


    fun <T> parseList(
        json: String,
        clazz: Class<T>
    ): List<T>


    fun <T> parseObject(
        json: String,
        clazz: Class<T>
    ): T?

}