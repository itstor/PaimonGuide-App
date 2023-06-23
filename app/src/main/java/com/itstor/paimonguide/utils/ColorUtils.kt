package com.itstor.paimonguide.utils

import android.graphics.Color
import kotlin.math.roundToInt


class ColorUtils {
    companion object {
        fun adjustBrightness(color: Int, factor: Float): Int {
            val a: Int = Color.alpha(color)
            val r = (Color.red(color) * factor).roundToInt()
            val g = (Color.green(color) * factor).roundToInt()
            val b = (Color.blue(color) * factor).roundToInt()
            return Color.argb(
                a,
                r.coerceAtMost(255),
                g.coerceAtMost(255),
                b.coerceAtMost(255)
            )
        }
    }
}