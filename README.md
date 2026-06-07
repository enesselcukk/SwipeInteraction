# SwipeInteraction

An Android sample app that demonstrates a polished, looping card stack built with Jetpack Compose. Pull the front card in any direction and watch it animate to the back of the deck — the stack never runs out of cards.

## Features

- **Looping card stack** — the front card cycles to the back instead of being dismissed
- **Directional send-to-back animation** — swipe right or down to tuck behind from the right; swipe left or up from the left
- **Fanned stack layout** — depth-scaled cards offset to the right with subtle rotation
- **Drag-driven promotion** — background cards scale up as the front card is pulled
- **Animated header** — the current card title crossfades as the deck rotates
- **Custom deck theme** — gradient cards, category tags, and a dedicated `DeckTheme` token set
- **Layered architecture** — state, animation, layout, and UI split across focused modules
- **Material 3 design** — edge-to-edge layout with a cool indigo-accent palette

## Screen recording

https://github.com/user-attachments/assets/385ae560-a88a-40c4-95a2-dd55a719f430

## Gestures

| Direction | Send-to-back path |
|-----------|-------------------|
| Right     | Sweeps right, settles behind from the right |
| Left      | Sweeps left, settles behind from the left |
| Down      | Drops down, tucks in from the right |
| Up        | Lifts up, tucks in from the left |

Any direction above the swipe threshold triggers the cycle. Below the threshold, the card snaps back.

## How it works

1. Five cards are rendered in a fanned stack; only the front card accepts drag input.
2. Dragging the front card promotes the cards behind it toward the front position.
3. When the swipe threshold is met, `LoopingCardStackState.cycleToBack()` runs a ~920 ms keyframe path via `CyclePathAnimation`.
4. The cycling card passes behind the stack (z-index drops mid-animation) while the next card moves to the front.
5. When the animation finishes, the list rotates atomically (`removeAt(0)` + `add`) and swipe state resets — ready for the next pull.

## Gesture configuration

Thresholds and drag behavior live in `SwipeCardState.kt`:

- **Cycle threshold** — 22% of card width/height along a single axis
- **Corner threshold** — 35% combined diagonal progress
- **Fling velocity threshold** — 900 px/s for fast swipes
- **Max rotation** — 12° based on horizontal drag offset
- **Cycle duration** — 920 ms (`CYCLE_DURATION_MS` in `CyclePathAnimation.kt`)

## Tech stack

| Layer      | Technology                   |
|------------|------------------------------|
| Language   | Kotlin                       |
| UI         | Jetpack Compose, Material 3 |
| Min SDK    | 24 (Android 7.0)             |
| Target SDK | 36                           |
| Build      | Gradle (Kotlin DSL), AGP 9.2 |
