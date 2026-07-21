package com.aris.greekcitizenshipquiz.data.mapper

import com.aris.greekcitizenshipquiz.data.local.entity.QuestionImageEntity
import com.aris.greekcitizenshipquiz.data.remote.dto.QuestionImageDto


fun QuestionImageDto.toEntity(): QuestionImageEntity {

    return QuestionImageEntity(

        imageId = imageId,

        questionId = questionId,

        image = image,

        caption = caption,

        order = order,

        createdAt = "",

        updatedAt = updatedAt
    )
}