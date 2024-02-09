package com.petko.fruitapp.ui.screens

import android.widget.Toast
import androidx.activity.compose.BackHandler
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
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.platform.LocalContext
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
                .background(color = MaterialTheme.colorScheme.inverseOnSurface)
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
                    pageType = fruitUiState.currentPageType,
                    modifier = if (isFullScreen) {
                        Modifier.padding(horizontal = 24.dp)
                    } else {
                        Modifier.padding(horizontal = 18.dp)
                    },
                    isFullScreen = isFullScreen
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
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun FruitDetailsCard(
    fruit: Fruit,
    pageType: PageType,
    modifier: Modifier = Modifier,
    isFullScreen: Boolean = false
) {
    val context = LocalContext.current
    val displayToast = { text: String ->
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
    Column (
        modifier = modifier,
    ){
        Card(
            modifier = Modifier,
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
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

        // TODO
        if (isFullScreen) {
            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.small,
                enabled = true
            ) {
                Text("Add To Favourites")
            }
        } else {
            OutlinedButton(
                onClick = {},
                shape = MaterialTheme.shapes.small,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Remove From Favourites")
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
        ItemDetailsRow(detailType = "Name", itemDetail = fruit.name, modifier = modifier)
    }
    ItemDetailsRow(
        detailType = "Id",
        itemDetail = fruit.id.toString(),
        modifier = modifier
    )
    ItemDetailsRow(
        detailType = "Order",
        itemDetail = fruit.order,
        modifier = modifier
    )
    ItemDetailsRow(
        detailType = "Family",
        itemDetail = fruit.family,
        modifier = modifier
    )
    ItemDetailsRow(
        detailType = "Genus",
        itemDetail = fruit.genus,
        modifier = modifier
    )
    ItemDetailsRow(
        detailType = "Nutrition:",
        modifier = modifier
    )
    ItemNutritionRow(
        detailType = "Calories",
        itemDetail = fruit.nutritions.calories,
        modifier = modifier,
        unit = "kcal"
    )
    ItemNutritionRow(
        detailType = "Protein",
        itemDetail = fruit.nutritions.protein,
        modifier = modifier,
        unit = "g"
    )
    ItemNutritionRow(
        detailType = "Carbohydrates",
        itemDetail = fruit.nutritions.carbohydrates,
        modifier = modifier,
        unit = "g"
    )
    ItemNutritionRow(
        detailType = "Fats",
        itemDetail = fruit.nutritions.fat,
        modifier = modifier,
        unit = "g"
    )
    ItemNutritionRow(
        detailType = "Sugar",
        itemDetail = fruit.nutritions.sugar,
        modifier = modifier,
        unit = "g"
    )
}

@Composable
private fun ItemDetailsRow(
    detailType: String,
    itemDetail: String = "",
    unit: String = "",
    modifier: Modifier = Modifier
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
    itemDetail: Double = 0.0,
    unit: String = "",
    modifier: Modifier = Modifier
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

@Composable
private fun ActionButton(
    text: String,
    onButtonClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
    containIrreversibleAction: Boolean = false,
) {
    Box(modifier = modifier) {
        Button(
            onClick = { onButtonClicked(text) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 10.dp
                ),
            colors = ButtonDefaults.buttonColors(
                containerColor =
                if (containIrreversibleAction) {
                    MaterialTheme.colorScheme.onErrorContainer
                } else {
                    MaterialTheme.colorScheme.primaryContainer
                }
            )
        ) {
            Text(
                text = text,
                color =
                if (containIrreversibleAction) {
                    MaterialTheme.colorScheme.onError
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                }
            )
        }
    }
}
