package com.aris.greekcitizenshipquiz.data.mapper

import com.aris.greekcitizenshipquiz.data.local.entity.QuestionOptionEntity
import com.aris.greekcitizenshipquiz.data.remote.dto.QuestionOptionDto


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