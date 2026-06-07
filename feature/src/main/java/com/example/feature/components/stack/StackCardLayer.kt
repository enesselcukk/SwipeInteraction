package com.example.feature.components.stack

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import com.example.feature.components.card.StackCardFace
import com.example.feature.model.StackCard
import com.example.feature.model.StackCardTransform

@Composable
internal fun StackCardLayer(
    card: StackCard,
    transform: StackCardTransform,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .graphicsLayer {
                scaleX = transform.scale
                scaleY = transform.scale
                translationX = transform.translationX
                translationY = transform.translationY
                rotationZ = transform.rotationZ
                alpha = transform.alpha
                cameraDistance = 12f * density
            }
    ) {
        StackCardFace(
            card = card,
            modifier = Modifier.matchParentSize()
        )
    }
}
