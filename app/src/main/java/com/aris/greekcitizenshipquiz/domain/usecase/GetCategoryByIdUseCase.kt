package com.aris.greekcitizenshipquiz.domain.usecase
import com.aris.greekcitizenshipquiz.domain.model.CategoryQuestion
import javax.inject.Inject
import com.aris.greekcitizenshipquiz.domain.repository.CategoryRepository

class GetCategoryByIdUseCase @Inject constructor(
    private val repository: CategoryRepository
) {

    suspend operator fun invoke(
        categoryId: Int
    ): CategoryQuestion? {

        return repository.getCategoryById(categoryId)
    }
}