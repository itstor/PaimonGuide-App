package com.itstor.paimonguide.ui.navigation

sealed class Screen(val  route:String){
    object Home:Screen("home")
    object Details:Screen("details/{characterId}") {
        const val ARGUMENT_CHARACTER_ID = "characterId"

        fun createRoute(characterId: String): String {
            return "details/$characterId"
        }
    }
    object Favorites:Screen("favorites")
    object About:Screen("about")
}