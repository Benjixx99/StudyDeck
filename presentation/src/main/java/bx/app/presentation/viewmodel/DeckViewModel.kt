package bx.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bx.app.data.enums.CardFailing
import bx.app.data.model.DeckModel
import bx.app.data.repository.DeckRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DeckViewModel(private val repo: DeckRepository) : ViewModel() {
    private var _deck = MutableStateFlow<DeckModel>(DeckModel(
        name = "",
        description = "",
        color = 0,
        learnBothSides = false,
        onFailing = CardFailing.MOVE_TO_START
    ))

    val decks: StateFlow<List<DeckModel>> = repo.getAll().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
    var deck: StateFlow<DeckModel> = _deck

    fun getDeckById(id: Long) = viewModelScope.launch { _deck.value = repo.getById(id) }
    fun insertDeck(deck: DeckModel) = viewModelScope.launch { repo.insert(deck) }
    fun updateDeck(deck: DeckModel) = viewModelScope.launch { repo.update(deck) }
    fun deleteDeck(deck: DeckModel) = viewModelScope.launch { repo.delete(deck) }
}