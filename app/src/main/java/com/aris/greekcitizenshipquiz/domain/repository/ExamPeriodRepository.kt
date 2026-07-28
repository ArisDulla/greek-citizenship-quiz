package com.aris.greekcitizenshipquiz.domain.repository

import kotlinx.coroutines.flow.Flow

interface ExamPeriodRepository {

    fun observeLatestExamPeriodTitle(): Flow<String?>

}