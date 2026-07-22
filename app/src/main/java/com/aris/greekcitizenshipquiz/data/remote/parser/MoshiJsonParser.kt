package com.aris.greekcitizenshipquiz.data.remote.parser


import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class MoshiJsonParser @Inject constructor(
    private val moshi: Moshi
) : JsonParser {


    override fun <T> parseList(
        json: String,
        clazz: Class<T>
    ): List<T> {

        val type = Types.newParameterizedType(
            List::class.java,
            clazz
        )

        val adapter = moshi.adapter<List<T>>(type)

        return adapter.fromJson(json)
            ?: emptyList()
    }


    override fun <T> parseObject(
        json: String,
        clazz: Class<T>
    ): T? {

        val adapter = moshi.adapter(clazz)

        return adapter.fromJson(json)
    }

}