package com.aris.greekcitizenshipquiz.data.mapper

import com.aris.greekcitizenshipquiz.data.local.entity.QuestionEntity
import com.aris.greekcitizenshipquiz.data.remote.dto.QuestionDto
import com.aris.greekcitizenshipquiz.domain.model.Question
import  com.aris.greekcitizenshipquiz.data.local.relation.QuestionWithDetails

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

        createdAt = createdAt,

        updatedAt = updatedAt,

        imageAnswer = imageAnswer
    )
}
fun QuestionWithDetails.toDomain(): Question {

    return Question(

        questionId = question.questionId,

        topic = question.topic,

        introText = question.introText,

        mainText = question.mainText ?: "",

        textCompletion = question.textCompletion?: "",

        focusCompletion = question.focusCompletion ?: "",

        isNew = question.isNew,

        maxCorrect = question.maxCorrect,

        categoryId = question.categoryId,

        typeQuestionId = question.typeQuestionId,

        questionNumber = question.questionNumber,

        imageAnswer = question.imageAnswer,

        options = options.map { option ->
            option.toDomain()
        },

        images = images.map { image ->
            image.toDomain()
        }
    )
}