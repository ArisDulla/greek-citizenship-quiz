package com.aris.greekcitizenshipquiz.data.local.entity
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "type_question",
    indices = [
        Index("sortOrder"),
        Index("isActive"),
        Index("updatedAt")
    ]
)
data class TypeQuestionEntity(

    @PrimaryKey
    val typeQuestionId: Int,

    val name: String,

    val description: String?,

    val sortOrder: Int,

    val isActive: Boolean,

    val updatedAt: String
)