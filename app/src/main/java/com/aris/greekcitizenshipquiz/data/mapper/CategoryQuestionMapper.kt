package com.aris.greekcitizenshipquiz.data.mapper

import com.aris.greekcitizenshipquiz.data.local.entity.CategoryQuestionEntity
import com.aris.greekcitizenshipquiz.data.remote.dto.CategoryQuestionDto


fun CategoryQuestionDto.toEntity(): CategoryQuestionEntity {

    return CategoryQuestionEntity(

        categoryId = categoryId,

        name = name,

        description = description,

        isActive = isActive,

        updatedAt = updatedAt
    )
}