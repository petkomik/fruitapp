package com.petko.fruitapp.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.petko.fruitapp.ui.screens.FruitViewModel
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.petko.fruitapp.ui.screens.FruitHomeScreen
import com.petko.fruitapp.utils.Fruit
import com.petko.fruitapp.utils.PageType
import com.petko.fruitapp.utils.FruitContentType
import com.petko.fruitapp.utils.FruitNavigationType


@Composable
fun FruitInfoApp(
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier
) {
    val navigationType: FruitNavigationType
    val contentType: FruitContentType
    val fruitViewModel: FruitViewModel = viewModel(factory = FruitViewModel.Factory)
    val fruitUiState = fruitViewModel.fruitUiState.collectAsState().value

    when (windowSize) {
        WindowWidthSizeClass.Compact -> {
            navigationType = FruitNavigationType.BOTTOM_NAVIGATION
            contentType = FruitContentType.LIST_ONLY
        }
        WindowWidthSizeClass.Medium -> {
            navigationType = FruitNavigationType.NAVIGATION_RAIL
            contentType = FruitContentType.LIST_ONLY
        }
        WindowWidthSizeClass.Expanded -> {
            navigationType = FruitNavigationType.PERMANENT_NAVIGATION_DRAWER
            contentType = FruitContentType.LIST_AND_DETAIL
        }
        else -> {
            navigationType = FruitNavigationType.BOTTOM_NAVIGATION
            contentType = FruitContentType.LIST_ONLY
        }
    }

    if(fruitUiState.currentSelectedFruit.name == "") {
        fruitViewModel.resetHomeScreenStates()
    }

    FruitHomeScreen(
        navigationType = navigationType,
        contentType= contentType,
        fruitUiState = fruitUiState,
        onTabPressed = { pageType: PageType ->
            fruitViewModel.updateCurrentMailbox(pageType = pageType)
            fruitViewModel.resetHomeScreenStates()
        },
        onFruitPressed = { fruit: Fruit ->
            fruitViewModel.updateDetailsScreenStates(
                fruit = fruit
            )
        },
        onDetailScreenBackPressed = {
            fruitViewModel.resetHomeScreenStates()
        },
        addToFavourites = { fruit: Fruit ->
            fruitViewModel.addToFavorites(fruit)
        },
        removeFromFavourites = { fruit: Fruit ->
            fruitViewModel.removeFromFavorites(fruit)
        },
        modifier = modifier
    )
}
