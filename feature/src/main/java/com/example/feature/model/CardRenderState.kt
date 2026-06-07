package com.example.feature.model

internal data class CardRenderState(
    val card: StackCard,
    val transform: StackCardTransform,
    val zIndex: Float,
    val isInteractive: Boolean
)