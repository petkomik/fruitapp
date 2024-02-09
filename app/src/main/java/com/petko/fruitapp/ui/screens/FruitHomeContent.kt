package com.petko.fruitapp.ui.screens

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.petko.fruitapp.R
import com.petko.fruitapp.utils.Fruit

@Composable
fun FruitLogo(
    modifier: Modifier = Modifier,
) {
    Image(
        painter = painterResource(R.mipmap.logo_maxed_foreground),
        contentDescription = stringResource(R.string.logo),
        modifier = modifier
    )
}

@Composable
fun FruitListOnlyContent(
    fruitUiState: FruitUiState,
    onFruitPressed: (Fruit) -> Unit,
    modifier: Modifier = Modifier
) {
    val fruits = fruitUiState.fruits[fruitUiState.currentPageType] ?: emptyList()

    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            FruitHomeTopBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = 8.dp,
                        horizontal = 20.dp)
            )
        }
        items(fruits, key = { fruit -> fruit.id }) { fruit ->
            FruitListItem(
                fruit = fruit,
                selected = false,
                onCardClick = {
                    onFruitPressed(fruit)
                }
            )
        }
    }
    
}

@Composable
fun FruitListAndDetailContent(
    fruitUiState: FruitUiState,
    onFruitPressed: (Fruit) -> Unit,
    modifier: Modifier = Modifier
) {
    val fruits = fruitUiState.fruits[fruitUiState.currentPageType] ?: emptyList()
    Row(modifier = modifier) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 20.dp
                ),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(fruits, key = { fruit -> fruit.id }) { fruit ->
                FruitListItem(
                    fruit = fruit,
                    selected = fruitUiState.currentSelectedFruit.id == fruit.id,
                    onCardClick = {
                        onFruitPressed(fruit)
                    },
                )
            }
        }
        val activity = LocalContext.current as Activity
        FruitDetailsScreen(
            fruitUiState = fruitUiState,
            modifier = Modifier.weight(1f),
            onBackPressed = { activity.finish() }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FruitListItem(
    fruit: Fruit,
    selected: Boolean,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (selected) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.secondaryContainer
            }
        ),
        onClick = onCardClick,
        elevation = if (selected) {
            CardDefaults.cardElevation(defaultElevation = 4.dp)
        } else {
            CardDefaults.cardElevation(defaultElevation = 2.dp)
        }
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = fruit.name,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(bottom = 8.dp),
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "order: ${fruit.order}, family: ${fruit.family}, genus: ${fruit.genus}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontStyle = FontStyle.Italic
                )
            }
        }
    }
}


@Composable
private fun FruitHomeTopBar(modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        FruitLogo(
            modifier = Modifier
                .size(60.dp)
        )
        Text(
            text = stringResource(id = R.string.app_name_uc),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface,
            fontStyle = FontStyle.Italic,
            fontSize = TextUnit(18f, TextUnitType.Sp)
        )
    }
}