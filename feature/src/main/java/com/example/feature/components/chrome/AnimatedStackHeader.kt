package com.example.feature.components.chrome

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.core.ui.theme.DeckTheme
import com.example.feature.R
import com.example.feature.state.LoopingCardStackState

@Composable
fun AnimatedStackHeader(
    state: LoopingCardStackState,
    modifier: Modifier = Modifier
) {
    val entrance = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        entrance.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 680, easing = FastOutSlowInEasing)
        )
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .graphicsLayer {
                alpha = entrance.value
                translationY = (1f - entrance.value) * 18f
            }
    ) {
        Text(
            text = stringResource(R.string.stack_screen_eyebrow),
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.SemiBold
            ),
            color = DeckTheme.accent
        )
        AnimatedContent(
            targetState = state.frontCard()?.id,
            transitionSpec = {
                (fadeIn(tween(280)) + slideInVertically { it / 3 }) togetherWith
                    (fadeOut(tween(220)) + slideOutVertically { -it / 4 })
            },
            modifier = Modifier.padding(top = 8.dp),
            label = "front-card-title"
        ) { cardId ->
            val card = state.cards.find { it.id == cardId }
            Text(
                text = card?.let { stringResource(it.titleRes) }
                    ?: stringResource(R.string.stack_screen_title),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        StackProgressIndicator(
            activeIndex = 0,
            total = state.totalCards(),
            modifier = Modifier.padding(top = 14.dp)
        )
    }
}
