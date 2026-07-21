package com.aris.greekcitizenshipquiz.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "question_option",

    foreignKeys = [
        ForeignKey(
            entity = QuestionEntity::class,
            parentColumns = ["questionId"],
            childColumns = ["questionId"],
            onDelete = ForeignKey.CASCADE
        )
    ],

    indices = [
        Index(value = ["questionId"]),
        Index(
            value = [
                "questionId",
                "order"
            ],
            unique = true
        ),
        Index(value = ["isCorrect"]),
        Index(value = ["updatedAt"])
    ]
)
data class QuestionOptionEntity(

    @PrimaryKey
    val optionId: Int,

    val questionId: Int,

    val optionText: String?,

    val optionImage: String?,

    val isCorrect: Boolean,

    val order: Int,

    val updatedAt: String
)