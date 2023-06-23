package com.itstor.paimonguide.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.itstor.paimonguide.R
import com.itstor.paimonguide.ui.navigation.NavigationItem
import com.itstor.paimonguide.ui.navigation.Screen
import com.itstor.paimonguide.ui.theme.BlueGray
import com.itstor.paimonguide.ui.theme.PaimonGuideTheme

@Composable
fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val navigationItems = listOf(
        NavigationItem(
            title = stringResource(R.string.home_title),
            icon = Icons.Filled.Home,
            screen = Screen.Home
        ),
        NavigationItem(
            title = stringResource(R.string.favorites_title),
            icon = Icons.Filled.Favorite,
            screen = Screen.Favorites
        ),
        NavigationItem(
            title = stringResource(R.string.about_title),
            icon = Icons.Filled.Person,
            screen = Screen.About
        ),
    )

    Box(modifier = modifier.padding(20.dp)) {
        NavigationBar(
            modifier = Modifier.shadow(
                elevation = 1.dp,
                shape = RoundedCornerShape(24.dp),
                clip = true
            ),
            containerColor = BlueGray
        ) {
            navigationItems.map { item ->
                NavigationBarItem(
                    modifier = Modifier,
                    icon = {
                        val isSelected = currentRoute == item.screen.route
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title,
                            tint = if (isSelected) Color.White else LocalContentColor.current
                        )
                    },
                    selected = currentRoute == item.screen.route,
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = BlueGray
                    ),
                    onClick = {
                        navController.navigate(item.screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }

}

@Preview
@Composable
fun BottomBarPreview() {
    PaimonGuideTheme {
        BottomBar(navController = rememberNavController())
    }
}