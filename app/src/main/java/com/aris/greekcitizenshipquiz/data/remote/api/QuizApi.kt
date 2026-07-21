package com.aris.greekcitizenshipquiz.data.remote.api

import okhttp3.ResponseBody
import retrofit2.http.GET


interface QuizApi {


    @GET("api/download/")
    suspend fun downloadQuiz(): ResponseBody

}