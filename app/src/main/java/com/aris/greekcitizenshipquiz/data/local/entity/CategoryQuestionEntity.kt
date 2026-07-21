package com.aris.greekcitizenshipquiz.data.local.entity
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "category_question",
    indices = [
        Index("isActive"),
        Index("updatedAt")
    ]
)
data class CategoryQuestionEntity(

    @PrimaryKey
    val categoryId: Int,

    val name: String,

    val description: String?,

    val isActive: Boolean,

    val updatedAt: String
)