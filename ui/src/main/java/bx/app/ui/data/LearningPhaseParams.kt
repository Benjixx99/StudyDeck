package bx.app.ui.data

import bx.app.data.enums.CardSideType
import bx.app.data.model.CardModel

internal data class LearningPhaseParams(
    val card: CardModel,
    val frontText: String = "",
    val frontFileName: String = "",
    val frontPath: String = "",
    val backText: String = "",
    val backFileName: String = "",
    val backPath: String = ""
) {
    companion object {
        fun from(
            textById: Map<Long, String?>,
            fileNameById: Map<Long, String?>,
            pathById: Map<Long, String?>,
            card: CardModel
        ): LearningPhaseParams {
            return LearningPhaseParams(
                card = card,
                frontText =
                    if (card.frontSideType == CardSideType.TEXT) textById[card.frontSideId].orEmpty() else "",
                frontFileName =
                    if (card.frontSideType == CardSideType.AUDIO) fileNameById[card.frontSideId].orEmpty() else "",
                frontPath =
                    if (card.frontSideType == CardSideType.AUDIO) pathById[card.frontSideId].orEmpty() else "",
                backText =
                    if (card.backSideType == CardSideType.TEXT) textById[card.backSideId].orEmpty() else "",
                backFileName =
                    if (card.backSideType == CardSideType.AUDIO) fileNameById[card.backSideId].orEmpty() else "",
                backPath =
                    if (card.backSideType == CardSideType.AUDIO) pathById[card.backSideId].orEmpty() else ""
            )
        }
    }
}