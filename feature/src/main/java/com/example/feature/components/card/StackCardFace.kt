package com.example.feature.components.card

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.feature.model.StackCard

@Composable
fun StackCardFace(
    card: StackCard,
    modifier: Modifier = Modifier
) {
    key(card.id) {
        Surface(
            modifier = modifier,
            shape = RoundedCornerShape(20.dp),
            shadowElevation = 6.dp,
            color = card.backgroundColor
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.15f)
                ) {
                    CardPatternCanvas(
                        pattern = card.pattern,
                        color = card.patternColor,
                        seed = card.id,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 22.dp, vertical = 20.dp)
                ) {
                    Text(
                        text = stringResource(card.titleRes),
                        color = card.contentColor,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Normal,
                        fontSize = 24.sp,
                        lineHeight = 30.sp
                    )
                    Text(
                        text = stringResource(card.descriptionRes),
                        color = card.contentColor.copy(alpha = 0.82f),
                        fontSize = 15.sp,
                        lineHeight = 22.sp,
                        modifier = Modifier.padding(top = 10.dp)
                    )
                }
            }
        }
    }
}
