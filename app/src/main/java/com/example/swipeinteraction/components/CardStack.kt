package com.example.swipeinteraction.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import com.example.swipeinteraction.model.SwipeDirection
import com.example.swipeinteraction.model.TutorialCard
import com.example.swipeinteraction.swipe.SwipeCardState
import com.example.swipeinteraction.swipe.SwipeableCard
import kotlin.math.abs

@Composable
fun CardStack(
    cards: List<TutorialCard>,
    swipeState: SwipeCardState,
    onSwipeAttempt: (SwipeDirection) -> Unit,
    onTryAgain: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dragProgress = if (swipeState.cardWidth > 0f && swipeState.cardHeight > 0f) {
        maxOf(
            abs(swipeState.offsetX) / swipeState.cardWidth,
            abs(swipeState.offsetY) / swipeState.cardHeight
        ).coerceIn(0f, 1f)
    } else {
        0f
    }
    val backScale = 0.94f + 0.06f * dragProgress
    val backOffset = 20f * (1f - dragProgress)

    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        if (cards.isEmpty()) {
            TutorialCompleteFace(
                onTryAgain = onTryAgain,
                modifier = Modifier
                    .fillMaxWidth(0.92f)
                    .aspectRatio(0.7f)
            )
            return@Box
        }

        cards.getOrNull(2)?.let { deepCard ->
            TutorialCardFace(
                card = deepCard,
                isBackground = true,
                modifier = Modifier
                    .fillMaxWidth(0.92f)
                    .aspectRatio(0.7f)
                    .graphicsLayer {
                        scaleX = 0.88f
                        scaleY = 0.88f
                        translationY = 36f
                        alpha = 0.88f
                    }
            )
        }

        cards.getOrNull(1)?.let { nextCard ->
            TutorialCardFace(
                card = nextCard,
                isBackground = true,
                modifier = Modifier
                    .fillMaxWidth(0.92f)
                    .aspectRatio(0.7f)
                    .graphicsLayer {
                        scaleX = backScale
                        scaleY = backScale
                        translationY = backOffset
                    }
            )
        }

        SwipeableCard(
            state = swipeState,
            enabled = true,
            onSwipeAttempt = onSwipeAttempt,
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .aspectRatio(0.7f)
        ) {
            TutorialCardFace(
                card = cards.first(),
                modifier = Modifier.matchParentSize()
            )
        }
    }
}
