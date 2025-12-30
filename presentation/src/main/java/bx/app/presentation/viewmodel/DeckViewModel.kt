package bx.app.presentation.viewmodel

import android.graphics.Color
import androidx.lifecycle.viewModelScope
import bx.app.data.enums.CardFailing
import bx.app.data.model.DeckModel
import bx.app.data.repository.DeckRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DeckViewModel(private val repo: DeckRepository) : DebouncedAutoSaveViewModel() {
    private val _deck = MutableStateFlow<DeckModel>(getInitialDeck())

    val decks: StateFlow<List<DeckModel>> = repo.getAll().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
    val deck: StateFlow<DeckModel> = _deck

    fun getDeckById(id: Long) = viewModelScope.launch { _deck.value = repo.getById(id) }
    fun insertDeck(deck: DeckModel) = viewModelScope.launch { repo.insert(deck) }
    fun updateDeck(deck: DeckModel) = viewModelScope.launch { repo.update(deck) }
    fun deleteDeck(deck: DeckModel) = viewModelScope.launch { repo.delete(deck) }

    fun resetDeck() { _deck.value = getInitialDeck() }

    fun changeName(value: String) = upsertDeck { _deck.value.copy(name = value) }
    fun changeDescription(value: String) = upsertDeck { _deck.value.copy(description = value) }
    fun changeColor(value: Long) = upsertDeck { _deck.value.copy(color = value) }
    fun changeLearnBothSides(value: Boolean) = upsertDeck { _deck.value.copy(learnBothSides = value) }
    fun changeOnFailing(value: CardFailing) = upsertDeck { _deck.value.copy(onFailing = value) }

    private fun upsertDeck(transform: DeckModel.() -> DeckModel) {
        _deck.value = _deck.value.transform()
        scheduleAutoSave { getDeckById(repo.upsert(_deck.value)) }
    }

    private fun getInitialDeck(): DeckModel {
        return DeckModel(
            name = "",
            description = "",
            color = Color.RED.toLong(),
            learnBothSides = false,
            onFailing = CardFailing.MOVE_TO_START
        )
    }
}