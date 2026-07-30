package com.aris.greekcitizenshipquiz.data.mapper

import com.aris.greekcitizenshipquiz.data.local.entity.QuestionImageEntity
import com.aris.greekcitizenshipquiz.data.remote.dto.QuestionImageDto
import com.aris.greekcitizenshipquiz.domain.model.QuestionImage

fun QuestionImageDto.toEntity(): QuestionImageEntity {

    return QuestionImageEntity(

        imageId = imageId,

        questionId = questionId,

        image = image,

        caption = caption,

        order = order,

        createdAt = createdAt,

        updatedAt = updatedAt
    )
}
fun QuestionImageEntity.toDomain(): QuestionImage {

    return QuestionImage(
        imageId = imageId,
        image = image,
        caption = caption,
        order = order
    )
}