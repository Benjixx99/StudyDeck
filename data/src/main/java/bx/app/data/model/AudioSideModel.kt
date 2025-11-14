package bx.app.data.model

import bx.app.data.enums.CardSide
import bx.app.data.local.entity.AudioSideEntity

data class AudioSideModel(
    val id: Long = 0,
    val path: String,
    val fileName: String,
    val side: CardSide,
    val cardId: Long,
) : BaseModel() {
    override fun toEntity(): AudioSideEntity {
        return AudioSideEntity(
            id = this.id,
            path = this.path,
            fileName = this.fileName,
            side = this.side,
            cardId = this.cardId
        )
    }
}
