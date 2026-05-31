package bx.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bx.app.data.enums.ConfigScope
import bx.app.data.enums.SortMode
import bx.app.data.model.ConfigModel
import bx.app.data.repository.ConfigRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ConfigViewModel(private val repo: ConfigRepository) : ViewModel() {
    private object Key {
        const val SORT_BY = "sort_by"
    }

    private val _decksSortMode = MutableStateFlow<SortMode>(SortMode.TEXT_ASC)
    val decksSortMode: StateFlow<SortMode> = _decksSortMode

    private val _cardsSortMode = MutableStateFlow<SortMode>(SortMode.TEXT_ASC)
    val cardsSortMode: StateFlow<SortMode> = _cardsSortMode

    fun loadSortMode(scope: ConfigScope) = viewModelScope.launch {
        val value = repo.getValue(Key.SORT_BY, scope)
        if (value.isNotEmpty()) {
            setSortMode(scope, SortMode.fromString(value))
        }
    }

    fun updateSortMode(scope: ConfigScope, value: SortMode) = viewModelScope.launch {
        repo.insert(ConfigModel(key = Key.SORT_BY, scope = scope, value = value.toString()))
        setSortMode(scope, value)
    }

    private fun setSortMode(scope: ConfigScope, value: SortMode) {
        when(scope) {
            ConfigScope.DECKS -> _decksSortMode.value = value
            ConfigScope.CARDS -> _cardsSortMode.value = value
            else -> { /* ignore */ }
        }
    }
}