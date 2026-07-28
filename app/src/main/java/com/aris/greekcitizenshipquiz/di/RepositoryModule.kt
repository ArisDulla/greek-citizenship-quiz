package com.aris.greekcitizenshipquiz.di

import com.aris.greekcitizenshipquiz.data.remote.parser.JsonParser
import com.aris.greekcitizenshipquiz.data.remote.parser.MoshiJsonParser
import com.aris.greekcitizenshipquiz.data.repository.QuizRepositoryImpl
import com.aris.greekcitizenshipquiz.domain.repository.QuizRepository
import com.aris.greekcitizenshipquiz.domain.repository.CategoryRepository
import com.aris.greekcitizenshipquiz.domain.repository.QuestionRepository
import com.aris.greekcitizenshipquiz.data.repository.CategoryRepositoryImpl
import com.aris.greekcitizenshipquiz.data.repository.ExamPeriodRepositoryImpl
import com.aris.greekcitizenshipquiz.data.repository.QuestionRepositoryImpl
import com.aris.greekcitizenshipquiz.domain.repository.ExamPeriodRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {


    @Binds
    abstract fun bindQuizRepository(
        impl: QuizRepositoryImpl
    ): QuizRepository

    @Binds
    abstract fun bindCategoryRepository(
        impl: CategoryRepositoryImpl
    ): CategoryRepository


    @Binds
    abstract fun bindQuestionRepository(
        impl: QuestionRepositoryImpl
    ): QuestionRepository



    @Binds
    abstract fun bindJsonParser(
        impl: MoshiJsonParser
    ): JsonParser

    @Binds
    abstract fun bindExamPeriodRepository(
        impl: ExamPeriodRepositoryImpl
    ): ExamPeriodRepository

}