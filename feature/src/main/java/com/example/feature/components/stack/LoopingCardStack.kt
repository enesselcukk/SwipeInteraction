package com.example.feature.components.stack

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.zIndex
import com.example.core.swipe.swipeCardDrag
import com.example.feature.VISIBLE_STACK_DEPTH
import com.example.feature.layout.CardStackLayoutMapper
import com.example.feature.state.LoopingCardStackState
import kotlinx.coroutines.launch

@Composable
fun LoopingCardStack(
    state: LoopingCardStackState,
    modifier: Modifier = Modifier
) {
    val swipeState = state.swipeState
    val scope = rememberCoroutineScope()
    val dragProgress = state.currentDragProgress()
    val promotionProgress = when {
        swipeState.isDragging -> dragProgress
        state.isCycling -> state.promotionProgress
        else -> 0f
    }

    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        val visibleCards = state.cards.take(VISIBLE_STACK_DEPTH + 1)
        if (visibleCards.isEmpty()) return@Box

        CardStackLayoutMapper.buildRenderStates(
            visibleCards = visibleCards,
            cyclingCardId = state.cyclingCardId,
            isCycling = state.isCycling,
            cycleProgress = state.cycleProgress,
            swipeState = swipeState,
            promotionProgress = promotionProgress
        )
            .sortedBy { it.zIndex }
            .forEach { renderState ->
                key(renderState.card.id) {
                    StackCardLayer(
                        card = renderState.card,
                        transform = renderState.transform,
                        modifier = Modifier
                            .fillMaxWidth(0.88f)
                            .aspectRatio(0.68f)
                            .zIndex(renderState.zIndex)
                            .then(
                                if (renderState.isInteractive) {
                                    Modifier
                                        .onSizeChanged { size: IntSize ->
                                            swipeState.updateSize(
                                                size.width.toFloat(),
                                                size.height.toFloat()
                                            )
                                        }
                                        .swipeCardDrag(
                                            state = swipeState,
                                            enabled = !state.isCycling,
                                            onSwipeAttempt = { direction ->
                                                state.prepareCycle()
                                                scope.launch { state.cycleToBack(direction) }
                                            }
                                        )
                                } else {
                                    Modifier
                                }
                            )
                    )
                }
            }
    }
}
