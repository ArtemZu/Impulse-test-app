package com.artemzu.githubapp.main.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.artemzu.githubapp.main.navigation.MainDestination

@Composable
fun BottomBar(
    destinations: List<MainDestination>,
    onNavigateToDestination: (MainDestination) -> Unit,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier,
) {
    NavigationBar(
        modifier = modifier,
        tonalElevation = 0.dp
    ) {
        destinations.forEach { destination ->
            val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)
            ItaNavigationBarItem(
                selected = selected,
                onClick = { onNavigateToDestination(destination) },
                icon = {
                    val icon = if (selected) {
                        destination.selectedIcon
                    } else {
                        destination.unselectedIcon
                    }
                    Icon(icon, contentDescription = null)
                },
                label = { Text(stringResource(destination.iconTextId)) },
            )
        }
    }
}

@Composable
private fun RowScope.ItaNavigationBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    selectedIcon: @Composable () -> Unit = icon,
    enabled: Boolean = true,
    label: @Composable (() -> Unit)? = null,
    alwaysShowLabel: Boolean = true,
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = if (selected) selectedIcon else icon,
        modifier = modifier,
        enabled = enabled,
        label = label,
        alwaysShowLabel = alwaysShowLabel,
//        colors = NavigationBarItemDefaults.colors(
//            selectedIconColor = ItaNavigationDefaults.navigationSelectedItemColor(),
//            unselectedIconColor = ItaNavigationDefaults.navigationContentColor(),
//            selectedTextColor = ItaNavigationDefaults.navigationSelectedItemColor(),
//            unselectedTextColor = ItaNavigationDefaults.navigationContentColor(),
//            indicatorColor = ItaNavigationDefaults.navigationIndicatorColor(),
//        ),
    )
}

private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: MainDestination) =
    this?.hierarchy?.any {
        it.route?.contains(destination.name, true) ?: false
    } ?: false