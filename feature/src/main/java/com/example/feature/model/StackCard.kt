package com.example.feature.model

import androidx.compose.ui.graphics.Color
import com.example.feature.R

enum class StackCardPattern {
    Rings,
    Lattice,
    Strata,
    Scatter,
    Ripple
}

data class StackCardVisual(
    val gradientTop: Color,
    val gradientBottom: Color,
    val onCard: Color,
    val accent: Color,
    val patternColor: Color,
    val pattern: StackCardPattern
)

data class StackCard(
    val id: Int,
    val titleRes: Int,
    val descriptionRes: Int,
    val tagRes: Int,
    val visual: StackCardVisual
)

fun defaultStackCards(): List<StackCard> = listOf(
    StackCard(
        id = 0,
        titleRes = R.string.stack_card_gesture_physics_title,
        descriptionRes = R.string.stack_card_gesture_physics_desc,
        tagRes = R.string.stack_card_tag_physics,
        visual = StackCardVisual(
            gradientTop = Color(0xFF1E1B4B),
            gradientBottom = Color(0xFF312E81),
            onCard = Color(0xFFF0EEFF),
            accent = Color(0xFF818CF8),
            patternColor = Color(0x40FFFFFF),
            pattern = StackCardPattern.Strata
        )
    ),
    StackCard(
        id = 1,
        titleRes = R.string.stack_card_loop_rhythm_title,
        descriptionRes = R.string.stack_card_loop_rhythm_desc,
        tagRes = R.string.stack_card_tag_loop,
        visual = StackCardVisual(
            gradientTop = Color(0xFFFF6B6B),
            gradientBottom = Color(0xFFFF8E53),
            onCard = Color(0xFFFFF5F0),
            accent = Color(0xFFFFD4C2),
            patternColor = Color(0x55FFFFFF),
            pattern = StackCardPattern.Rings
        )
    ),
    StackCard(
        id = 2,
        titleRes = R.string.stack_card_depth_layer_title,
        descriptionRes = R.string.stack_card_depth_layer_desc,
        tagRes = R.string.stack_card_tag_depth,
        visual = StackCardVisual(
            gradientTop = Color(0xFFE0F2FE),
            gradientBottom = Color(0xFF7DD3FC),
            onCard = Color(0xFF0C4A6E),
            accent = Color(0xFF0284C7),
            patternColor = Color(0x35000000),
            pattern = StackCardPattern.Lattice
        )
    ),
    StackCard(
        id = 3,
        titleRes = R.string.stack_card_direction_paths_title,
        descriptionRes = R.string.stack_card_direction_paths_desc,
        tagRes = R.string.stack_card_tag_direction,
        visual = StackCardVisual(
            gradientTop = Color(0xFF064E3B),
            gradientBottom = Color(0xFF047857),
            onCard = Color(0xFFECFDF5),
            accent = Color(0xFF6EE7B7),
            patternColor = Color(0x45FFFFFF),
            pattern = StackCardPattern.Scatter
        )
    ),
    StackCard(
        id = 4,
        titleRes = R.string.stack_card_motion_craft_title,
        descriptionRes = R.string.stack_card_motion_craft_desc,
        tagRes = R.string.stack_card_tag_motion,
        visual = StackCardVisual(
            gradientTop = Color(0xFF7C3AED),
            gradientBottom = Color(0xFFA78BFA),
            onCard = Color(0xFFF5F3FF),
            accent = Color(0xFFC4B5FD),
            patternColor = Color(0x50FFFFFF),
            pattern = StackCardPattern.Ripple
        )
    )
)
