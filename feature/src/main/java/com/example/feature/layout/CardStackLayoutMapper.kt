package com.example.feature.layout

import com.example.core.swipe.SwipeCardState
import com.example.feature.VISIBLE_STACK_DEPTH
import com.example.feature.animation.cyclingCardZIndex
import com.example.feature.model.CardRenderState
import com.example.feature.model.StackCard
import com.example.feature.model.StackCardTransform
import com.example.feature.model.lerpTransform
import com.example.feature.model.stackTransformForDepth

internal object CardStackLayoutMapper {

    fun buildRenderStates(
        visibleCards: List<StackCard>,
        cyclingCardId: Int?,
        isCycling: Boolean,
        cycleProgress: Float,
        swipeState: SwipeCardState,
        promotionProgress: Float
    ): List<CardRenderState> {
        return visibleCards.mapIndexed { index, card ->
            val stackDepth = index.coerceAtMost(VISIBLE_STACK_DEPTH)
            val isCyclingCard = isCycling && card.id == cyclingCardId
            val isInteractive = index == 0 && !isCycling

            val transform = when {
                isCyclingCard -> cyclingCardTransform(swipeState)
                index == 0 -> frontCardTransform(swipeState)
                promotionProgress > 0f -> lerpTransform(
                    stackTransformForDepth(stackDepth),
                    stackTransformForDepth(stackDepth - 1),
                    promotionProgress
                )
                else -> stackTransformForDepth(stackDepth)
            }

            val zIndex = if (isCyclingCard) {
                cyclingCardZIndex(cycleProgress)
            } else {
                (VISIBLE_STACK_DEPTH + 1 - index).toFloat()
            }

            CardRenderState(
                card = card,
                transform = transform,
                zIndex = zIndex,
                isInteractive = isInteractive
            )
        }
    }

    private fun cyclingCardTransform(swipeState: SwipeCardState): StackCardTransform {
        return StackCardTransform(
            scale = swipeState.scale,
            translationX = swipeState.offsetX,
            translationY = swipeState.offsetY,
            rotationZ = swipeState.extraRotation,
            alpha = 1f
        )
    }

    private fun frontCardTransform(swipeState: SwipeCardState): StackCardTransform {
        val isMoved = swipeState.isDragging ||
            swipeState.isAnimating ||
            swipeState.offsetX != 0f ||
            swipeState.offsetY != 0f ||
            swipeState.scale != 1f ||
            swipeState.extraRotation != 0f

        return if (isMoved) {
            StackCardTransform(
                scale = swipeState.scale,
                translationX = swipeState.offsetX,
                translationY = swipeState.offsetY,
                rotationZ = swipeState.totalRotation,
                alpha = 1f
            )
        } else {
            stackTransformForDepth(0)
        }
    }
}
