package bx.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bx.app.data.model.CardInLevelModel
import bx.app.data.repository.CardInLevelRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.collections.plus

class CardInLevelViewModel(private val repo: CardInLevelRepository) : ViewModel() {
    private val _cardsCountByLevelId = MutableStateFlow<Map<Long, Int?>>(emptyMap())

    val cardsCountByLevelId: StateFlow<Map<Long, Int?>> = _cardsCountByLevelId

    fun countCardsByLevelId(id: Long) = viewModelScope.launch {
        if (_cardsCountByLevelId.value.containsKey(id)) return@launch
        repo.countCardsByLevelId(id).filterNotNull().collect {
            count -> _cardsCountByLevelId.update { current ->  current + (id to count) }
        }
    }

    fun insertCardInLevel(cardInLevel: CardInLevelModel) = viewModelScope.launch { repo.insert(cardInLevel) }
}