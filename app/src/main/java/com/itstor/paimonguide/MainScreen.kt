package com.itstor.paimonguide

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.itstor.paimonguide.ui.navigation.Screen
import com.itstor.paimonguide.ui.screens.about.AboutScreen
import com.itstor.paimonguide.ui.screens.details.DetailsScreen
import com.itstor.paimonguide.ui.screens.favorite.FavoriteScreen
import com.itstor.paimonguide.ui.screens.home.HomeScreen
import com.itstor.paimonguide.ui.theme.BlueGrayBackground

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    Scaffold(
        modifier = modifier,
        containerColor = BlueGrayBackground,
    ) { innerPadding ->
        NavHost(navController = navController, startDestination = Screen.Home.route) {
            composable(Screen.Home.route) {
                HomeScreen(
                    modifier = Modifier.padding(innerPadding),
                    navController = navController,
                    navigateToDetails = { characterId ->
                        navController.navigate(Screen.Details.createRoute(characterId))
                    }
                )
            }
            composable(Screen.Favorites.route) {
                FavoriteScreen(navigateToDetails = { characterId ->
                    navController.navigate(Screen.Details.createRoute(characterId))
                }, modifier = Modifier.padding(innerPadding), navController = navController)
            }
            composable(Screen.About.route) {
                AboutScreen(
                    modifier = Modifier.padding(innerPadding),
                    navController = navController,
                )
            }
            composable(
                Screen.Details.route,
                arguments = listOf(navArgument(Screen.Details.ARGUMENT_CHARACTER_ID) {
                    type = NavType.StringType
                })
            ) {
                val characterId = it.arguments?.getString(Screen.Details.ARGUMENT_CHARACTER_ID)
                characterId?.let { id ->
                    DetailsScreen(
                        modifier = Modifier.padding(innerPadding),
                        characterId = id,
                        navigateBack = { navController.popBackStack() },
                    )
                }
            }
        }
    }
}