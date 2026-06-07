package com.example.feature.state

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.Snapshot
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.core.model.SwipeDirection
import com.example.core.swipe.SwipeCardState
import com.example.feature.animation.CYCLE_DURATION_MS
import com.example.feature.animation.CyclePathAnimation
import com.example.feature.model.StackCard
import com.example.feature.model.backStackTarget
import com.example.feature.model.defaultStackCards
import kotlin.math.abs
import kotlin.math.max

@Stable
class LoopingCardStackState(
    initialCards: List<StackCard> = defaultStackCards()
) {
    val swipeState = SwipeCardState()
    val cards: SnapshotStateList<StackCard> = mutableStateListOf(*initialCards.toTypedArray())

    var isCycling by mutableStateOf(false)
    var promotionProgress by mutableFloatStateOf(0f)
    var cycleProgress by mutableFloatStateOf(0f)
    var cyclingCardId by mutableStateOf<Int?>(null)

    private val cycleAnimatable = Animatable(0f)

    fun frontCard(): StackCard? = cards.firstOrNull()

    fun totalCards(): Int = cards.size

    fun currentDragProgress(): Float {
        val width = swipeState.cardWidth
        val height = swipeState.cardHeight
        if (width <= 0f || height <= 0f) return 0f
        return max(
            abs(swipeState.offsetX) / width,
            abs(swipeState.offsetY) / height
        ).coerceIn(0f, 1f)
    }

    fun prepareCycle() {
        if (isCycling || cards.isEmpty()) return
        cyclingCardId = cards.first().id
        isCycling = true
        promotionProgress = currentDragProgress()
    }

    suspend fun cycleToBack(direction: SwipeDirection) {
        if (cards.isEmpty() || swipeState.isAnimating) return
        if (!isCycling) {
            prepareCycle()
        }

        swipeState.isAnimating = true
        val startOffsetX = swipeState.offsetX
        val startOffsetY = swipeState.offsetY
        val startScale = swipeState.scale
        val startRotation = swipeState.totalRotation
        val startPromotion = promotionProgress.coerceAtLeast(currentDragProgress())
        val target = backStackTarget(direction)

        swipeState.lockDragRotation = true
        swipeState.extraRotation = startRotation

        cycleAnimatable.snapTo(0f)
        cycleProgress = 0f
        promotionProgress = startPromotion

        cycleAnimatable.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = CYCLE_DURATION_MS,
                easing = LinearOutSlowInEasing
            )
        ) {
            val progress = value
            cycleProgress = progress

            val promotionT = ((progress - 0.2f) / 0.8f).coerceIn(0f, 1f)
            val easedPromotion = promotionT * promotionT * (3f - 2f * promotionT)
            promotionProgress = startPromotion + (1f - startPromotion) * easedPromotion

            val sample = CyclePathAnimation.frameAt(
                direction = direction,
                cardWidth = swipeState.cardWidth,
                cardHeight = swipeState.cardHeight,
                target = target,
                progress = progress,
                startOffsetX = startOffsetX,
                startOffsetY = startOffsetY,
                startScale = startScale,
                startRotation = startRotation
            )

            swipeState.offsetX = sample.offsetX
            swipeState.offsetY = sample.offsetY
            swipeState.scale = sample.scale
            swipeState.extraRotation = sample.rotation
        }

        finishCycle()
    }

    private fun finishCycle() {
        Snapshot.withMutableSnapshot {
            rotateFrontToBack()
            swipeState.lockDragRotation = false
            swipeState.reset()
            cycleProgress = 0f
            promotionProgress = 0f
            cyclingCardId = null
            isCycling = false
        }
    }

    private fun rotateFrontToBack() {
        if (cards.isEmpty()) return
        val frontCard = cards.removeAt(0)
        cards.add(frontCard)
    }
}

@Composable
fun rememberLoopingCardStackState(
    initialCards: List<StackCard> = defaultStackCards()
): LoopingCardStackState = remember { LoopingCardStackState(initialCards) }
