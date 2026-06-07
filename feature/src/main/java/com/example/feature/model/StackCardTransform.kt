package com.example.feature.model

import com.example.core.model.SwipeDirection

data class StackCardTransform(
    val scale: Float,
    val translationX: Float,
    val translationY: Float,
    val rotationZ: Float,
    val alpha: Float
)

private val stackTransforms = listOf(
    StackCardTransform(scale = 1f, translationX = 0f, translationY = 0f, rotationZ = 0f, alpha = 1f),
    StackCardTransform(scale = 0.988f, translationX = 9f, translationY = 7f, rotationZ = 1.8f, alpha = 1f),
    StackCardTransform(scale = 0.976f, translationX = 16f, translationY = 12f, rotationZ = 3.2f, alpha = 1f),
    StackCardTransform(scale = 0.964f, translationX = 22f, translationY = 16f, rotationZ = 4.4f, alpha = 1f),
    StackCardTransform(scale = 0.952f, translationX = 27f, translationY = 20f, rotationZ = 5.6f, alpha = 1f)
)

fun stackTransformForDepth(depth: Int): StackCardTransform {
    return stackTransforms.getOrElse(depth) { stackTransforms.last() }
}

fun mirrorStackTransform(transform: StackCardTransform): StackCardTransform {
    return transform.copy(
        translationX = -transform.translationX,
        rotationZ = -transform.rotationZ
    )
}

fun backStackTarget(direction: SwipeDirection): StackCardTransform {
    val base = stackTransformForDepth(stackTransforms.lastIndex)
    return when (direction) {
        SwipeDirection.Left, SwipeDirection.Up -> mirrorStackTransform(base)
        SwipeDirection.Right, SwipeDirection.Down -> base
    }
}

fun lerpTransform(
    from: StackCardTransform,
    to: StackCardTransform,
    progress: Float
): StackCardTransform {
    val t = progress.coerceIn(0f, 1f)
    return StackCardTransform(
        scale = from.scale + (to.scale - from.scale) * t,
        translationX = from.translationX + (to.translationX - from.translationX) * t,
        translationY = from.translationY + (to.translationY - from.translationY) * t,
        rotationZ = from.rotationZ + (to.rotationZ - from.rotationZ) * t,
        alpha = from.alpha + (to.alpha - from.alpha) * t
    )
}
