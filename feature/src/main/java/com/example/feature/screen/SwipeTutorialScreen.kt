package com.example.feature.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.core.model.SwipeDirection
import com.example.core.swipe.rememberSwipeCardState
import com.example.feature.components.CardStack
import com.example.feature.model.defaultTutorialCards
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SwipeTutorialScreen(
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    val swipeState = rememberSwipeCardState()
    val allCards = remember { defaultTutorialCards() }
    val cards = remember { mutableStateListOf(*allCards.toTypedArray()) }
    var tutorialSession by remember { mutableIntStateOf(0) }
    val currentCard = cards.firstOrNull()

    LaunchedEffect(tutorialSession, currentCard?.id) {
        swipeState.reset()
    }

    LaunchedEffect(tutorialSession, currentCard?.id) {
        val card = currentCard ?: return@LaunchedEffect
        delay(1500)
        while (cards.firstOrNull()?.id == card.id) {
            if (!swipeState.isDragging && !swipeState.isAnimating) {
                swipeState.playHintAnimation(card.action.direction)
            }
            delay(2800)
        }
    }

    fun handleSwipe(direction: SwipeDirection) {
        if (cards.isEmpty()) return
        val expected = cards.first().action.direction
        scope.launch {
            if (direction == expected) {
                swipeState.dismiss(direction)
                cards.removeAt(0)
            } else {
                swipeState.snapBack()
            }
        }
    }

    fun resetTutorial() {
        swipeState.reset()
        cards.clear()
        cards.addAll(allCards)
        tutorialSession++
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = 24.dp)
    ) {
        CardStack(
            cards = cards,
            swipeState = swipeState,
            onSwipeAttempt = ::handleSwipe,
            onTryAgain = ::resetTutorial,
            modifier = Modifier
                .weight(1f)
                .padding(top = 24.dp, bottom = 24.dp)
                .align(Alignment.CenterHorizontally)
        )
    }
}
