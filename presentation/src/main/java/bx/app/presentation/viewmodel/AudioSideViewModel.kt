package bx.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bx.app.core.hasValidId
import bx.app.data.enums.CardSide
import bx.app.data.model.AudioSideModel
import bx.app.data.model.CardModel
import bx.app.data.repository.AudioSideRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AudioSideViewModel(private val repo: AudioSideRepository) : ViewModel() {
    private val _audioSide = MutableStateFlow<AudioSideModel>(getInitialAudioSide())

    val audioSide: StateFlow<AudioSideModel> = _audioSide

    fun getAudioSideById(id: Long) = viewModelScope.launch { _audioSide.value = repo.getById(id) }

    fun getAudioSideByCard(card: CardModel, cardSide: CardSide) {
        val id =
            if (cardSide.isFront())
                card.frontSideId.takeIf { card.frontSideType.isAudio() }
            else
                card.backSideId.takeIf { card.backSideType.isAudio() }

        if (id != null && id.hasValidId()) getAudioSideById(id)
    }

    fun insertAudioSide(audioSide: AudioSideModel) = viewModelScope.launch { repo.insert(audioSide) }

    fun resetAudioSide() { _audioSide.value = getInitialAudioSide() }
    fun resetAudioSide(audioSide: AudioSideModel) {
        resetAudioSide()
        _audioSide.value = audioSide
    }

    private fun getInitialAudioSide(): AudioSideModel {
        return AudioSideModel(
            path = "",
            fileName = "",
            side = CardSide.FRONT,
            cardId = 0
        )
    }
}