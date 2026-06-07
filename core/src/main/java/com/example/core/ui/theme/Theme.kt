package com.example.core.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DeckLightColorScheme = lightColorScheme(
    primary = DeckTheme.accent,
    onPrimary = DeckTheme.surface,
    primaryContainer = DeckTheme.accentSoft,
    onPrimaryContainer = DeckTheme.ink,
    background = DeckTheme.background,
    onBackground = DeckTheme.ink,
    surface = DeckTheme.surface,
    onSurface = DeckTheme.ink,
    onSurfaceVariant = DeckTheme.muted,
    outline = DeckTheme.divider,
    secondary = CompleteContainer,
    onSecondary = OnCompleteContainer,
    secondaryContainer = CompleteContainerHigh,
    onSecondaryContainer = OnCompleteContainer,
    tertiary = SnoozeContainer,
    onTertiary = OnSnoozeContainer,
    error = DeleteContainer,
    onError = OnDeleteContainer
)

private val DeckDarkColorScheme = darkColorScheme(
    primary = DeckTheme.accent,
    onPrimary = DeckTheme.ink,
    primaryContainer = DeckTheme.accentSoft,
    onPrimaryContainer = DeckTheme.surface,
    background = Color(0xFF0F1219),
    onBackground = Color(0xFFE8ECF4),
    surface = Color(0xFF1A1F2B),
    onSurface = Color(0xFFE8ECF4),
    onSurfaceVariant = Color(0xFF9AA3B5),
    outline = Color(0xFF2E3544)
)

@Composable
fun SwipeInteractionTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DeckDarkColorScheme
        else -> DeckLightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
