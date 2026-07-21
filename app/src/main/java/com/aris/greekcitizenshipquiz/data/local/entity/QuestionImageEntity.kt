package com.aris.greekcitizenshipquiz.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "question_image",

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

        Index(value = ["createdAt"]),

        Index(value = ["updatedAt"])
    ]
)
data class QuestionImageEntity(

    @PrimaryKey
    val imageId: Int,

    val questionId: Int,

    val image: String,

    val caption: String?,

    val order: Int,

    val createdAt: String,

    val updatedAt: String
)