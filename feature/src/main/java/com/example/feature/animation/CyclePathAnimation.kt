package com.example.feature.animation

import com.example.core.model.SwipeDirection
import com.example.feature.model.StackCardTransform

internal const val CYCLE_DURATION_MS = 920

internal data class CyclePathSample(
    val offsetX: Float,
    val offsetY: Float,
    val scale: Float,
    val rotation: Float
)

private data class CycleKeyframe(
    val progress: Float,
    val offsetX: Float,
    val offsetY: Float,
    val scale: Float,
    val rotation: Float
)

internal object CyclePathAnimation {

    fun frameAt(
        direction: SwipeDirection,
        cardWidth: Float,
        cardHeight: Float,
        target: StackCardTransform,
        progress: Float,
        startOffsetX: Float,
        startOffsetY: Float,
        startScale: Float,
        startRotation: Float
    ): CyclePathSample {
        val path = buildPath(direction, cardWidth, cardHeight, target)
        return sample(path, progress, startOffsetX, startOffsetY, startScale, startRotation)
    }

    private fun sample(
        path: List<CycleKeyframe>,
        progress: Float,
        startOffsetX: Float,
        startOffsetY: Float,
        startScale: Float,
        startRotation: Float
    ): CyclePathSample {
        val clampedProgress = progress.coerceIn(0f, 1f)
        val fromIndex = path.indexOfLast { it.progress <= clampedProgress }.coerceAtLeast(0)
        val toIndex = (fromIndex + 1).coerceAtMost(path.lastIndex)

        if (fromIndex == toIndex || path[fromIndex].progress == path[toIndex].progress) {
            val keyframe = path[toIndex]
            return CyclePathSample(
                offsetX = if (toIndex == 0) startOffsetX else keyframe.offsetX,
                offsetY = if (toIndex == 0) startOffsetY else keyframe.offsetY,
                scale = if (toIndex == 0) startScale else keyframe.scale,
                rotation = if (toIndex == 0) startRotation else keyframe.rotation
            )
        }

        val from = path[fromIndex]
        val to = path[toIndex]
        val segmentT = ((clampedProgress - from.progress) / (to.progress - from.progress))
            .coerceIn(0f, 1f)
        val easedT = segmentT * segmentT * (3f - 2f * segmentT)

        val fromX = if (fromIndex == 0) startOffsetX else from.offsetX
        val fromY = if (fromIndex == 0) startOffsetY else from.offsetY
        val fromScale = if (fromIndex == 0) startScale else from.scale
        val fromRotation = if (fromIndex == 0) startRotation else from.rotation

        return CyclePathSample(
            offsetX = lerp(fromX, to.offsetX, easedT),
            offsetY = lerp(fromY, to.offsetY, easedT),
            scale = lerp(fromScale, to.scale, easedT),
            rotation = lerp(fromRotation, to.rotation, easedT)
        )
    }

    private fun buildPath(
        direction: SwipeDirection,
        cardWidth: Float,
        cardHeight: Float,
        target: StackCardTransform
    ): List<CycleKeyframe> {
        val w = cardWidth.coerceAtLeast(1f)
        val h = cardHeight.coerceAtLeast(1f)

        return when (direction) {
            SwipeDirection.Right -> listOf(
                CycleKeyframe(0f, 0f, 0f, 1f, 0f),
                CycleKeyframe(0.22f, w * 0.52f, -h * 0.04f, 0.985f, 7f),
                CycleKeyframe(0.48f, w * 0.38f, h * 0.06f, 0.968f, 9f),
                CycleKeyframe(0.72f, w * 0.22f, h * 0.12f, 0.958f, 7.5f),
                CycleKeyframe(1f, target.translationX, target.translationY, target.scale, target.rotationZ)
            )

            SwipeDirection.Left -> listOf(
                CycleKeyframe(0f, 0f, 0f, 1f, 0f),
                CycleKeyframe(0.22f, -w * 0.52f, -h * 0.04f, 0.985f, -7f),
                CycleKeyframe(0.48f, -w * 0.38f, h * 0.06f, 0.968f, -9f),
                CycleKeyframe(0.72f, -w * 0.22f, h * 0.12f, 0.958f, -7.5f),
                CycleKeyframe(1f, target.translationX, target.translationY, target.scale, target.rotationZ)
            )

            SwipeDirection.Down -> listOf(
                CycleKeyframe(0f, 0f, 0f, 1f, 0f),
                CycleKeyframe(0.22f, w * 0.28f, h * 0.22f, 0.985f, 5f),
                CycleKeyframe(0.48f, w * 0.32f, h * 0.10f, 0.968f, 7f),
                CycleKeyframe(0.72f, w * 0.22f, h * 0.14f, 0.958f, 6.5f),
                CycleKeyframe(1f, target.translationX, target.translationY, target.scale, target.rotationZ)
            )

            SwipeDirection.Up -> listOf(
                CycleKeyframe(0f, 0f, 0f, 1f, 0f),
                CycleKeyframe(0.22f, -w * 0.28f, -h * 0.16f, 0.985f, -5f),
                CycleKeyframe(0.48f, -w * 0.32f, h * 0.02f, 0.968f, -7f),
                CycleKeyframe(0.72f, -w * 0.22f, h * 0.10f, 0.958f, -6.5f),
                CycleKeyframe(1f, target.translationX, target.translationY, target.scale, target.rotationZ)
            )
        }
    }

    private fun lerp(start: Float, end: Float, fraction: Float): Float {
        return start + (end - start) * fraction
    }
}
