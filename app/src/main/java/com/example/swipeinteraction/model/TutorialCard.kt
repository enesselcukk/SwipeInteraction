package com.example.swipeinteraction.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Archive
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.swipeinteraction.R
import com.example.swipeinteraction.ui.theme.ArchiveContainer
import com.example.swipeinteraction.ui.theme.ArchiveContainerHigh
import com.example.swipeinteraction.ui.theme.CompleteContainer
import com.example.swipeinteraction.ui.theme.CompleteContainerHigh
import com.example.swipeinteraction.ui.theme.DeleteContainer
import com.example.swipeinteraction.ui.theme.DeleteContainerHigh
import com.example.swipeinteraction.ui.theme.OnArchiveContainer
import com.example.swipeinteraction.ui.theme.OnCompleteContainer
import com.example.swipeinteraction.ui.theme.OnDeleteContainer
import com.example.swipeinteraction.ui.theme.OnSnoozeContainer
import com.example.swipeinteraction.ui.theme.SnoozeContainer
import com.example.swipeinteraction.ui.theme.SnoozeContainerHigh

enum class SwipeActionType(
    val labelRes: Int,
    val descriptionRes: Int,
    val hintRes: Int,
    val icon: ImageVector,
    val direction: SwipeDirection,
    val containerColor: Color,
    val containerColorHigh: Color,
    val contentColor: Color
) {
    Complete(
        labelRes = R.string.action_complete,
        descriptionRes = R.string.desc_complete,
        hintRes = R.string.hint_complete,
        icon = Icons.Outlined.CheckCircle,
        direction = SwipeDirection.Right,
        containerColor = CompleteContainer,
        containerColorHigh = CompleteContainerHigh,
        contentColor = OnCompleteContainer
    ),
    Snooze(
        labelRes = R.string.action_snooze,
        descriptionRes = R.string.desc_snooze,
        hintRes = R.string.hint_snooze,
        icon = Icons.Outlined.Schedule,
        direction = SwipeDirection.Left,
        containerColor = SnoozeContainer,
        containerColorHigh = SnoozeContainerHigh,
        contentColor = OnSnoozeContainer
    ),
    Archive(
        labelRes = R.string.action_archive,
        descriptionRes = R.string.desc_archive,
        hintRes = R.string.hint_archive,
        icon = Icons.Outlined.Archive,
        direction = SwipeDirection.Down,
        containerColor = ArchiveContainer,
        containerColorHigh = ArchiveContainerHigh,
        contentColor = OnArchiveContainer
    ),
    Delete(
        labelRes = R.string.action_delete,
        descriptionRes = R.string.desc_delete,
        hintRes = R.string.hint_delete,
        icon = Icons.Outlined.Delete,
        direction = SwipeDirection.Up,
        containerColor = DeleteContainer,
        containerColorHigh = DeleteContainerHigh,
        contentColor = OnDeleteContainer
    )
}

data class TutorialCard(
    val id: Int,
    val action: SwipeActionType
)

fun defaultTutorialCards(): List<TutorialCard> = SwipeActionType.entries.mapIndexed { index, action ->
    TutorialCard(id = index, action = action)
}
