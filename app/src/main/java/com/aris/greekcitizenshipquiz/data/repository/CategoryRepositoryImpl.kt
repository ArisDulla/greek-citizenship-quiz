package com.aris.greekcitizenshipquiz.data.repository

import com.aris.greekcitizenshipquiz.data.local.dao.CategoryQuestionDao
import com.aris.greekcitizenshipquiz.data.mapper.toDomain
import com.aris.greekcitizenshipquiz.domain.model.CategoryQuestion
import com.aris.greekcitizenshipquiz.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryQuestionDao: CategoryQuestionDao
) : CategoryRepository {


    override fun getActiveCategoriesWithQuestions(): Flow<List<CategoryQuestion>> {

        return categoryQuestionDao
            .getActiveCategoriesWithQuestions()
            .map { entities ->

                entities.map { entity ->
                    entity.toDomain()
                }
            }
    }

    override suspend fun getCategoryById(
        categoryId: Int
    ): CategoryQuestion? {

        return categoryQuestionDao
            .getCategoryById(categoryId)
            ?.toDomain()
    }

}