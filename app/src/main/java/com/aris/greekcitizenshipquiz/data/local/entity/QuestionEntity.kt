package com.aris.greekcitizenshipquiz.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "question",

    foreignKeys = [
        ForeignKey(
            entity = CategoryQuestionEntity::class,
            parentColumns = ["categoryId"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.CASCADE
        ),

        ForeignKey(
            entity = TypeQuestionEntity::class,
            parentColumns = ["typeQuestionId"],
            childColumns = ["typeQuestionId"],
            onDelete = ForeignKey.SET_NULL
        )
    ],

    indices = [

        Index(value = ["isNew"]),
        Index(value = ["maxCorrect"]),
        Index(value = ["questionNumber"]),
        Index(value = ["isDeleted"]),
        Index(value = ["createdAt"]),
        Index(value = ["updatedAt"]),
        Index(value = ["categoryId"]),
        Index(value = ["typeQuestionId"]),
    ]
)
data class QuestionEntity(

    @PrimaryKey
    val questionId: Int,

    val topic: String,

    val introText: String?,

    val mainText: String,

    val textCompletion: String?,

    val focusCompletion: String?,

    val isNew: Boolean,

    val maxCorrect: Int?,

    val categoryId: Int,

    val typeQuestionId: Int?,

    val questionNumber: Int,

    val isDeleted: Boolean,

    val createdAt: String,

    val updatedAt: String,

    val imageAnswer: String?
)