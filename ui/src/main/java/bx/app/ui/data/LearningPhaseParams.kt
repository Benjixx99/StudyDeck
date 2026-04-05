package bx.app.ui.data

import bx.app.data.model.CardModel

internal data class LearningPhaseParams(
    val card: CardModel,
    val lastTimeLearnedFront: Boolean = false
) {
    companion object {
        fun from(
            card: CardModel,
            lastTimeLearnedFront: Boolean = false,
        ): LearningPhaseParams {
            return LearningPhaseParams(
                card = card,
                lastTimeLearnedFront = lastTimeLearnedFront
            )
        }
    }
}