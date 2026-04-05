package bx.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import bx.app.data.enums.SortMode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TopBarViewModel : ViewModel() {
    private val _title = MutableStateFlow("")
    private val _cardsSortMode = MutableStateFlow(SortMode.ID_ASC)
    val title: StateFlow<String> = _title.asStateFlow()
    val cardsSortMode: StateFlow<SortMode> = _cardsSortMode.asStateFlow()

    fun setTitle(title: String) { _title.value = title }
    fun setCardsSortMode(sortMode: SortMode) { _cardsSortMode.value = sortMode }
}