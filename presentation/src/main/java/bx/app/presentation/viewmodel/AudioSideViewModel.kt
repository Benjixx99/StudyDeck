package bx.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bx.app.data.enums.CardSide
import bx.app.data.model.AudioSideModel
import bx.app.data.repository.AudioSideRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.collections.plus

class AudioSideViewModel(private val repo: AudioSideRepository) : ViewModel() {
    private val _fileNameById = MutableStateFlow<Map<Long, String?>>(emptyMap())
    private var _audioSide = MutableStateFlow<AudioSideModel>(AudioSideModel(
        path = "",
        fileName = "",
        side = CardSide.FRONT,
        cardId = 0
    ))

    val fileNameById: StateFlow<Map<Long, String?>> = _fileNameById
    var audioSide: StateFlow<AudioSideModel> = _audioSide

    fun getAudioSideById(id: Long) = viewModelScope.launch { _audioSide.value = repo.getById(id) }
    fun getFileNameById(id: Long) = viewModelScope.launch {
        if (_fileNameById.value.containsKey(id)) return@launch
        repo.getFileNameById(id).filterNotNull().collect {
                fileName -> _fileNameById.update { current ->  current + (id to fileName) }
        }
    }

    fun insertAudioSide(audioSide: AudioSideModel) = viewModelScope.launch { repo.insert(audioSide) }
    fun updateAudioSide(audioSide: AudioSideModel) = viewModelScope.launch { repo.update(audioSide) }
    fun deleteAudioSide(audioSide: AudioSideModel) = viewModelScope.launch { repo.delete(audioSide) }
}