package bx.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HideNavigationBarViewModel : ViewModel() {
    private val _hide = MutableStateFlow(false)
    val hide: StateFlow<Boolean> = _hide.asStateFlow()

    fun setHide(hide: Boolean) { _hide.value = hide }
}