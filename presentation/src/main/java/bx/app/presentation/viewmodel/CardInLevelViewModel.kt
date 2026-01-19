package bx.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bx.app.data.enums.CardFailing
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
    private val _learnableCardsCountByLevelId = MutableStateFlow<Map<Long, Int?>>(emptyMap())
    private val _lastTimeLearnedFront = MutableStateFlow(false)

    val cardsCountByLevelId: StateFlow<Map<Long, Int?>> = _cardsCountByLevelId
    val learnableCardsCountByLevelId: StateFlow<Map<Long, Int?>> = _learnableCardsCountByLevelId
    val lastTimeLearnedFront: StateFlow<Boolean> = _lastTimeLearnedFront

    fun getLastTimeLearnedFrontByCardId(id: Long) = viewModelScope.launch {
        repo.getLastTimeLearnedFrontByCardId(id).filterNotNull().collect {
            value -> _lastTimeLearnedFront.update { value }
        }
    }

    fun countCardsByLevelId(id: Long) = viewModelScope.launch {
        if (_cardsCountByLevelId.value.containsKey(id)) return@launch
        repo.countCardsByLevelId(id).filterNotNull().collect {
            count -> _cardsCountByLevelId.update { current -> current + (id to count) }
        }
    }

    fun countLearnableCardsByLevelId(id: Long) = viewModelScope.launch {
        if (_learnableCardsCountByLevelId.value.containsKey(id)) return@launch
        repo.countLearnableCardsByLevelId(id).filterNotNull().collect {
                count -> _learnableCardsCountByLevelId.update { current -> current + (id to count) }
        }
    }

    fun insertCardInLevel(cardInLevel: CardInLevelModel) = viewModelScope.launch { repo.insert(cardInLevel) }

    fun updateCardInLevel(
        cardId: Long,
        levelId: Long,
        learnBothSides: Boolean,
    ) = viewModelScope.launch { repo.update(levelId, cardId, learnBothSides) }

    fun updateCardInLevel(
        cardId: Long,
        levelId: Long,
        learnBothSides: Boolean,
        onFailing: CardFailing,
    ) = viewModelScope.launch { repo.update(levelId, cardId, learnBothSides, onFailing) }
}