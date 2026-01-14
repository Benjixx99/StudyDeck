package bx.app.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import bx.app.data.enums.CardSide
import bx.app.data.enums.CardSideType
import bx.app.data.model.CardModel
import bx.app.data.repository.CardWithSidesRepository
import bx.app.presentation.data.IdValidator
import kotlinx.coroutines.launch

class CardWithSidesViewModel(
    private val repo: CardWithSidesRepository,
    val cardViewModel: CardViewModel,
    val textSideViewModel: TextSideViewModel,
    val audioSideViewModel: AudioSideViewModel
) : DebouncedAutoSaveViewModel() {
    private val card = cardViewModel.card
    private val textSide = textSideViewModel.textSide
    private val audioSide = audioSideViewModel.audioSide

    fun changeText(value: String, cardSide: CardSide) {
        when {
            card.value.id < IdValidator.MIN_VALID_ID -> insertCardWithTextSide(value, cardSide)
            textSide.value.id < IdValidator.MIN_VALID_ID -> insertTextSideAndUpdateCard(value, cardSide)
            else -> updateCardWithTextSide(value, cardSide)
        }
    }

    fun changeAudioData(path: String, fileName: String, cardSide: CardSide) {
        when {
            card.value.id < IdValidator.MIN_VALID_ID -> insertCardWithAudioSide(path, fileName, cardSide)
            audioSide.value.id < IdValidator.MIN_VALID_ID -> insertAudioSideAndUpdateCard(path, fileName, cardSide)
            else -> updateCardWithAudioSide(path, fileName, cardSide)
        }
    }

    fun changeCardSideType(value: CardSideType, cardSide: CardSide) {
        scheduleAutoSave {
            val id = repo.updateCardSide(
                card = card.value,
                textSide = textSide.value,
                audioSide = audioSide.value,
                cardSide = cardSide,
                cardSideType = value
            )

            if (id == 0L) return@scheduleAutoSave
            when (value) {
                CardSideType.TEXT -> textSideViewModel.getTextSideById(id)
                CardSideType.AUDIO -> audioSideViewModel.getAudioSideById(id)
            }
        }
    }

    fun getCardSideValues(card: CardModel) {
        if (card.frontSideType == CardSideType.TEXT) { textSideViewModel.getTextById(card.frontSideId) }
        if (card.backSideType == CardSideType.TEXT) { textSideViewModel.getTextById(card.backSideId) }
        if (card.frontSideType == CardSideType.AUDIO) {
            audioSideViewModel.getFileNameById(card.frontSideId)
            audioSideViewModel.getPathById(card.frontSideId)
        }
        if (card.backSideType == CardSideType.AUDIO) {
            audioSideViewModel.getFileNameById(card.backSideId)
            audioSideViewModel.getPathById(card.backSideId)
        }
    }

    fun deleteCardsByDeckId(id: Long) = viewModelScope.launch { repo.deleteCardsByDeckId(id) }
    fun deleteCardById(id: Long) = viewModelScope.launch { repo.deleteCardById(id) }

    private fun insertCardWithTextSide(value: String, cardSide: CardSide) {
        textSideViewModel.resetTextSide(textSide.value.copy(text = value))
        scheduleAutoSave {
            val pair = repo.insertCardWithTextSide(
                card = card.value.copy(deckId = cardViewModel.deckId.value),
                textSide = textSide.value.copy(text = value, cardId = card.value.id),
                cardSide = cardSide
            )
            cardViewModel.getCardById(pair.first)
            textSideViewModel.getTextSideById(pair.second)
        }
    }

    private fun insertTextSideAndUpdateCard(value: String, cardSide: CardSide) {
        textSideViewModel.resetTextSide(textSide.value.copy(text = value))
        scheduleAutoSave {
            val id = repo.insertTextSideAndUpdateCard(
                card = card.value,
                textSide = textSide.value.copy(text = value, cardId = card.value.id),
                cardSide = cardSide)
            cardViewModel.getCardById(card.value.id)
            textSideViewModel.getTextSideById(id)
        }
    }

    private fun updateCardWithTextSide(value: String, cardSide: CardSide) {
        textSideViewModel.resetTextSide(textSide.value.copy(text = value))
        scheduleAutoSave {
            repo.updateCardWithTextSide(card.value, textSide.value, cardSide)
        }
    }

    private fun insertCardWithAudioSide(path: String, fileName: String, cardSide: CardSide) {
        audioSideViewModel.resetAudioSide(audioSide.value.copy(path = path, fileName = fileName))
        scheduleAutoSave {
            val pair = repo.insertCardWithAudioSide(
                card = card.value.copy(deckId = cardViewModel.deckId.value),
                audioSide = audioSide.value.copy(path = path, fileName = fileName, cardId = card.value.id),
                cardSide = cardSide
            )
            cardViewModel.getCardById(pair.first)
            textSideViewModel.getTextSideById(pair.second)
        }
    }

    private fun insertAudioSideAndUpdateCard(path: String, fileName: String, cardSide: CardSide) {
        audioSideViewModel.resetAudioSide(audioSide.value.copy(path = path, fileName = fileName))
        scheduleAutoSave {
            val id = repo.insertAudioSideAndUpdateCard(
                card = card.value,
                audioSide = audioSide.value.copy(path = path, fileName = fileName, cardId = card.value.id),
                cardSide = cardSide
            )
            cardViewModel.getCardById(card.value.id)
            audioSideViewModel.getAudioSideById(id)
        }
    }

    private fun updateCardWithAudioSide(path: String, fileName: String, cardSide: CardSide) {
        audioSideViewModel.resetAudioSide(audioSide.value.copy(path = path, fileName = fileName))
        scheduleAutoSave {
            repo.updateCardWithAudioSide(card.value, audioSide.value, cardSide)
        }
    }
}