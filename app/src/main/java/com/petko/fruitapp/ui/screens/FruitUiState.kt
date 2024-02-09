package com.petko.fruitapp.ui.screens

import com.petko.fruitapp.utils.Fruit
import com.petko.fruitapp.utils.Nutrition
import com.petko.fruitapp.utils.PageType

data class FruitUiState(
    val currentPageType: PageType = PageType.Home,
    val fruits: Map<PageType, List<Fruit>> = emptyMap(),
    val currentSelectedFruit: Fruit = fruits[currentPageType]?.get(0)
        ?: Fruit("", 0, "", "", "",
            Nutrition(0.0, 0.0, 0.0, 0.0, 0.0)),
    val isShowingHomepage: Boolean = true
)