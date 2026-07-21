package com.aris.greekcitizenshipquiz.di

import android.content.Context
import androidx.room.Room
import com.aris.greekcitizenshipquiz.data.local.AppDatabase
import com.aris.greekcitizenshipquiz.data.local.dao.CategoryQuestionDao
import com.aris.greekcitizenshipquiz.data.local.dao.ExamPeriodDao
import com.aris.greekcitizenshipquiz.data.local.dao.QuestionDao
import com.aris.greekcitizenshipquiz.data.local.dao.QuestionImageDao
import com.aris.greekcitizenshipquiz.data.local.dao.QuestionOptionDao
import com.aris.greekcitizenshipquiz.data.local.dao.SyncMetaDao
import com.aris.greekcitizenshipquiz.data.local.dao.TypeQuestionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {


    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {

        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "greek_citizenship_quiz.db"
        )
            .build()
    }


    @Provides
    fun provideQuestionDao(
        database: AppDatabase
    ): QuestionDao {
        return database.questionDao()
    }


    @Provides
    fun provideQuestionOptionDao(
        database: AppDatabase
    ): QuestionOptionDao {
        return database.questionOptionDao()
    }


    @Provides
    fun provideQuestionImageDao(
        database: AppDatabase
    ): QuestionImageDao {
        return database.questionImageDao()
    }


    @Provides
    fun provideCategoryQuestionDao(
        database: AppDatabase
    ): CategoryQuestionDao {
        return database.categoryQuestionDao()
    }


    @Provides
    fun provideTypeQuestionDao(
        database: AppDatabase
    ): TypeQuestionDao {
        return database.typeQuestionDao()
    }


    @Provides
    fun provideExamPeriodDao(
        database: AppDatabase
    ): ExamPeriodDao {
        return database.examPeriodDao()
    }


    @Provides
    fun provideSyncMetaDao(
        database: AppDatabase
    ): SyncMetaDao {
        return database.syncMetaDao()
    }
}