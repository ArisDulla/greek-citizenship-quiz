package com.aris.greekcitizenshipquiz.domain.repository

import com.aris.greekcitizenshipquiz.domain.model.CategoryQuestion
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    fun getActiveCategoriesWithQuestions(): Flow<List<CategoryQuestion>>
    // fun getActiveCategoriesWithQuestions(): Flow<List<CategoryQuestionEntity>>

}