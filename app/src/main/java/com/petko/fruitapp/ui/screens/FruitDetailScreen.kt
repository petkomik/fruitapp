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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
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
                .padding(top = dimensionResource(R.dimen.detail_card_list_padding_top))
        ) {
            item {
                if ( isFullScreen ) {
                    FruitDetailsScreenTopBar(
                        onBackPressed,
                        fruitUiState,
                        Modifier
                            .fillMaxWidth()
                            .padding(bottom = dimensionResource(R.dimen.detail_topbar_padding_bottom))
                    )
                }
                FruitDetailsCard(
                    fruit = fruitUiState.currentSelectedFruit,
                    pageType = fruitUiState.currentPageType,
                    modifier = if (isFullScreen) {
                        Modifier.padding(horizontal = dimensionResource(R.dimen.detail_card_outer_padding_horizontal))
                    } else {
                        Modifier.padding(end = dimensionResource(R.dimen.detail_card_outer_padding_horizontal))
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
                .padding(horizontal = dimensionResource(R.dimen.detail_topbar_back_button_padding_horizontal))
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
                .padding(end = dimensionResource(R.dimen.detail_subject_padding_end))
        ) {
            Text(
                text = fruitUiState.currentSelectedFruit.name,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
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
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.detail_card_inner_padding))
        ) {
            DetailsScreenHeader(
                fruit,
                Modifier.fillMaxWidth()
            )
            if (isFullScreen) {
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.detail_content_padding_top)))
            } else {
                Text(
                    text = fruit.name,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.outline,
                    modifier = Modifier.padding(
                        top = dimensionResource(R.dimen.detail_content_padding_top),
                        bottom = dimensionResource(R.dimen.detail_expanded_subject_body_spacing)
                    ),
                )
            }
            Text(
                text = fruit.family,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            DetailsScreenButtonBar(pageType, displayToast)
        }
    }
}

@Composable
private fun DetailsScreenButtonBar(
    pageType: PageType,
    displayToast: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
            ActionButton(
                text = stringResource(id = R.string.add_to_favourite),
                onButtonClicked = displayToast
            )
        }
}


@Composable
private fun DetailsScreenHeader(fruit: Fruit, modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .padding(
                horizontal = dimensionResource(R.dimen.fruit_header_content_padding_horizontal),
                vertical = dimensionResource(R.dimen.fruit_header_content_padding_vertical)
            ),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = fruit.name,
            style = MaterialTheme.typography.labelMedium
        )
        Text(
            text = fruit.family,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.outline
        )
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
                .padding(vertical = dimensionResource(R.dimen.detail_action_button_padding_vertical)),
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
