package com.example.swipeinteraction

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.core.ui.theme.SwipeInteractionTheme
import com.example.feature.screen.LoopingCardStackScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SwipeInteractionTheme {
                LoopingCardStackScreen()
            }
        }
    }
}
