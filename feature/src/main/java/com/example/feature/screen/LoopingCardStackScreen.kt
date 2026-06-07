package com.example.feature.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.feature.components.chrome.AnimatedStackHeader
import com.example.feature.components.stack.LoopingCardStack
import com.example.feature.state.rememberLoopingCardStackState

@Composable
fun LoopingCardStackScreen(
    modifier: Modifier = Modifier
) {
    val stackState = rememberLoopingCardStackState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = 28.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        AnimatedStackHeader(state = stackState)
        LoopingCardStack(
            state = stackState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(bottom = 4.dp)
        )
    }
}
