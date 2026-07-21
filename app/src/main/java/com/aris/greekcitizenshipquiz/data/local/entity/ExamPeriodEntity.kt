package com.aris.greekcitizenshipquiz.data.local.entity
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "exam_period",
    indices = [
        Index("isActive"),
        Index("updatedAt")
    ]
)
data class ExamPeriodEntity(

    @PrimaryKey
    val id: Int,

    val title: String,

    val isActive: Boolean,

    val updatedAt: String
)