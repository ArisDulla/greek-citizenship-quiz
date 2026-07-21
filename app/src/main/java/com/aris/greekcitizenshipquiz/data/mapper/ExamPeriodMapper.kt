package com.aris.greekcitizenshipquiz.data.mapper

import com.aris.greekcitizenshipquiz.data.local.entity.ExamPeriodEntity
import com.aris.greekcitizenshipquiz.data.remote.dto.ExamPeriodDto


fun ExamPeriodDto.toEntity(): ExamPeriodEntity {

    return ExamPeriodEntity(

        id = id,

        title = title,

        isActive = isActive,

        updatedAt = updatedAt
    )
}