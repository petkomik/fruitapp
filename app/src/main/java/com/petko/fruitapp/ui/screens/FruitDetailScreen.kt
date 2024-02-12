package com.petko.fruitapp.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.petko.fruitapp.R
import com.petko.fruitapp.utils.Fruit
import com.petko.fruitapp.utils.PageType

@Composable
fun FruitDetailsScreen(
    fruitUiState: FruitUiState,
    onBackPressed: () -> Unit,
    addToFavourites: (Fruit) -> Unit,
    removeFromFavourites: (Fruit) -> Unit,
    modifier: Modifier = Modifier,
    isFullScreen: Boolean = false
) {
    BackHandler {
        onBackPressed()
    }
    Box(modifier = modifier) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.primaryContainer)
                .padding(top = 18.dp)
        ) {
            item {
                if ( isFullScreen ) {
                    FruitDetailsScreenTopBar(
                        onBackPressed,
                        fruitUiState,
                        Modifier
                            .fillMaxWidth()
                            .padding(bottom = 18.dp)
                    )
                }
                FruitDetailsCard(
                    fruit = fruitUiState.currentSelectedFruit,
                    isFullScreen = isFullScreen,
                    addToFavourites = addToFavourites,
                    removeFromFavourites = removeFromFavourites,
                    isFavorite = (fruitUiState.fruits[PageType.Favorites]?.map { it.id }?.
                        contains(fruitUiState.currentSelectedFruit.id)) ?: false,
                    modifier = if (isFullScreen) {
                        Modifier.padding(horizontal = 24.dp)
                    } else {
                        Modifier.padding(horizontal = 18.dp)
                    }
                )
            }
        }
    }
}

@Composable
private fun FruitDetailsScreenTopBar(
    onBackButtonClicked: () -> Unit,
    fruitUiState: FruitUiState,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(
            onClick = onBackButtonClicked,
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .background(MaterialTheme.colorScheme.surface, shape = CircleShape),
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = stringResource(id = R.string.navigation_back)
            )
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 40.dp)
        ) {
            Text(
                text = fruitUiState.currentSelectedFruit.name,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun FruitDetailsCard(
    fruit: Fruit,
    modifier: Modifier = Modifier,
    isFullScreen: Boolean = false,
    addToFavourites: (Fruit) -> Unit,
    removeFromFavourites: (Fruit) -> Unit,
    isFavorite: Boolean = false
) {
    Column (
        modifier = modifier
    ){
        Card(
            modifier = Modifier,
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        all = 20.dp
                    )
            ) {
                FruitInfo(fruit, Modifier, isFullScreen)
            }
        }

        Spacer(modifier = Modifier.height(15.dp))

        if (isFavorite) {
            OutlinedButton(
                onClick = { removeFromFavourites(fruit) },
                shape = MaterialTheme.shapes.small,
                modifier = Modifier.fillMaxWidth(),
                border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
            ) {
                Text(stringResource(R.string.remove_from_favourites))
            }
        } else {
            Button(
                onClick = { addToFavourites(fruit) },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.small,
                enabled = true
            ) {
                Text(stringResource(R.string.add_to_favourites))
            }
        }
    }
}

@Composable
private fun FruitInfo(
    fruit: Fruit,
    modifier: Modifier = Modifier,
    isFullScreen: Boolean = false
) {
    if (!isFullScreen) {
        ItemDetailsRow(
            detailType = stringResource(R.string.name),
            itemDetail = fruit.name)
    }
    ItemDetailsRow(
        detailType = stringResource(R.string.id),
        itemDetail = fruit.id.toString(),
    )
    ItemDetailsRow(
        detailType = stringResource(R.string.order),
        itemDetail = fruit.order,
    )
    ItemDetailsRow(
        detailType = stringResource(R.string.family),
        itemDetail = fruit.family,
    )
    ItemDetailsRow(
        detailType = stringResource(R.string.genus),
        itemDetail = fruit.genus,
    )
    ItemDetailsRow(
        detailType = stringResource(R.string.nutrition),
    )
    ItemNutritionRow(
        detailType = stringResource(R.string.calories),
        itemDetail = fruit.nutritions.calories,
        unit = stringResource(R.string.kcal)
    )
    ItemNutritionRow(
        detailType = stringResource(R.string.protein),
        itemDetail = fruit.nutritions.protein,
        modifier = modifier,
        unit = stringResource(R.string.g)
    )
    ItemNutritionRow(
        detailType = stringResource(R.string.carbohydrates),
        itemDetail = fruit.nutritions.carbohydrates,
        modifier = modifier,
        unit = stringResource(R.string.g)
    )
    ItemNutritionRow(
        detailType = stringResource(R.string.fats),
        itemDetail = fruit.nutritions.fat,
        modifier = modifier,
        unit = stringResource(R.string.g)
    )
    ItemNutritionRow(
        detailType = stringResource(R.string.sugar),
        itemDetail = fruit.nutritions.sugar,
        modifier = modifier,
        unit = stringResource(R.string.g)
    )
}

@Composable
private fun ItemDetailsRow(
    detailType: String,
    itemDetail: String = "",
    unit: String = ""
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = 5.dp
            )
    ) {
        Text(text = detailType, fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.weight(1f))
        Text(text = itemDetail + unit, fontStyle = FontStyle.Italic)
    }
}

@Composable
private fun ItemNutritionRow(
    detailType: String,
    modifier: Modifier = Modifier,
    itemDetail: Double = 0.0,
    unit: String = ""
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = 5.dp
            )
    ) {
        Text(
            text = detailType,
            fontWeight = FontWeight.Normal,
            modifier = modifier.padding(start = 30.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(text = "$itemDetail $unit", fontStyle = FontStyle.Italic)
    }
}