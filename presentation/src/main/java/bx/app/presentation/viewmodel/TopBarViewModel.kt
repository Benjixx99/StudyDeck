package bx.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import bx.app.presentation.enums.ImportMode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TopBarViewModel : ViewModel() {
    private val _title = MutableStateFlow("")
    private val _importMode = MutableStateFlow(ImportMode.ALL)
    val title: StateFlow<String> = _title.asStateFlow()
    val importMode: StateFlow<ImportMode> = _importMode.asStateFlow()

    fun setTitle(title: String) { _title.value = title }
    fun setImportMode(importMode: ImportMode) { _importMode.value = importMode }
}