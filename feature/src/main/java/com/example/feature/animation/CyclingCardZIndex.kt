package com.example.feature.animation

import com.example.feature.VISIBLE_STACK_DEPTH

internal fun cyclingCardZIndex(progress: Float): Float {
    return when {
        progress < 0.42f -> VISIBLE_STACK_DEPTH + 1f
        progress < 0.72f -> {
            val t = ((progress - 0.42f) / 0.30f).coerceIn(0f, 1f)
            val eased = t * t * (3f - 2f * t)
            lerp(VISIBLE_STACK_DEPTH + 1f, 0.5f, eased)
        }
        else -> 0.5f
    }
}

private fun lerp(start: Float, end: Float, fraction: Float): Float {
    return start + (end - start) * fraction
}
