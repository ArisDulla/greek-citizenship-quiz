package com.aris.greekcitizenshipquiz.data.mapper

import com.aris.greekcitizenshipquiz.data.local.entity.TypeQuestionEntity
import com.aris.greekcitizenshipquiz.data.remote.dto.TypeQuestionDto


fun TypeQuestionDto.toEntity(): TypeQuestionEntity {

    return TypeQuestionEntity(

        typeQuestionId = typeQuestionId,

        name = name,

        description = description,

        sortOrder = sortOrder,

        isActive = isActive,

        updatedAt = updatedAt
    )
}