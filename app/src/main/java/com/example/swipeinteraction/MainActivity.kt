package com.example.swipeinteraction

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.example.swipeinteraction.screen.SwipeTutorialScreen
import com.example.swipeinteraction.ui.theme.SwipeInteractionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SwipeInteractionTheme {
                SwipeTutorialScreen(modifier = Modifier.fillMaxSize())
            }
        }
    }
}
