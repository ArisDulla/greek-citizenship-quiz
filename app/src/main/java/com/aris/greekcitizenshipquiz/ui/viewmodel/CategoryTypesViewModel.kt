package com.aris.greekcitizenshipquiz.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aris.greekcitizenshipquiz.domain.usecase.GetQuestionTypesByCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.aris.greekcitizenshipquiz.domain.usecase.GetCategoryByIdUseCase
import com.aris.greekcitizenshipquiz.ui.model.CategoryTypesUiModel
import com.aris.greekcitizenshipquiz.ui.state.TypesUiState


@HiltViewModel
class CategoryTypesViewModel @Inject constructor(
    private val getQuestionTypesByCategoryUseCase: GetQuestionTypesByCategoryUseCase,
    private val getCategoryByIdUseCase: GetCategoryByIdUseCase
) : ViewModel() {


    private val _uiState =
        MutableStateFlow<TypesUiState>(TypesUiState.Loading)

    val uiState = _uiState.asStateFlow()


    fun loadTypes(categoryId: Int) {

        viewModelScope.launch {

            try {

                _uiState.value = TypesUiState.Loading


                val category =
                    getCategoryByIdUseCase(categoryId)


                val types =
                    getQuestionTypesByCategoryUseCase(categoryId)


                if (types.isEmpty()) {

                    _uiState.value =
                        TypesUiState.Empty(
                            message = "Δεν υπάρχουν διαθέσιμοι τύποι ερωτήσεων"
                        )
                } else {

                    _uiState.value =
                        TypesUiState.Success(
                            CategoryTypesUiModel(
                                category = category,
                                types = types
                            )
                        )
                }


            } catch (e: Exception) {

                _uiState.value =
                    TypesUiState.Error(
                        e.message ?: "Παρουσιάστηκε σφάλμα κατά τη φόρτωση των δεδομένων."
                    )
            }
        }
    }
}