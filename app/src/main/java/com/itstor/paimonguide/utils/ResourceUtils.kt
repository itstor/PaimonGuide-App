package com.itstor.paimonguide.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

class ResourceUtils {
    companion object {
        @Composable
        fun getImageResource(id: String, suffix: String, defaultImage: Int): Int {
            val packageName = LocalContext.current.packageName
            val resourceName = id + '_' + suffix

            val resourceId = try {
                val resId = Class.forName("$packageName.R\$drawable").getField(resourceName).getInt(null)
                resId
            } catch (e: Exception) {
                defaultImage
            }

            return resourceId
        }
    }
}