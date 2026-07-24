package com.aris.greekcitizenshipquiz.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "incorrect_answers")
data class IncorrectAnswerEntity(

    @PrimaryKey
    val questionId: Int
)