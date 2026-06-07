package com.example.core.swipe

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.core.model.SwipeDirection
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.hypot
import kotlin.math.sqrt

@Stable
class SwipeCardState {
    var offsetX by mutableFloatStateOf(0f)
    var offsetY by mutableFloatStateOf(0f)
    var cardWidth by mutableFloatStateOf(0f)
    var cardHeight by mutableFloatStateOf(0f)
    var isDragging by mutableStateOf(false)
    var isAnimating by mutableStateOf(false)
    var scale by mutableFloatStateOf(1f)
    var extraRotation by mutableFloatStateOf(0f)
    var lockDragRotation by mutableStateOf(false)

    private var animationGeneration by mutableIntStateOf(0)

    val rotation: Float
        get() = if (cardWidth > 0f) {
            (offsetX / cardWidth).coerceIn(-1f, 1f) * 12f
        } else {
            0f
        }

    val totalRotation: Float
        get() = if (lockDragRotation) extraRotation else rotation + extraRotation

    fun updateSize(width: Float, height: Float) {
        cardWidth = width
        cardHeight = height
    }

    fun onDragStart() {
        animationGeneration++
        isAnimating = false
        isDragging = true
    }

    fun onDrag(deltaX: Float, deltaY: Float) {
        offsetX += deltaX
        offsetY += deltaY
    }

    fun onDragEnd() {
        isDragging = false
    }

    suspend fun snapBack() {
        isAnimating = true
        animateTo(0f, 0f, snapSpec())
        isAnimating = false
    }


    fun reset() {
        animationGeneration++
        offsetX = 0f
        offsetY = 0f
        scale = 1f
        extraRotation = 0f
        lockDragRotation = false
        isDragging = false
        isAnimating = false
    }

    fun resolveDirection(velocityX: Float = 0f, velocityY: Float = 0f): SwipeDirection? {
        if (cardWidth <= 0f || cardHeight <= 0f) return null

        val horizontalDominance = abs(offsetX) / cardWidth
        val verticalDominance = abs(offsetY) / cardHeight
        val combinedProgress = sqrt(
            horizontalDominance * horizontalDominance + verticalDominance * verticalDominance
        )

        val fromPosition = when {
            combinedProgress >= 0.35f -> dominantAxisDirection()
            horizontalDominance >= 0.22f || verticalDominance >= 0.22f -> dominantAxisDirection()
            else -> null
        }

        if (fromPosition != null) return fromPosition

        if (hypot(velocityX.toDouble(), velocityY.toDouble()) >= 900f) {
            return dominantVelocityDirection(velocityX, velocityY)
        }

        return null
    }

    private fun dominantAxisDirection(): SwipeDirection {
        val horizontalDominance = abs(offsetX) / cardWidth
        val verticalDominance = abs(offsetY) / cardHeight
        return if (horizontalDominance >= verticalDominance) {
            if (offsetX >= 0f) SwipeDirection.Right else SwipeDirection.Left
        } else {
            if (offsetY >= 0f) SwipeDirection.Down else SwipeDirection.Up
        }
    }

    private fun dominantVelocityDirection(velocityX: Float, velocityY: Float): SwipeDirection {
        return if (abs(velocityX) >= abs(velocityY)) {
            if (velocityX >= 0f) SwipeDirection.Right else SwipeDirection.Left
        } else {
            if (velocityY >= 0f) SwipeDirection.Down else SwipeDirection.Up
        }
    }

    private suspend fun animateTo(
        targetX: Float,
        targetY: Float,
        animationSpec: AnimationSpec<Float>
    ) {
        val generation = animationGeneration
        val animX = Animatable(offsetX)
        val animY = Animatable(offsetY)
        coroutineScope {
            launch {
                animX.animateTo(targetX, animationSpec) {
                    if (generation == animationGeneration) {
                        offsetX = value
                    }
                }
            }
            launch {
                animY.animateTo(targetY, animationSpec) {
                    if (generation == animationGeneration) {
                        offsetY = value
                    }
                }
            }
        }
    }

    private fun snapSpec() = spring<Float>(
        dampingRatio = Spring.DampingRatioMediumBouncy,
        stiffness = Spring.StiffnessMedium
    )
}
