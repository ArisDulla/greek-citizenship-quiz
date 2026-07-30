package com.aris.greekcitizenshipquiz.data.mapper

import com.aris.greekcitizenshipquiz.data.local.entity.QuestionOptionEntity
import com.aris.greekcitizenshipquiz.data.remote.dto.QuestionOptionDto
import com.aris.greekcitizenshipquiz.domain.model.QuestionOption

fun QuestionOptionDto.toEntity(): QuestionOptionEntity {

    return QuestionOptionEntity(

        optionId = optionId,

        questionId = questionId,

        optionText = optionText,

        optionImage = optionImage,

        isCorrect = isCorrect,

        order = order,

        updatedAt = updatedAt
    )
}

fun QuestionOptionEntity.toDomain(): QuestionOption {

    return QuestionOption(
        optionId = optionId,
        optionText = optionText,
        optionImage = optionImage,
        isCorrect = isCorrect,
        order = order
    )
}