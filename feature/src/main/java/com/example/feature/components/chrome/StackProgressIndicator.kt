package com.example.feature.components.chrome

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.example.core.ui.theme.CatalogInk
import com.example.core.ui.theme.CatalogMuted

@Composable
internal fun StackProgressIndicator(
    activeIndex: Int,
    total: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(total) { index ->
            val isActive = index == activeIndex
            Box(
                modifier = Modifier
                    .size(if (isActive) 7.dp else 5.dp)
                    .graphicsLayer {
                        alpha = if (isActive) 1f else 0.35f
                    },
                contentAlignment = Alignment.Center
            ) {
                Surface(
                    shape = CircleShape,
                    color = if (isActive) CatalogInk else CatalogMuted,
                    modifier = Modifier.matchParentSize()
                ) {}
            }
        }
    }
}
