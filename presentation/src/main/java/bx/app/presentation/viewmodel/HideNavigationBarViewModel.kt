package bx.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// TODO: Need to change the name because its task is to hide the navigation bar and i need this for the bug [0002] too
// but it hides the nav bar if (deckId <= IdValidator.MIN_VALUE)
class HideNavigationBarViewModel : ViewModel() {
    private val _hide = MutableStateFlow(false)
    val hide: StateFlow<Boolean> = _hide.asStateFlow()

    fun setHide(hide: Boolean) { _hide.value = hide }
}