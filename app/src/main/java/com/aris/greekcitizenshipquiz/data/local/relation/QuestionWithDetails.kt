package com.aris.greekcitizenshipquiz.data.local.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.aris.greekcitizenshipquiz.data.local.entity.QuestionEntity
import com.aris.greekcitizenshipquiz.data.local.entity.QuestionImageEntity
import com.aris.greekcitizenshipquiz.data.local.entity.QuestionOptionEntity

data class QuestionWithDetails(

    @Embedded
    val question: QuestionEntity,

    @Relation(
        parentColumn = "questionId",
        entityColumn = "questionId"
    )
    val options: List<QuestionOptionEntity>,

    @Relation(
        parentColumn = "questionId",
        entityColumn = "questionId"
    )
    val images: List<QuestionImageEntity>


)