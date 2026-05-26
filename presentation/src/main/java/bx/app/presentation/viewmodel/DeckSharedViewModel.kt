package bx.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DeckSharedViewModel : ViewModel() {
    private val _deckId = MutableStateFlow<Long>(0)
    val deckId: StateFlow<Long> = _deckId
    fun setDeckId(id: Long) { _deckId.value = id }
}