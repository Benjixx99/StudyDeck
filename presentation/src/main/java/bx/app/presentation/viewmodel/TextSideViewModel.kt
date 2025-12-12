package bx.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bx.app.data.enums.CardSide
import bx.app.data.model.TextSideModel
import bx.app.data.repository.TextSideRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TextSideViewModel(private val repo: TextSideRepository) : ViewModel() {
    private val _textById = MutableStateFlow<Map<Long, String?>>(emptyMap())
    private var _textSide = MutableStateFlow<TextSideModel>(TextSideModel(
        text = "",
        side = CardSide.FRONT,
        cardId = 0
    ))

    val textById: StateFlow<Map<Long, String?>> = _textById
    var textSide: StateFlow<TextSideModel> = _textSide

    fun getTextSideById(id: Long) = viewModelScope.launch { _textSide.value = repo.getById(id) }
    fun getTextFlowById(id: Long) = viewModelScope.launch {
        if (_textById.value.containsKey(id)) return@launch
        repo.getTextById(id).filterNotNull().collect {
            text -> _textById.update { current ->  current + (id to text) }
        }
    }

    fun insertTextSide(textSide: TextSideModel) = viewModelScope.launch { repo.insert(textSide) }
    fun updateTextSide(textSide: TextSideModel) = viewModelScope.launch { repo.update(textSide) }
    fun deleteTextSide(textSide: TextSideModel) = viewModelScope.launch { repo.delete(textSide) }
}