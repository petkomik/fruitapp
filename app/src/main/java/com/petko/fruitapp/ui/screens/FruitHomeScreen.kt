package com.petko.fruitapp.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.petko.fruitapp.R
import com.petko.fruitapp.utils.Fruit
import com.petko.fruitapp.utils.FruitContentType
import com.petko.fruitapp.utils.FruitNavigationType
import com.petko.fruitapp.utils.PageType

@Composable
fun FruitHomeScreen(
    navigationType: FruitNavigationType,
    contentType: FruitContentType,
    fruitUiState: FruitUiState,
    onTabPressed: (PageType) -> Unit,
    onFruitPressed: (Fruit) -> Unit,
    onDetailScreenBackPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    val navigationItemContentList = listOf(
        NavigationItemContent(
            pageType = PageType.Favorites,
            icon = Icons.Default.Favorite,
            text = stringResource(id = R.string.tab_favorites)
        ),
        NavigationItemContent(
            pageType = PageType.Home,
            icon = Icons.Default.Home,
            text = stringResource(id = R.string.tab_home)
        )
    )
    if (navigationType == FruitNavigationType.PERMANENT_NAVIGATION_DRAWER) {
        val navigationDrawerContentDescription = stringResource(R.string.navigation_drawer)
        PermanentNavigationDrawer(
            drawerContent = {
                PermanentDrawerSheet(Modifier.width(dimensionResource(R.dimen.drawer_width))) {
                    NavigationDrawerContent(
                        selectedDestination = fruitUiState.currentPageType,
                        onTabPressed = onTabPressed,
                        navigationItemContentList = navigationItemContentList,
                        modifier = Modifier
                            .wrapContentWidth()
                            .fillMaxHeight()
                            .background(MaterialTheme.colorScheme.inverseOnSurface)
                            .padding(dimensionResource(R.dimen.drawer_padding_content))
                    )
                }
            },
            modifier = Modifier.testTag(navigationDrawerContentDescription)
        ) {
            FruitAppContent(
                navigationType = navigationType,
                contentType = contentType,
                fruitUiState = fruitUiState,
                onTabPressed = onTabPressed,
                onFruitPressed = onFruitPressed,
                navigationItemContentList = navigationItemContentList,
                modifier = modifier
            )
        }
    } else {
        if (fruitUiState.isShowingHomepage) {
            FruitAppContent(
                navigationType = navigationType,
                contentType = contentType,
                fruitUiState = fruitUiState,
                onTabPressed = onTabPressed,
                onFruitPressed = onFruitPressed,
                navigationItemContentList = navigationItemContentList,
                modifier = modifier
            )
        } else {
            FruitDetailsScreen(
                fruitUiState = fruitUiState,
                isFullScreen = true,
                onBackPressed = onDetailScreenBackPressed,
                modifier = modifier
            )
        }
    }
}

@Composable
private fun FruitAppContent(
    navigationType: FruitNavigationType,
    contentType: FruitContentType,
    fruitUiState: FruitUiState,
    onTabPressed: ((PageType) -> Unit),
    onFruitPressed: (Fruit) -> Unit,
    navigationItemContentList: List<NavigationItemContent>,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        Row(modifier = Modifier.fillMaxSize()) {
            AnimatedVisibility(visible = navigationType == FruitNavigationType.NAVIGATION_RAIL) {
                val navigationRailContentDescription = stringResource(R.string.navigation_rail)
                NavigationRail(
                    currentTab = fruitUiState.currentPageType,
                    onTabPressed = onTabPressed,
                    navigationItemContentList = navigationItemContentList,
                    modifier = Modifier
                        .testTag(navigationRailContentDescription)
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.inverseOnSurface)
            ) {
                if (contentType == FruitContentType.LIST_AND_DETAIL) {
                    FruitListAndDetailContent(
                        fruitUiState = fruitUiState,
                        onFruitPressed = onFruitPressed,
                        modifier = Modifier.weight(1f)
                    )
                } else {
//                    Text(text = fruitUiState.toString())
                    FruitListOnlyContent(
                        fruitUiState = fruitUiState,
                        onFruitPressed = onFruitPressed,
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = dimensionResource(R.dimen.fruit_list_only_horizontal_padding))
                    )
                }
                AnimatedVisibility(
                    visible = navigationType == FruitNavigationType.BOTTOM_NAVIGATION
                ) {
                    BottomNavigationBar(
                        currentTab = fruitUiState.currentPageType,
                        onTabPressed = onTabPressed,
                        navigationItemContentList = navigationItemContentList,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
private fun NavigationRail(
    currentTab: PageType,
    onTabPressed: ((PageType) -> Unit),
    navigationItemContentList: List<NavigationItemContent>,
    modifier: Modifier = Modifier
) {
    NavigationRail(modifier = modifier) {
        for (navItem in navigationItemContentList) {
            NavigationRailItem(
                selected = currentTab == navItem.pageType,
                onClick = { onTabPressed(navItem.pageType) },
                icon = {
                    Icon(
                        imageVector = navItem.icon,
                        contentDescription = navItem.text
                    )
                }
            )
        }
    }
}

@Composable
private fun BottomNavigationBar(
    currentTab: PageType,
    onTabPressed: ((PageType) -> Unit),
    navigationItemContentList: List<NavigationItemContent>,
    modifier: Modifier = Modifier
) {
    NavigationBar(modifier = modifier) {
        for (navItem in navigationItemContentList) {
            NavigationBarItem(
                selected = currentTab == navItem.pageType,
                onClick = { onTabPressed(navItem.pageType) },
                icon = {
                    Icon(
                        imageVector = navItem.icon,
                        contentDescription = navItem.text
                    )
                }
            )
        }
    }
}

@Composable
private fun NavigationDrawerContent(
    selectedDestination: PageType,
    onTabPressed: ((PageType) -> Unit),
    navigationItemContentList: List<NavigationItemContent>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        NavigationDrawerHeader(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.profile_image_padding)),
        )
        for (navItem in navigationItemContentList) {
            NavigationDrawerItem(
                selected = selectedDestination == navItem.pageType,
                label = {
                    Text(
                        text = navItem.text,
                        modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.drawer_padding_header))
                    )
                },
                icon = {
                    Icon(
                        imageVector = navItem.icon,
                        contentDescription = navItem.text
                    )
                },
                colors = NavigationDrawerItemDefaults.colors(
                    unselectedContainerColor = Color.Transparent
                ),
                onClick = { onTabPressed(navItem.pageType) }
            )
        }
    }
}

@Composable
private fun NavigationDrawerHeader(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        FruitLogo(modifier = Modifier.size(70.dp))
        Text(
            modifier = Modifier
                .padding(horizontal = dimensionResource(R.dimen.drawer_padding_header)),
            text = stringResource(id = R.string.app_name),
        )
    }
}

private data class NavigationItemContent(
    val pageType: PageType,
    val icon: ImageVector,
    val text: String
)