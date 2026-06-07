package com.example.feature.components.card

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import com.example.feature.model.StackCardPattern
import kotlin.math.PI
import kotlin.math.min
import kotlin.math.sin
import kotlin.random.Random

@Composable
internal fun CardPatternCanvas(
    pattern: StackCardPattern,
    color: Color,
    seed: Int,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        when (pattern) {
            StackCardPattern.Rings -> drawRings(color, seed)
            StackCardPattern.Lattice -> drawLattice(color)
            StackCardPattern.Strata -> drawStrata(color)
            StackCardPattern.Scatter -> drawScatter(color, seed)
            StackCardPattern.Ripple -> drawRipple(color)
        }
    }
}

private fun DrawScope.drawRings(color: Color, seed: Int) {
    val center = Offset(size.width * 0.62f, size.height * 0.48f)
    val maxRadius = min(size.width, size.height) * 0.55f
    val ringCount = 6
    repeat(ringCount) { index ->
        val radius = maxRadius * (index + 1) / ringCount
        val alphaScale = 0.35f + (index % 3) * 0.15f
        drawCircle(
            color = color.copy(alpha = color.alpha * alphaScale),
            radius = radius,
            center = center,
            style = Stroke(width = 2f + (seed % 2))
        )
    }
}

private fun DrawScope.drawLattice(color: Color) {
    val cell = size.width / 9f
    var row = 0
    var y = cell / 2f
    while (y < size.height) {
        var x = if (row % 2 == 0) cell / 2f else cell
        while (x < size.width) {
            val shade = ((row + x.toInt()) % 5) / 8f
            drawRect(
                color = color.copy(alpha = color.alpha * (0.35f + shade)),
                topLeft = Offset(x, y),
                size = Size(cell * 0.68f, cell * 0.68f)
            )
            x += cell
        }
        y += cell
        row++
    }
}

private fun DrawScope.drawStrata(color: Color) {
    val bandCount = 5
    val bandHeight = size.height / bandCount
    repeat(bandCount) { index ->
        val offset = index * bandHeight * 0.18f
        var x = -size.height + offset
        while (x < size.width + size.height) {
            drawLine(
                color = color.copy(alpha = color.alpha * (0.5f + index * 0.1f)),
                start = Offset(x, size.height),
                end = Offset(x + size.height, 0f),
                strokeWidth = 2f
            )
            x += 22f
        }
    }
}

private fun DrawScope.drawScatter(color: Color, seed: Int) {
    val random = Random(seed)
    repeat(38) {
        val radius = random.nextFloat() * 4.5f + 2f
        drawCircle(
            color = color.copy(alpha = color.alpha * (0.45f + random.nextFloat() * 0.55f)),
            radius = radius,
            center = Offset(
                random.nextFloat() * size.width,
                random.nextFloat() * size.height
            )
        )
    }
}

private fun DrawScope.drawRipple(color: Color) {
    val origin = Offset(size.width * 0.35f, size.height * 0.6f)
    repeat(4) { ring ->
        val radius = size.width * (0.12f + ring * 0.14f)
        drawCircle(
            color = color.copy(alpha = color.alpha * (0.9f - ring * 0.18f)),
            radius = radius,
            center = origin,
            style = Stroke(width = 2.5f)
        )
    }
    val path = Path()
    val amplitude = size.height * 0.1f
    val wavelength = size.width / 2.8f
    path.moveTo(0f, size.height * 0.72f)
    var x = 0f
    while (x <= size.width) {
        val y = size.height * 0.72f + sin((x / wavelength) * PI.toFloat() * 2f) * amplitude
        path.lineTo(x, y)
        x += 6f
    }
    drawPath(path, color = color.copy(alpha = color.alpha * 0.7f), style = Stroke(width = 2.5f))
}
