package com.aris.greekcitizenshipquiz.data.repository

import com.aris.greekcitizenshipquiz.data.local.dao.CategoryQuestionDao
import com.aris.greekcitizenshipquiz.data.local.dao.ExamPeriodDao
import com.aris.greekcitizenshipquiz.data.local.dao.QuestionDao
import com.aris.greekcitizenshipquiz.data.local.dao.QuestionImageDao
import com.aris.greekcitizenshipquiz.data.local.dao.QuestionOptionDao
import com.aris.greekcitizenshipquiz.data.local.dao.SyncMetaDao
import com.aris.greekcitizenshipquiz.data.local.dao.TypeQuestionDao
import com.aris.greekcitizenshipquiz.data.remote.api.QuizApi
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class QuizRepository @Inject constructor(

    private val quizApi: QuizApi,

    private val questionDao: QuestionDao,

    private val questionOptionDao: QuestionOptionDao,

    private val questionImageDao: QuestionImageDao,

    private val categoryQuestionDao: CategoryQuestionDao,

    private val typeQuestionDao: TypeQuestionDao,

    private val examPeriodDao: ExamPeriodDao,

    private val syncMetaDao: SyncMetaDao

) {

}