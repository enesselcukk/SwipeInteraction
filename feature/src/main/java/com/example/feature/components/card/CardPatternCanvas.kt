package com.example.feature.components.card

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import com.example.feature.model.StackCardPattern
import kotlin.math.PI
import kotlin.math.sin
import kotlin.random.Random
import androidx.compose.ui.graphics.drawscope.DrawScope

@Composable
internal fun CardPatternCanvas(
    pattern: StackCardPattern,
    color: Color,
    seed: Int,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        when (pattern) {
            StackCardPattern.VerticalLines -> drawVerticalLines(color, seed)
            StackCardPattern.Grid -> drawGrid(color)
            StackCardPattern.DiagonalLines -> drawDiagonalLines(color)
            StackCardPattern.Dots -> drawDots(color, seed)
            StackCardPattern.Waves -> drawWaves(color)
        }
    }
}

private fun DrawScope.drawVerticalLines(
    color: Color,
    seed: Int
) {
    val lineCount = 28
    val spacing = size.width / lineCount
    repeat(lineCount) { index ->
        val heightFactor = 0.35f + ((index * 17 + seed * 3) % 11) / 18f
        val lineHeight = size.height * heightFactor
        val x = spacing * index + spacing / 2f
        drawLine(
            color = color,
            start = Offset(x, size.height - lineHeight),
            end = Offset(x, size.height),
            strokeWidth = 2.5f
        )
    }
}

private fun DrawScope.drawGrid(color: Color) {
    val cell = size.width / 10f
    var row = 0
    var y = cell / 2f
    while (y < size.height) {
        var x = if (row % 2 == 0) cell / 2f else cell
        while (x < size.width) {
            val shade = ((row + x.toInt()) % 5) / 8f
            drawRect(
                color = color.copy(alpha = color.alpha * (0.4f + shade)),
                topLeft = Offset(x, y),
                size = Size(cell * 0.72f, cell * 0.72f)
            )
            x += cell
        }
        y += cell
        row++
    }
}

private fun DrawScope.drawDiagonalLines(color: Color) {
    val spacing = 18f
    var offset = -size.height
    while (offset < size.width + size.height) {
        drawLine(
            color = color,
            start = Offset(offset, size.height),
            end = Offset(offset + size.height, 0f),
            strokeWidth = 2f
        )
        offset += spacing
    }
}

private fun DrawScope.drawDots(color: Color, seed: Int) {
    val random = Random(seed)
    repeat(42) {
        val radius = random.nextFloat() * 5f + 2f
        drawCircle(
            color = color.copy(alpha = color.alpha * (0.5f + random.nextFloat() * 0.5f)),
            radius = radius,
            center = Offset(
                random.nextFloat() * size.width,
                random.nextFloat() * size.height
            )
        )
    }
}

private fun DrawScope.drawWaves(color: Color) {
    val path = Path()
    val amplitude = size.height * 0.12f
    val wavelength = size.width / 3f
    path.moveTo(0f, size.height * 0.55f)
    var x = 0f
    while (x <= size.width) {
        val y = size.height * 0.55f + sin((x / wavelength) * PI.toFloat() * 2f) * amplitude
        path.lineTo(x, y)
        x += 8f
    }
    drawPath(path, color = color, style = Stroke(width = 3f))
}
