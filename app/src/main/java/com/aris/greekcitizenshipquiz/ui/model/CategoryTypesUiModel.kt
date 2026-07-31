package com.aris.greekcitizenshipquiz.ui.model

import com.aris.greekcitizenshipquiz.domain.model.CategoryQuestion
import com.aris.greekcitizenshipquiz.domain.model.CategoryTypeCount

data class CategoryTypesUiModel(
    val category: CategoryQuestion?,
    val types: List<CategoryTypeCount>
)