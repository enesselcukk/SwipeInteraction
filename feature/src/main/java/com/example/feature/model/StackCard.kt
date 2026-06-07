package com.example.feature.model

import androidx.compose.ui.graphics.Color
import com.example.feature.R

enum class StackCardPattern {
    VerticalLines,
    Grid,
    DiagonalLines,
    Dots,
    Waves
}

data class StackCard(
    val id: Int,
    val titleRes: Int,
    val descriptionRes: Int,
    val backgroundColor: Color,
    val contentColor: Color,
    val patternColor: Color,
    val pattern: StackCardPattern
)

fun defaultStackCards(): List<StackCard> = listOf(
    StackCard(
        id = 0,
        titleRes = R.string.stack_card_craft_notes_title,
        descriptionRes = R.string.stack_card_craft_notes_desc,
        backgroundColor = Color(0xFF1A1A1A),
        contentColor = Color(0xFFF5F5F5),
        patternColor = Color(0x55FFFFFF),
        pattern = StackCardPattern.DiagonalLines
    ),
    StackCard(
        id = 1,
        titleRes = R.string.stack_card_working_knowledge_title,
        descriptionRes = R.string.stack_card_working_knowledge_desc,
        backgroundColor = Color(0xFFE85D04),
        contentColor = Color(0xFFFFF8F0),
        patternColor = Color(0x66FFFFFF),
        pattern = StackCardPattern.VerticalLines
    ),
    StackCard(
        id = 2,
        titleRes = R.string.stack_card_practical_demo_title,
        descriptionRes = R.string.stack_card_practical_demo_desc,
        backgroundColor = Color(0xFFF5F0E6),
        contentColor = Color(0xFF2C2417),
        patternColor = Color(0x33000000),
        pattern = StackCardPattern.Grid
    ),
    StackCard(
        id = 3,
        titleRes = R.string.stack_card_field_study_title,
        descriptionRes = R.string.stack_card_field_study_desc,
        backgroundColor = Color(0xFF7EB8DA),
        contentColor = Color(0xFF0F2A3D),
        patternColor = Color(0x44000000),
        pattern = StackCardPattern.Dots
    ),
    StackCard(
        id = 4,
        titleRes = R.string.stack_card_design_log_title,
        descriptionRes = R.string.stack_card_design_log_desc,
        backgroundColor = Color(0xFF8CB369),
        contentColor = Color(0xFF1A2E0A),
        patternColor = Color(0x44000000),
        pattern = StackCardPattern.Waves
    )
)
