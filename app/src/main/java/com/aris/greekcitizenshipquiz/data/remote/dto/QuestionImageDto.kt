package com.aris.greekcitizenshipquiz.data.remote.dto

import com.squareup.moshi.Json


data class QuestionImageDto(

    @param:Json(name = "imageId")
    val imageId: Int,

    @param:Json(name = "question")
    val questionId: Int,

    @param:Json(name = "image")
    val image: String,

    @param:Json(name = "caption")
    val caption: String?,

    @param:Json(name = "order")
    val order: Int,

    @param:Json(name = "updated_at")
    val updatedAt: String,

    @param:Json(name = "created_at")
    val createdAt: String,
)