package com.aris.greekcitizenshipquiz.data.repository

import com.aris.greekcitizenshipquiz.data.local.dao.ExamPeriodDao
import com.aris.greekcitizenshipquiz.domain.repository.ExamPeriodRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExamPeriodRepositoryImpl @Inject constructor(
    private val examPeriodDao: ExamPeriodDao
) : ExamPeriodRepository {


    override fun observeLatestExamPeriodTitle(): Flow<String?> {

        return examPeriodDao.observeLatestExamPeriodTitle()
    }
}