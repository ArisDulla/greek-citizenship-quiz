package com.aris.greekcitizenshipquiz.di

import com.aris.greekcitizenshipquiz.data.remote.parser.JsonParser
import com.aris.greekcitizenshipquiz.data.remote.parser.MoshiJsonParser
import com.aris.greekcitizenshipquiz.data.repository.QuizRepositoryImpl
import com.aris.greekcitizenshipquiz.domain.repository.QuizRepository
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
    abstract fun bindJsonParser(
        impl: MoshiJsonParser
    ): JsonParser

}