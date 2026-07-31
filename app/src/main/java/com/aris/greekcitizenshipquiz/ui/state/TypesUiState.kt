package com.aris.greekcitizenshipquiz.ui.state

import com.aris.greekcitizenshipquiz.ui.model.CategoryTypesUiModel

sealed interface TypesUiState {

    data object Loading : TypesUiState


    data class Success(
        val data: CategoryTypesUiModel
    ) : TypesUiState


    data class Empty(
        val message: String
    ) : TypesUiState


    data class Error(
        val message: String
    ) : TypesUiState
}