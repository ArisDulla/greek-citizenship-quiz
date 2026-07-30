package com.aris.greekcitizenshipquiz.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aris.greekcitizenshipquiz.data.local.dao.CategoryQuestionDao
import com.aris.greekcitizenshipquiz.data.local.dao.ExamPeriodDao
import com.aris.greekcitizenshipquiz.data.local.dao.IncorrectAnswerDao
import com.aris.greekcitizenshipquiz.data.local.dao.QuestionDao
import com.aris.greekcitizenshipquiz.data.local.dao.QuestionImageDao
import com.aris.greekcitizenshipquiz.data.local.dao.QuestionOptionDao
import com.aris.greekcitizenshipquiz.data.local.dao.SyncMetaDao
import com.aris.greekcitizenshipquiz.data.local.dao.TypeQuestionDao
import com.aris.greekcitizenshipquiz.data.local.entity.CategoryQuestionEntity
import com.aris.greekcitizenshipquiz.data.local.entity.ExamPeriodEntity
import com.aris.greekcitizenshipquiz.data.local.entity.QuestionEntity
import com.aris.greekcitizenshipquiz.data.local.entity.QuestionImageEntity
import com.aris.greekcitizenshipquiz.data.local.entity.QuestionOptionEntity
import com.aris.greekcitizenshipquiz.data.local.entity.SyncMetaEntity
import com.aris.greekcitizenshipquiz.data.local.entity.TypeQuestionEntity
import com.aris.greekcitizenshipquiz.data.local.entity.IncorrectAnswerEntity


@Database(
    entities = [
        CategoryQuestionEntity::class,
        TypeQuestionEntity::class,
        ExamPeriodEntity::class,

        QuestionEntity::class,
        QuestionOptionEntity::class,
        QuestionImageEntity::class,

        SyncMetaEntity::class,
        IncorrectAnswerEntity::class
    ],
    version = 6,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {


    abstract fun categoryQuestionDao(): CategoryQuestionDao


    abstract fun typeQuestionDao(): TypeQuestionDao


    abstract fun examPeriodDao(): ExamPeriodDao


    abstract fun questionDao(): QuestionDao


    abstract fun questionOptionDao(): QuestionOptionDao


    abstract fun questionImageDao(): QuestionImageDao


    abstract fun syncMetaDao(): SyncMetaDao

    abstract fun incorrectAnswerDao(): IncorrectAnswerDao
}