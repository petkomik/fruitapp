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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.petko.fruitapp.R
import com.petko.fruitapp.utils.Fruit

@Composable
fun FruitLogo(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary
) {
    Image(
        painter = painterResource(R.mipmap.logo_foreground),
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
        verticalArrangement = Arrangement.spacedBy(
            dimensionResource(R.dimen.fruit_list_item_vertical_spacing)
        )
    ) {
        item {
            FruitHomeTopBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = dimensionResource(R.dimen.topbar_padding_vertical))
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
                    end = dimensionResource(R.dimen.list_and_detail_list_padding_end),
                    top = dimensionResource(R.dimen.list_and_detail_list_padding_top)
                ),
            verticalArrangement = Arrangement.spacedBy(
                dimensionResource(R.dimen.fruit_list_item_vertical_spacing)
            )
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
        onClick = onCardClick
    ) {
        Row(
            modifier = Modifier.padding(
                horizontal = dimensionResource(R.dimen.fruit_list_item_inner_padding),
                vertical = dimensionResource(R.dimen.fruit_list_item_inner_padding)
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = fruit.name,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(
                        bottom = dimensionResource(R.dimen.fruit_list_item_subject_body_spacing)
                    ),
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
                .size(100.dp)
                .padding(start = dimensionResource(R.dimen.topbar_logo_padding_start))
        )
        Text(
            modifier = Modifier
                .padding(horizontal = dimensionResource(R.dimen.drawer_padding_header)),
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}