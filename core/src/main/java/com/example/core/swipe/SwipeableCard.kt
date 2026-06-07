package com.example.core.swipe

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.util.VelocityTracker
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import com.example.core.model.SwipeDirection
import kotlinx.coroutines.launch

@Composable
fun SwipeableCard(
    state: SwipeCardState,
    enabled: Boolean,
    onSwipeAttempt: (SwipeDirection) -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .onSizeChanged { size: IntSize ->
                state.updateSize(size.width.toFloat(), size.height.toFloat())
            }
            .swipeCardTransform(state)
            .swipeCardDrag(state, enabled, onSwipeAttempt),
        content = content
    )
}

fun Modifier.swipeCardTransform(state: SwipeCardState): Modifier = this.graphicsLayer {
    translationX = state.offsetX
    translationY = state.offsetY
    rotationZ = state.totalRotation
    scaleX = state.scale
    scaleY = state.scale
    cameraDistance = 12f * density
}

@Composable
fun Modifier.swipeCardDrag(
    state: SwipeCardState,
    enabled: Boolean,
    onSwipeAttempt: (SwipeDirection) -> Unit
): Modifier {
    val scope = rememberCoroutineScope()
    return pointerInput(enabled) {
        if (enabled.not()) return@pointerInput
        val velocityTracker = VelocityTracker()
        detectDragGestures(
            onDragStart = {
                velocityTracker.resetTracking()
                state.onDragStart()
            },
            onDragEnd = {
                val velocity = velocityTracker.calculateVelocity()
                state.onDragEnd()
                scope.launch {
                    finishDrag(state, velocity.x, velocity.y, onSwipeAttempt)
                }
            },
            onDragCancel = {
                val velocity = velocityTracker.calculateVelocity()
                state.onDragEnd()
                scope.launch {
                    finishDrag(state, velocity.x, velocity.y, onSwipeAttempt)
                }
            },
            onDrag = { change, dragAmount ->
                change.consume()
                velocityTracker.addPosition(change.uptimeMillis, change.position)
                state.onDrag(dragAmount.x, dragAmount.y)
            }
        )
    }
}

private suspend fun finishDrag(
    state: SwipeCardState,
    velocityX: Float,
    velocityY: Float,
    onSwipeAttempt: (SwipeDirection) -> Unit
) {
    val direction = state.resolveDirection(velocityX, velocityY)
    if (direction != null) {
        onSwipeAttempt(direction)
    } else {
        state.snapBack()
    }
}
