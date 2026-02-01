package bx.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bx.app.data.enums.CardSideType
import bx.app.data.model.CardModel
import bx.app.data.repository.CardRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.collections.plus

class CardViewModel(private val repo: CardRepository) : ViewModel() {
    private val _deckId = MutableStateFlow(0L)
    private val _levelId = MutableStateFlow(0L)
    private val _card = MutableStateFlow<CardModel>(getInitialCard())
    private val _cardsCountByDeckId = MutableStateFlow<Map<Long, Int?>>(emptyMap())

    val deckId: StateFlow<Long> = _deckId
    val card: StateFlow<CardModel> = _card
    val cardsCountByDeckId: StateFlow<Map<Long, Int?>> = _cardsCountByDeckId
    val cards: StateFlow<List<CardModel>> =
        repo.observeById(_deckId).stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
    val cardsInLevel: StateFlow<List<CardModel>> =
        repo.observeByLevelId(_levelId).stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun countCardsByDeckId(id: Long) = viewModelScope.launch {
        if (_cardsCountByDeckId.value.containsKey(id)) return@launch
        repo.countCardsByDeckId(id).filterNotNull().collect {
                count -> _cardsCountByDeckId.update { current -> current + (id to count) }
        }
    }

    fun getCardById(id: Long) = viewModelScope.launch { _card.value = repo.getById(id) }
    fun insertCard(card: CardModel) = viewModelScope.launch { repo.insert(card) }

    fun setDeckId(id: Long) { _deckId.value = id }
    fun setLevelId(id: Long) { _levelId.value = id }
    fun resetCard() { _card.value = getInitialCard() }

    private fun getInitialCard(): CardModel {
        return CardModel(
            frontSideId = 0,
            frontSideType = CardSideType.TEXT,
            backSideId = 0,
            backSideType = CardSideType.TEXT,
            deckId = 0
        )
    }
}