package bx.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bx.app.data.enums.CardSideType
import bx.app.data.model.CardModel
import bx.app.data.repository.CardRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CardViewModel(private val repo: CardRepository) : ViewModel() {
    private val _deckId = MutableStateFlow(0L)
    private var _card = MutableStateFlow<CardModel>(getInitialCard())

    var card: StateFlow<CardModel> = _card
    val deckId: StateFlow<Long> = _deckId
    val cards: StateFlow<List<CardModel>> =
        repo.observeById(deckId).stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun getCardById(id: Long) = viewModelScope.launch { _card.value = repo.getById(id) }
    fun insertCard(card: CardModel) = viewModelScope.launch { repo.insert(card) }
    fun updateCard(card: CardModel) = viewModelScope.launch { repo.update(card) }
    fun deleteCard(card: CardModel) = viewModelScope.launch { repo.delete(card) }

    fun setDeckId(id: Long) { _deckId.value = id }

    fun resetCard() { _card.value = getInitialCard() }
    fun resetCard(card: CardModel) {
        resetCard()
        _card.value = card
    }

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