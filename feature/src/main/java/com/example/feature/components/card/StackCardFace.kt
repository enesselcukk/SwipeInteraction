package com.example.feature.components.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.feature.model.StackCard

@Composable
fun StackCardFace(
    card: StackCard,
    modifier: Modifier = Modifier
) {
    val visual = card.visual
    val gradient = Brush.verticalGradient(
        colors = listOf(visual.gradientTop, visual.gradientBottom)
    )

    key(card.id) {
        Surface(
            modifier = modifier,
            shape = RoundedCornerShape(24.dp),
            shadowElevation = 8.dp,
            color = visual.gradientBottom
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(gradient)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.12f)
                ) {
                    CardPatternCanvas(
                        pattern = visual.pattern,
                        color = visual.patternColor,
                        seed = card.id,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 22.dp, vertical = 20.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = visual.accent.copy(alpha = 0.22f)
                    ) {
                        Text(
                            text = stringResource(card.tagRes),
                            style = MaterialTheme.typography.labelSmall,
                            color = visual.onCard,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                        )
                    }
                    Text(
                        text = stringResource(card.titleRes),
                        style = MaterialTheme.typography.titleMedium,
                        color = visual.onCard,
                        modifier = Modifier.padding(top = 12.dp)
                    )
                    Text(
                        text = stringResource(card.descriptionRes),
                        style = MaterialTheme.typography.bodyMedium,
                        color = visual.onCard.copy(alpha = 0.82f),
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
    }
}
