package bx.app.ui.data

import bx.app.data.model.CardModel

internal data class LearningPhaseParams(
    val card: CardModel,
    val frontText: String = "",
    val frontFileName: String = "",
    val frontPath: String = "",
    val backText: String = "",
    val backFileName: String = "",
    val backPath: String = "",
    val lastTimeLearnedFront: Boolean = false
) {
    companion object {
        fun from(
            textById: Map<Long, String?>,
            fileNameById: Map<Long, String?>,
            pathById: Map<Long, String?>,
            card: CardModel,
            lastTimeLearnedFront: Boolean = false,
        ): LearningPhaseParams {
            return LearningPhaseParams(
                card = card,
                frontText =
                    if (card.frontSideType.isText()) textById[card.frontSideId].orEmpty() else "",
                frontFileName =
                    if (card.frontSideType.isAudio()) fileNameById[card.frontSideId].orEmpty() else "",
                frontPath =
                    if (card.frontSideType.isAudio()) pathById[card.frontSideId].orEmpty() else "",
                backText =
                    if (card.backSideType.isText()) textById[card.backSideId].orEmpty() else "",
                backFileName =
                    if (card.backSideType.isAudio()) fileNameById[card.backSideId].orEmpty() else "",
                backPath =
                    if (card.backSideType.isAudio()) pathById[card.backSideId].orEmpty() else "",
                lastTimeLearnedFront = lastTimeLearnedFront
            )
        }
    }
}