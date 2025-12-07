package bx.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

abstract class DebouncedAutoSaveViewModel() : ViewModel() {
    private var saveJob: Job? = null
    private val debounceMilliseconds = 800L

    protected fun scheduleAutoSave(persistCurrentState: suspend () -> Unit) {
        saveJob?.cancel()
        saveJob = viewModelScope.launch {
            delay(debounceMilliseconds)
            persistCurrentState()
        }
    }

    override fun onCleared() {
        super.onCleared()
        saveJob?.cancel()
    }
}