# SwipeInteraction

An Android sample app that demonstrates polished, four-direction swipe gestures built with Jetpack Compose. Users learn task-style actions — complete, snooze, archive, and delete — through an interactive card tutorial with hint animations and a completion flow.

## Features

- **Four-direction swiping** — right, left, up, and down, each mapped to a distinct action
- **Card stack UI** — depth-scaled background cards with smooth drag, rotation, and dismiss animations
- **Guided tutorial** — automatic hint animations show the expected swipe direction on each card
- **Validation feedback** — correct swipes advance the stack; incorrect swipes snap back
- **Completion screen** — a success state with a **Try again** button to restart the tutorial
- **Material 3 design** — action-specific color palette, edge-to-edge layout, and dynamic theming support

## Screen recording

https://github.com/user-attachments/assets/b43e707a-82c0-4407-b24e-61740eb333ac

## Gestures

| Direction | Action  | Use case                                      |
|-----------|---------|-----------------------------------------------|
| Right     | Complete | Mark a finished task as done                  |
| Left      | Snooze   | Postpone a task for later                     |
| Down      | Archive  | Remove a task from the list and archive it    |
| Up        | Delete   | Permanently delete a task                     |

## Tech Stack

| Layer        | Technology                          |
|--------------|-------------------------------------|
| Language     | Kotlin                              |
| UI           | Jetpack Compose, Material 3         |
| Min SDK      | 24 (Android 7.0)                    |
| Target SDK   | 36                                  |
| Build        | Gradle (Kotlin DSL), AGP 9.2        |


## How It Works

1. The tutorial presents four cards, one per swipe action.
2. After a short delay, the top card plays a hint animation in the expected direction.
3. The user swipes the card; if the direction matches, the card dismisses and the next card appears.
4. When all cards are completed, a success screen is shown with the option to restart.
5. `SwipeCardState` handles drag tracking, spring-based animations, velocity-aware direction resolution, and animation cancellation on reset.

## Gesture Configuration

Thresholds and animation behavior are defined in `SwipeCardState.kt`:

- **Dismiss threshold** — 22% of card width/height along a single axis
- **Corner dismiss threshold** — 35% combined diagonal progress
- **Fling velocity threshold** — 900 px/s for fast swipes
- **Max rotation** — 12° based on horizontal drag offset
