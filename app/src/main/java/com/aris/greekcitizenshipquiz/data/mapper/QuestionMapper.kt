package com.aris.greekcitizenshipquiz.data.mapper

import com.aris.greekcitizenshipquiz.data.local.entity.QuestionEntity
import com.aris.greekcitizenshipquiz.data.remote.dto.QuestionDto
import com.aris.greekcitizenshipquiz.domain.model.Question


fun QuestionDto.toEntity(): QuestionEntity {

    return QuestionEntity(

        questionId = questionId,

        topic = topic,

        introText = introText,

        mainText = mainText,

        textCompletion = textCompletion,

        focusCompletion = focusCompletion,

        isNew = isNew,

        maxCorrect = maxCorrect,

        categoryId = categoryId,

        typeQuestionId = typeQuestionId,

        questionNumber = questionNumber,

        isDeleted = isDeleted,

        createdAt = "",

        updatedAt = updatedAt,

        imageAnswer = imageAnswer
    )
}
fun QuestionEntity.toDomain(): Question {

    return Question(

        questionId = questionId,

        topic = topic,

        introText = introText,

        mainText = mainText,

        textCompletion = textCompletion,

        focusCompletion = focusCompletion,

        categoryId = categoryId,

        typeQuestionId = typeQuestionId,

        questionNumber = questionNumber,

        imageAnswer = imageAnswer,

        isNew = isNew,

        maxCorrect = maxCorrect,
    )
}