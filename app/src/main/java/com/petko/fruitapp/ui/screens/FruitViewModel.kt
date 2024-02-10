package com.petko.fruitapp.ui.screens

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


class FruitViewModel(
    private val networkFruitRepository: FruitRepository,
    private val offlineFruitsRepository: FruitRepository
) : ViewModel() {

    private val _fruitUiState = MutableStateFlow(FruitUiState())
    val fruitUiState: StateFlow<FruitUiState> = _fruitUiState

    init {
        getFruits()
    }

    fun addToFavorites(fruit: Fruit) {
        viewModelScope.launch {
            offlineFruitsRepository.insertItem(fruit.toFruitDB())
            getFruits()
        }
    }

    fun removeFromFavorites(fruit: Fruit) {
        viewModelScope.launch {
            offlineFruitsRepository.deleteItem(fruit.toFruitDB())
            getFruits()
        }
    }


    private fun getFruits() {
        viewModelScope.launch {
            _fruitUiState.update {
                it.copy(
                    fruits = mutableMapOf(PageType.Home to networkFruitRepository.getFruits(),
                    PageType.Favorites to fruitsFromDb()))
            }
        }
    }

    private fun fruitsFromDb() : List<Fruit> {
        val fruits = mutableListOf<Fruit>()
        viewModelScope.launch {
            offlineFruitsRepository.getAllItemsStream().collect() { fruitsDB ->
                fruitsDB.forEach { fruitDB ->
                    fruits.add(fruitDB.toFruit())
                }
            }
        }
        return fruits
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
            val firstFruit = if (it.fruits[it.currentPageType]?.isNotEmpty() == true) {
                it.fruits[it.currentPageType]?.get(0)
            } else {
                null
            }

            it.copy(
                currentSelectedFruit = firstFruit ?: Fruit("", 0, "", "", "", Nutrition(0.0, 0.0, 0.0, 0.0, 0.0)),
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
                val networkFruitRepository = application.container.networkFruitRepository
                val offlineFruitRepository = application.container.offlineFruitRepository
                FruitViewModel(
                    networkFruitRepository = networkFruitRepository,
                    offlineFruitsRepository = offlineFruitRepository)
            }

        }
    }

}