package com.petko.fruitapp.ui.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.petko.fruitapp.FruitApplication
import com.petko.fruitapp.utils.Fruit
import com.petko.fruitapp.data.FruitRepository
import com.petko.fruitapp.utils.Nutrition
import com.petko.fruitapp.utils.PageType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class FruitViewModel(private val fruitRepository: FruitRepository) : ViewModel() {

    private val _fruitUiState = MutableStateFlow(FruitUiState())
    val fruitUiState: StateFlow<FruitUiState> = _fruitUiState

    init {
        getFruits()
    }

    private fun getFruits() {
        Log.e("FruitViewModel", "getFruits")
        viewModelScope.launch {
            _fruitUiState.update {
                it.copy(
                    fruits = mutableMapOf(PageType.Home to fruitRepository.getFruits()))
            }
        }
    }
    fun updateDetailsScreenStates(fruit: Fruit) {
        _fruitUiState.update {
            it.copy(
                currentSelectedFruit = fruit,
                isShowingHomepage = false
            )
        }
    }

    fun resetHomeScreenStates() {
        _fruitUiState.update {
            it.copy(
                currentSelectedFruit = Fruit("", 0, "", "", "",
                    Nutrition(0.0, 0.0, 0.0, 0, 0.0)
                ),
                isShowingHomepage = true
            )
        }
    }

    fun updateCurrentMailbox(pageType: PageType) {
        _fruitUiState.update {
            it.copy(
                currentPageType = pageType
            )
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as FruitApplication)
                val fruitRepository = application.container.fruitRepository
                FruitViewModel(fruitRepository = fruitRepository)
            }

        }
    }

}