package com.aris.greekcitizenshipquiz.data.remote.api

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface QuizApi {


    @GET("download/")
    suspend fun downloadQuiz(
        @Query("since") version: Int
    ): Response<ResponseBody>

}